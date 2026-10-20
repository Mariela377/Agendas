package agenda;

public abstract class ElementosAgenda implements Exportable{
    Evento evento;
    Contacto contacto;

    //public abstract String converToXml(); //agarra lo que le da y lo pasa en XML NO LLEVA COD
    //public abstract String converToJSON();

    @Override
    public String toString() {
        return "ElementosAgenda{" +
                "contacto=" + contacto +
                ", evento=" + evento +
                '}';
    }
}
