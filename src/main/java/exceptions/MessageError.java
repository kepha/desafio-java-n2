package exceptions;


public class MessageError {

    private String titulo;
    private Long status;
    private Long timestamp;
    private String error;

    public MessageError(String titulo, Long status, Long timestamp, String error) {
        this.titulo = titulo;
        this.status = status;
        this.timestamp = timestamp;
        this.error = error;
    }

    public static MessageError getError(String msg, String titulo, Long status) {

        return new MessageError(
                titulo,
                status,
                System.currentTimeMillis(),
                msg);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
