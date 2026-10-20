package agenda;

import java.time.LocalDate;

public abstract class Evento extends ElementosAgenda {
    private LocalDate fecha;
    private String descripcion;

    public Evento(String descripcion, LocalDate fecha) {
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    //esto es para que si se implementan mas clases hijas tengan que tener este compromiso y convertir a xml
    //public abstract String converToXml(); //agarra lo que le da y lo pasa en XML NO LLEVA COD
    //public abstract String converToJSON();
    //solo se pone abstract si la clase es abstracta

    @Override
    public String toString() {
        return "Evento{" +
                "descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", contacto=" + contacto +
                ", evento=" + evento +
                "} " + super.toString();
    }
}
