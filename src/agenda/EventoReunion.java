package agenda;

import java.time.LocalDate;

public class EventoReunion extends Evento {
    int countPersonas;

    public EventoReunion(int countPersonas, LocalDate fecha, String descripcion){
        super(descripcion, fecha);
        this.countPersonas = countPersonas;
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
        return "[REUNIÓN] " + super.toString() + " | Personas: " + countPersonas;
    }
}
