package exceptions;

public class SalonNoExisteException extends RuntimeException {
    public SalonNoExisteException(String message) {
        super(message);
    }
}
