package exceptions;

public class ValidationException extends RuntimeException {
    public static Object obj;

    public ValidationException(String message, Object object) {
        super(message);
        obj = object;
    }
}
