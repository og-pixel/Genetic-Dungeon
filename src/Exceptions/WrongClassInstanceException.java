package Exceptions;

public class WrongClassInstanceException extends RuntimeException {
    public WrongClassInstanceException() {
        super("");
    }
    public WrongClassInstanceException(String message) {
        super("This object needs to be: " + message);
    }
}
