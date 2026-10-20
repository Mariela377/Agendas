package agenda;

public class Persona {
    private String nombre;
    private int edad;
    private int genero;

    public Persona(String nombre, int edad, int genero) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
    }

    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        String generoTexto;
        switch (genero) {
            case 1:  generoTexto = "Masculino"; break;
            case 2:  generoTexto = "Femenino";  break;
            case 3:  generoTexto = "Otro";      break;
            default: generoTexto = "No indicado";
        }
        return "Nombre: " + nombre + " | Edad: " + edad + " | Género: " + generoTexto;
    }
}