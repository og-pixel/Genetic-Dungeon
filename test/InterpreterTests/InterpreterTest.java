package InterpreterTests;

import Chromosome.Chromosome;
import DataStructure.Matrix;
import DataStructure.MutabilityMatrix;
import Genetic_Algorithm.CorrectionStrategy.AddPermanentWallsStrategy;
import Genetic_Algorithm.CorrectionStrategy.CorrectionStrategy;
import Genetic_Algorithm.MutatorStrategy.DefaultMutatorStrategy;
import Genetic_Algorithm.MutatorStrategy.MutatorStrategy;
import Main.Interpreter;
import org.junit.jupiter.api.*;
import Algorithms.Algorithms;


import java.io.IOException;

import static Chromosome.TileList.CORRIDOR;
import static Chromosome.TileList.WALL;


public class InterpreterTest  {


    @Test
    public void test(){
        Matrix matrix = new MutabilityMatrix(10, 10);
        matrix.fillMatrix(CORRIDOR);

        System.out.println(matrix);
        Chromosome chromosome = new Chromosome(matrix);


        CorrectionStrategy correctionStrategy = new AddPermanentWallsStrategy();
        correctionStrategy.correctMap(chromosome);

        MutatorStrategy mutatorStrategy = new DefaultMutatorStrategy(0.5);
        mutatorStrategy.mutateDungeon(chromosome);

        System.out.println(matrix);

        mutatorStrategy.mutateDungeon(chromosome);

        System.out.println(matrix);

        mutatorStrategy.mutateDungeon(chromosome);
        System.out.println(matrix);

        mutatorStrategy.mutateDungeon(chromosome);
        System.out.println(matrix);


//        Matrix matrix = new MutabilityMatrix(10, 15);
//        matrix.fillMatrix(WALL);
//        Chromosome chromosome = new Chromosome(matrix);
//
//        Matrix room = new MutabilityMatrix(3, 3);
//        room.fillMatrix(CORRIDOR);
//
//        matrix.fillMatrixArea(2, 2, room);
//
//        System.out.println(chromosome.getMapMatrix());
//
//        try {
//            Algorithms.writeToFile("/home/u1659885/try", chromosome);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    @Test
    public void multiplicationOfZeroIntegersShouldReturnZero() {
        Assertions.assertTrue(true);


//        String[] args = new String[]{"--noise"};
//        Interpreter interpreter = new Interpreter(args);


    }
}
