package Genetic_Algorithm.CorrectionStrategy;

import DataStructure.Matrix;
import Chromosome.*;
import static Chromosome.TileList.*;

public class FindHolesStrategy implements CorrectionStrategy {

    public static final String IMPLEMENTATION = "find_holes";

    @Override
    public void correctMap(Chromosome chromosome) {
        Matrix matrix = chromosome.getMapMatrix();

            for (int y = 0; y < matrix.getHeight(); y++) {
                for (int x = 0; x < matrix.getWidth(); x++) {
                    if(matrix.getElement(x, y) == CORRIDOR){
                        if(matrix.getUp(x, y) == WALL &&
                            matrix.getRight(x, y) == WALL &&
                            matrix.getDown(x, y) == WALL &&
                            matrix.getLeft(x, y) == WALL){
                            matrix.put(x, y, WALL);
                        }
                    }
                }
            }
    }
}
