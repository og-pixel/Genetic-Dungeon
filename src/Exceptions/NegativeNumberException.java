package Exceptions;

public class NegativeNumberException extends RuntimeException {
    public NegativeNumberException() {
        super("Negative number");
    }

    public NegativeNumberException(String message) {
        super(message);
    }
}
