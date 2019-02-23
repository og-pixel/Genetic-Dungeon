package Exceptions;

public class VariableBoundsException extends RuntimeException{
    public VariableBoundsException(int lowerBound, int upperBound) {
        super("Variable bounds incorrect, expected " + lowerBound + " to " + upperBound);
    }
    public VariableBoundsException(double lowerBound, double upperBound) {
        super("Variable bounds incorrect, expected " + lowerBound + " to " + upperBound);
    }
    public VariableBoundsException(String message) {
        super(message);
    }
}
