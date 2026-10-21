package agenda;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class Agenda {
    private LinkedList<ElementosAgenda> listaEventos;  //la lista guarda tipo Contacto, no ContactoFamiliar ni ContactoNegocios por separado.

    public Agenda() {
        this.listaEventos = new LinkedList<>();
    }

    public void agregarContacto(Contacto c){
        this.listaEventos.add(c);
    }
    public void agregarEvento(Evento e){
        this.listaEventos.add(e);
    }

    public Contacto buscarContacto(int cedula){ //Recorre la lista comparando cédulas. Si no encuentra nada, devuelve null
        for (ElementosAgenda elemento : this.listaEventos){
            if (elemento instanceof Contacto){
                Contacto c = (Contacto) elemento;
                if(c.getCedula()==cedula)
                    return c;
            }
        }
        return null;
    }

    // ⬇️ MÉTODO NUEVO ⬇️
    //Igual que buscarContacto, pero compara el teléfono. Si no encuentra nada, devuelve null
    public Contacto buscarContactoPorTelefono(String telefono) {
        for (ElementosAgenda elemento : this.listaEventos) {
            if (elemento instanceof Contacto) {
                Contacto c = (Contacto) elemento;
                if (c.getTelefono().equals(telefono))
                    return c;
            }
        }
        return null;
    }
    // ⬆️ HASTA AQUÍ ⬆️

    //Primero busca el objeto, luego lo remueve de la lista por referencia (no por posición).
    public void eliminarContacto(int cedula){
        Contacto c = this.buscarContacto(cedula);
        if (c!= null)
            this.listaEventos.removeFirstOccurrence(c);
        else
            System.out.println("La cedula a borrar no pertenece a ningún contacto");
    }

    public void modificarContacto(int cedula, String nombre, int edad,
                                  int genero, String telefono) {

        Contacto contacto = buscarContacto(cedula);

        if (contacto != null) {
            contacto.getLaPersona().setNombre(nombre);
            contacto.getLaPersona().setEdad(edad);
            contacto.getLaPersona().setGenero(genero);
            contacto.setTelefono(telefono);
        }
        else
            System.out.println("ERROR: La cédula no pertenece a ningún contacto en la agenda!");
    }

    public void modificarContactoFamiliar(int cedula, String nombre, int edad,
                                          int genero, String telefono,
                                          String parentesco) {

        Contacto contacto = buscarContacto(cedula);

        if (contacto != null) {
            if (contacto instanceof ContactoFamiliar) {
                ContactoFamiliar familiar = (ContactoFamiliar) contacto;

                familiar.getLaPersona().setNombre(nombre);
                familiar.getLaPersona().setEdad(edad);
                familiar.getLaPersona().setGenero(genero);
                familiar.setTelefono(telefono);
                familiar.setParentesco(parentesco);
            }
            else
                System.out.println("ERROR: El Contacto que se desea modificar no es un Contacto Familiar ");
        }
        else
            System.out.println("ERROR: La cédula no pertenece a ningún contacto en la agenda!");
    }

    public void modificarContactoNegocios(int cedula, String nombre, int edad,
                                          int genero, String telefono,
                                          String correoElectronico) {

        Contacto contacto = buscarContacto(cedula);

        if (contacto != null) {
            if (contacto instanceof ContactoNegocios) {
                ContactoNegocios negocios = (ContactoNegocios) contacto;

                negocios.getLaPersona().setNombre(nombre);
                negocios.getLaPersona().setEdad(edad);
                negocios.getLaPersona().setGenero(genero);
                negocios.setTelefono(telefono);
                negocios.setCorreoElectronico(correoElectronico);
            }
            else
                System.out.println("ERROR: El Contacto que se desea modificar no es un Contacto de Negocios ");
        }
        else
            System.out.println("ERROR: La cédula no pertenece a ningún contacto en la agenda!");
    }


    @Override
    public String toString() {
        return this.listaEventos.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\n"));
    }

    /*@Override
    public String toString() {
        String resultado = "";

        for (int i = 0; i < this.listaContactos.size(); i++) {
            resultado += this.listaContactos.get(i).toString() + "\n";
        }

        return resultado;
    }*/
}