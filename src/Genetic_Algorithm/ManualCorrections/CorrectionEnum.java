package Genetic_Algorithm.ManualCorrections;

import Algorithms.Matrix;
import Dungeon.*;

public enum CorrectionEnum implements CorrectionImp, TileList {
    FIND_HOLES{
        @Override
        public void correct(Dungeon dungeon) {
            Matrix matrix = dungeon.getDungeonMatrix();

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
}
