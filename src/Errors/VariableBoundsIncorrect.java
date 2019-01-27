package Errors;

public class VariableBoundsIncorrect extends RuntimeException{
    public VariableBoundsIncorrect(int lowerBound, int upperBound) {
        super("Variable bounds incorrect, expected " + lowerBound + " and " + upperBound);
    }
    public VariableBoundsIncorrect(String message) {
        super(message);
    }
}
