package agenda;

public interface Exportable
{
    public abstract String converToXml();
    public abstract String converToJSON();
}
// porque una clase no puede extender en java mas de una superclase (herencia multiple)=
// R/ heredar una caracteristica mas de una clase tiene un problema y se llama igua, si hay 2
// comportamientos con el mismo nombre no se sabe cual escoger no se puede ambas,
// correr como perro o como humano ej,
// no es que no se pueda es que es complejo
// R/ da lo mismo heredar la misma clase de un metodo u otro en la interfas porque no tiene cuerpo,
// asi que da igual porque dice que hay que hacerlo pero no el como (el cod)