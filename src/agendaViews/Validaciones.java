package agendaViews;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Validaciones {

    // true si el texto está vacío o solo tiene espacios
    public static boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    // Solo letras (con tildes y ñ) y espacios
    public static boolean soloLetras(String texto) {
        return !estaVacio(texto) && texto.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+");
    }

    // Formato 8888-8888
    public static boolean esTelefonoValido(String texto) {
        return !estaVacio(texto) && texto.trim().matches("\\d{4}-\\d{4}");
    }

    // Exactamente 9 dígitos y que no empiece con 0
    public static boolean esCedulaValida(String texto) {
        return !estaVacio(texto) && texto.trim().matches("[1-9]\\d{8}");
    }

    // algo@algo.com
    public static boolean esCorreoValido(String texto) {
        return !estaVacio(texto)
                && texto.trim().matches("^[\\w.-]+@[\\w-]+(\\.[\\w-]+)*\\.[a-zA-Z]{2,}$");
    }

    // Convierte "dd/mm/aaaa" a fecha. Si la fecha no existe (ej. 31/02/2026) devuelve null
    public static LocalDate convertirFecha(String texto) {
        if (estaVacio(texto)) return null;
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            return LocalDate.parse(texto.trim(), formato);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}