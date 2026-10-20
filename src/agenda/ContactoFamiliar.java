package agenda;

public class ContactoFamiliar extends Contacto{
    private String parentesco;
//La palabra extends le dice a Java "hereda todos los atributos y métodos de Contacto"
    public ContactoFamiliar(Persona laPersona, String telefono, int cedula, String parentesco) {
        super(laPersona, telefono, cedula);   // ← llama al constructor del padre
        this.parentesco = parentesco;
    }

    public ContactoFamiliar(String nombre, int edad, int genero, String telefono, int cedula, String parentesco) {
        super(nombre, edad, genero, telefono, cedula);
        this.parentesco = parentesco;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    @Override
    public String converToJSON() {
        return "";
    }

    @Override
    public String converToXml() {
        return "";
    }

    @Override
    public String toString() {
        return "[CONTACTO FAMILIAR] " + super.toString() + " | Parentesco: " + parentesco;
    }
}