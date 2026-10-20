package agenda;

public abstract class Contacto extends ElementosAgenda {
    private Persona laPersona;
    private String telefono;
    private int cedula;

    public Contacto(Persona laPersona, String telefono, int cedula) {
        this.laPersona = laPersona; // ya me dan la Persona armada
        this.telefono = telefono;
        this.cedula = cedula;
    }

    public Contacto(String nombre, int edad, int genero, String telefono, int cedula) {
        this.laPersona = new Persona();  // creo una Persona vacía
        this.laPersona.setNombre(nombre);  // y la relleno con setters
        this.laPersona.setEdad(edad);
        this.laPersona.setGenero(genero);
        //this.laPersona = new Persona(nombre, edad, genero);
        this.telefono = telefono;
        this.cedula = cedula;
    }

    public Persona getLaPersona() {
        return laPersona;
    }  //es la puerta de entrada para llegar hasta los datos de la persona desde afuera

    public void setLaPersona(Persona laPersona) {
        this.laPersona = laPersona;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    //public abstract String converToXml(); //agarra lo que le da y lo pasa en XML NO LLEVA COD
    //public abstract String converToJSON();

    @Override
    public String toString() {
        return "Cédula: " + cedula + " | " + laPersona + " | Teléfono: " + telefono;
    }
}
