package exceptions;

public class HorarioInvalidoException extends RuntimeException{
    public HorarioInvalidoException(String message) {
        super(message);
    }
}
