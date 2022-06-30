package exceptions;


public class ExceptionGeneric extends RuntimeException{
     public static MessageError messageError;

    public ExceptionGeneric(String message, Long status, String title) {
        super(message);

        messageError =  MessageError.getError(message, title, status);
    }    
}
