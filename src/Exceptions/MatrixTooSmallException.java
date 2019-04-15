package Exceptions;

public class MatrixTooSmallException extends RuntimeException{
    public MatrixTooSmallException() {
        super("Matrix's dimensions are too small");
    }

    public MatrixTooSmallException(String message) {
        super(message);
    }
}
