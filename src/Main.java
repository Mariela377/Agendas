import agenda.*;
import agendaViews.MainForm;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Main{
    // Aquí quedan guardadas las clases cargadas: nombre simple -> clase (ej. "EventoPijamada")
    private static final Map<String, Class<?>> CLASES_CARGADAS = new LinkedHashMap<>();

    public static void main(String[] args) {

        //crear agenda
        Agenda agenda1 = new Agenda();

        //crear contactos y agregarlos a la agenda
        agenda1.agregarEvento(new EventoReunion(
                17,
                LocalDate.now(),
                "cumpleaños"
        ));

        agenda1.agregarEvento(new EventoSocial(
                "Casona Santa Maria",
                LocalDate.now(),
                "cumpleaños"
        ));

        agenda1.agregarContacto(new ContactoFamiliar(
                "María López",
                42,
                2,
                "8888-2222",
                202020202,
                "Hermana"
        ));

        agenda1.agregarContacto(new ContactoNegocios(
                "Carlos Rodríguez",
                50,
                1,
                "8888-3333",
                303030303,
                "carlos@empresa.com"
        ));

        agenda1.agregarContacto(new ContactoFamiliar(
                "Ana Vargas",
                28,
                2,
                "8888-4444",
                404040404,
                "Prima"
        ));

        agenda1.agregarContacto(new ContactoNegocios(
                "Luis González",
                45,
                1,
                "8888-5555",
                505050505,
                "luis@negocios.com"
        ));
        //imprimir agenda
        System.out.println(agenda1); //llama a su toString()

        //modificar un contacto de agenda
        agenda1.modificarContactoFamiliar(101010101,"Ana María Vargas",29,2,"888-4444","Prima");
        agenda1.modificarContactoFamiliar(404040404,"Ana María Vargas",29,2,"888-4444","Prima");

        //eliminar contacto
        agenda1.eliminarContacto(101010101);
        agenda1.eliminarContacto(606060606);

        //voy a agregar elementos nuevos

        MainForm starForm = new MainForm(agenda1);

        //cargar dinámicamente todas las clases de la carpeta ElementosAgendaClases
        List<Class<?>> clasesCargadas = cargarClasesDinamicamente("ElementosAgendaClases");
        for (Class<?> clase : clasesCargadas) {
            System.out.println("Clase cargada: " + clase.getName()
                    + " (hereda de " + clase.getSuperclass().getSimpleName() + ")");
        }

        //instanciar una clase cargada usando su nombre y los argumentos de su constructor
        LinkedList<String> asistentes = new LinkedList<>();
        asistentes.add("Ana");
        asistentes.add("María");
        Object pijamada = instanciar("EventoPijamada", LocalDate.now(), "Casa de Ana", asistentes, "Pijamada de viernes");
        if (pijamada instanceof Evento) {
            agenda1.agregarEvento((Evento) pijamada);
        }

        //imprimir agenda final
        System.out.println(agenda1);
    }

    /**
     * Lee todos los archivos .class de la carpeta indicada y los carga en memoria
     * de forma dinámica. No hace falta saber de antemano ni los nombres ni el
     * paquete de las clases: el nombre completo se toma del propio bytecode.
     *
     * @param rutaCarpeta ruta de la carpeta (relativa a la raíz del proyecto o absoluta)
     * @return lista con todas las clases que se pudieron cargar
     */
    public static List<Class<?>> cargarClasesDinamicamente(String rutaCarpeta) {
        List<Class<?>> clases = new ArrayList<>();
        Path carpeta = Paths.get(rutaCarpeta).toAbsolutePath();

        if (!Files.isDirectory(carpeta)) {
            System.out.println("ERROR: No existe la carpeta " + carpeta);
            return clases;
        }

        CargadorCarpeta cargador = new CargadorCarpeta(carpeta, Main.class.getClassLoader());

        try (DirectoryStream<Path> archivos = Files.newDirectoryStream(carpeta, "*.class")) {
            for (Path archivo : archivos) {
                try {
                    Class<?> clase = cargador.cargarArchivo(archivo);
                    if (!clases.contains(clase)) {
                        clases.add(clase);
                    }
                    CLASES_CARGADAS.put(clase.getSimpleName(), clase); // se guarda para usarla después
                } catch (IOException | LinkageError e) {
                    System.out.println("No se pudo cargar " + archivo.getFileName() + ": " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR al leer la carpeta " + carpeta + ": " + e.getMessage());
        }
        return clases;
    }

    /** Devuelve una clase que ya fue cargada, buscándola por su nombre simple (o null si no está). */
    public static Class<?> obtenerClase(String nombreClase) {
        return CLASES_CARGADAS.get(nombreClase);
    }

    /**
     * Crea un objeto de una clase cargada dinámicamente. Busca el constructor
     * que coincida con la cantidad y el tipo de los argumentos que se le pasan.
     *
     * @param nombreClase nombre simple de la clase, ej. "EventoPijamada"
     * @param argumentos  los valores para el constructor, en el mismo orden
     * @return el objeto creado, o null si no se pudo crear
     */
    public static Object instanciar(String nombreClase, Object... argumentos) {
        Class<?> clase = CLASES_CARGADAS.get(nombreClase);
        if (clase == null) {
            System.out.println("ERROR: La clase " + nombreClase + " no ha sido cargada");
            return null;
        }
        for (java.lang.reflect.Constructor<?> constructor : clase.getConstructors()) {
            if (coincidenParametros(constructor.getParameterTypes(), argumentos)) {
                try {
                    return constructor.newInstance(argumentos);
                } catch (ReflectiveOperationException e) {
                    System.out.println("ERROR al instanciar " + nombreClase + ": " + e);
                    return null;
                }
            }
        }
        System.out.println("ERROR: " + nombreClase + " no tiene un constructor público con esos argumentos");
        return null;
    }

    // Revisa si los argumentos sirven para los tipos de parámetros de un constructor
    private static boolean coincidenParametros(Class<?>[] tipos, Object[] argumentos) {
        if (tipos.length != argumentos.length) {
            return false;
        }
        for (int i = 0; i < tipos.length; i++) {
            Class<?> tipo = tipos[i].isPrimitive() ? envoltorio(tipos[i]) : tipos[i];
            if (argumentos[i] == null) {
                if (tipos[i].isPrimitive()) return false; // un int no puede ser null
            } else if (!tipo.isInstance(argumentos[i])) {
                return false;
            }
        }
        return true;
    }

    // int -> Integer, double -> Double, etc. (los argumentos llegan "envueltos" como objetos)
    private static Class<?> envoltorio(Class<?> primitivo) {
        if (primitivo == int.class) return Integer.class;
        if (primitivo == long.class) return Long.class;
        if (primitivo == double.class) return Double.class;
        if (primitivo == float.class) return Float.class;
        if (primitivo == boolean.class) return Boolean.class;
        if (primitivo == char.class) return Character.class;
        if (primitivo == byte.class) return Byte.class;
        if (primitivo == short.class) return Short.class;
        return primitivo;
    }

    /**
     * ClassLoader que define las clases a partir de los bytes de los .class de una carpeta.
     * Las clases del proyecto (ElementosAgenda, Evento, Contacto...) las sigue
     * resolviendo el ClassLoader normal de la aplicación (el "padre").
     */
    private static class CargadorCarpeta extends ClassLoader {
        private final Path carpeta;
        private final Map<String, Class<?>> cargadas = new HashMap<>(); // nombre de archivo -> clase

        CargadorCarpeta(Path carpeta, ClassLoader padre) {
            super(padre);
            this.carpeta = carpeta;
        }

        Class<?> cargarArchivo(Path archivo) throws IOException {
            String nombreArchivo = archivo.getFileName().toString();
            Class<?> yaCargada = cargadas.get(nombreArchivo);
            if (yaCargada != null) {
                return yaCargada;
            }
            byte[] bytes = Files.readAllBytes(archivo);
            // con nombre null, la JVM lee el nombre completo (paquete.Clase) del bytecode
            Class<?> clase = defineClass(null, bytes, 0, bytes.length);
            cargadas.put(nombreArchivo, clase);
            return clase;
        }

        // Se llama cuando una clase de la carpeta necesita otra que el padre no conoce
        // (por ejemplo, si en la carpeta una clase hereda de otra que también está ahí).
        @Override
        protected Class<?> findClass(String nombre) throws ClassNotFoundException {
            String nombreSimple = nombre.substring(nombre.lastIndexOf('.') + 1);
            Path archivo = carpeta.resolve(nombreSimple + ".class");
            if (Files.exists(archivo)) {
                try {
                    return cargarArchivo(archivo);
                } catch (IOException e) {
                    throw new ClassNotFoundException(nombre, e);
                }
            }
            throw new ClassNotFoundException(nombre);
        }
    }

}

// la primera restriccion para hacer  public abstract String converToXml(); la clase ha de ser abstracta