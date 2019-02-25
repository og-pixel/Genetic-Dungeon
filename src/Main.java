import Algorithms.Algorithms;
import Algorithms.Matrix;
import Dungeon.*;

public class Main {
    public static void main(String[] args){
//        new Interpreter(args);
        Matrix<Integer> integerMatrix = new Matrix<Integer>(50,50);
        integerMatrix.fillMatrix(1);
        System.out.println(integerMatrix);
    }
}
