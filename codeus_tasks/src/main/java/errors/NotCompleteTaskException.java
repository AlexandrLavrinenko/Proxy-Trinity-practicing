package errors;

public class NotCompleteTaskException extends RuntimeException {
    public NotCompleteTaskException(String message) {
        super(message);
    }
}
