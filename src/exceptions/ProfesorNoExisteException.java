package exceptions;

public class ProfesorNoExisteException extends RuntimeException {
    public ProfesorNoExisteException(String message) {
        super(message);
    }
}
