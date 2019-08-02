package Main;

import Algorithms.Algorithms;
import Chromosome.Chromosome;
import DataStructure.Matrix;
import DataStructure.MutabilityMatrix;

import java.io.IOException;

import static Chromosome.TileList.CORRIDOR;
import static Chromosome.TileList.WALL;

public class GMaps {
    public static void main(String[] args){

        //TODO dont delete, move to tests, that's how I will run all sorts of tests later
//        Matrix matrix = new MutabilityMatrix(10, 10);
//        matrix.fillMatrix(CORRIDOR);
//
//        System.out.println(matrix);
//        Chromosome chromosome = new Chromosome(matrix);
//
//
//        CorrectionStrategy correctionStrategy = new AddPermanentWallsStrategy();
//        correctionStrategy.correctMap(chromosome);
//
//        MutatorStrategy mutatorStrategy = new DefaultMutatorStrategy(0.5);
//        mutatorStrategy.mutateDungeon(chromosome);
//
//        System.out.println(matrix);
//
//        mutatorStrategy.mutateDungeon(chromosome);
//
//        System.out.println(matrix);
//
//        mutatorStrategy.mutateDungeon(chromosome);
//        System.out.println(matrix);
//
//        mutatorStrategy.mutateDungeon(chromosome);
//        System.out.println(matrix);


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


        new Interpreter(args);
    }
}
