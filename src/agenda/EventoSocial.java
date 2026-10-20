package agenda;

import java.time.LocalDate;

public class EventoSocial extends Evento {
    String lugar;

    public EventoSocial (String lugar, LocalDate fecha, String descripcion){
        super(descripcion, fecha);
        this.lugar = lugar;
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
        return "[EVENTO SOCIAL] " + super.toString() + " | Lugar: " + lugar;
    }
}
//clases abstractas, metodos abstractos, interface, ect al proyecto,
// sobre tod0 si las clases no son abstractas usar interface
