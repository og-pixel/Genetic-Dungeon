package Exceptions;

public class SumErrorException extends Exception{
    public SumErrorException(int sumExpected){
        super("Number expected error, expected: " + sumExpected);
    }

    public SumErrorException(String message) {
        super(message);
    }
}
