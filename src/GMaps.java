import Chromosome.Chromosome;
import DataStructure.Matrix;
import DataStructure.MutabilityMatrix;
import Genetic_Algorithm.CorrectionStrategy.AddPermanentWallsStrategy;
import Genetic_Algorithm.CorrectionStrategy.CorrectionStrategy;
import Genetic_Algorithm.MutatorStrategy.DefaultMutatorStrategy;
import Genetic_Algorithm.MutatorStrategy.MutatorStrategy;

import static Chromosome.TileList.CORRIDOR;
import static Chromosome.TileList.WALL;

public class GMaps {
    public static void main(String[] args){


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
//        new Interpreter(args);
    }
}
