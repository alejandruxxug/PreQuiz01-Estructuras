package exceptions;

public class ReservaNoExisteException extends RuntimeException {
    public ReservaNoExisteException(String message) {
        super(message);
    }
}
