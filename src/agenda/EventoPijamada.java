package agenda;

import java.time.LocalDate;
import java.util.LinkedList;

public class EventoPijamada extends Evento{
    private LinkedList<String> asistentes;
    private LocalDate fecha;
    private String lugar;
    private String descripcion;

    public EventoPijamada(LocalDate fecha, String lugar, LinkedList<String> asistentes, String descripcion){
        super(descripcion, fecha);
        this.asistentes = asistentes;
        this.fecha =  fecha;
        this.lugar = lugar;
    }

    @Override
    public String converToXml() {

        return "";
    }

    @Override
    public String converToJSON() {
        return "";
    }
}
