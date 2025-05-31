package mx.gob.sev.serv.exception;

public class ValidacionUsuarioException extends RuntimeException {
    public ValidacionUsuarioException(String message) {
        super(message);
    }

    public ValidacionUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }
}