package Exceptions;

public class MatrixWrongDimensionsException extends RuntimeException {
    public MatrixWrongDimensionsException() {
        super("Matrices's dimensions must match!");
    }

    public MatrixWrongDimensionsException(String message) {
        super(message);
    }
}
