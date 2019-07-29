package Genetic_Algorithm.Corrections;

import DataStructure.Matrix;
import Map.*;
import static Map.TileList.*;

public class FindHolesStrategy implements CorrectionImp {

    public static final String IMPLEMENTATION = "find_holes";

    @Override
    public void correctMap(Map map) {
        Matrix matrix = map.getMapMatrix();

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
