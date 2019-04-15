package Genetic_Algorithm.ManualCorrections;

import Algorithms.Algorithms;
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
    },
    FIND_ROOM{
        @Override
        public void correct(Dungeon dungeon) {
             Matrix matrix = dungeon.getDungeonMatrix();

             //TODO rooms need to be a template too
            //TODO room hard expect door at the right side (hole)
            Matrix room = new Matrix(5,5);
            room.replaceRow(0, new long[]{WALL, WALL, WALL, WALL, WALL});
            room.replaceRow(1, new long[]{WALL, CORRIDOR, CORRIDOR, CORRIDOR, WALL});
            room.replaceRow(2, new long[]{WALL, CORRIDOR, CORRIDOR, CORRIDOR, CORRIDOR});
            room.replaceRow(3, new long[]{WALL, CORRIDOR, CORRIDOR, CORRIDOR, WALL});
            room.replaceRow(4, new long[]{WALL, WALL, CORRIDOR, WALL, WALL});

            Matrix cutMatrix = null;
            for (int y = 0; y < matrix.getHeight() - room.getHeight(); y = y + room.getHeight()) {
                for (int x = 0; x < matrix.getWidth() - room.getWidth(); x = x + room.getWidth()) {
                    cutMatrix = matrix.getPartOfMatrix(x, y, room.getWidth(), room.getHeight());
                    if(Algorithms.getHammingDistance(cutMatrix, room) <= 3){
//                        System.out.println("FOUND ONE");
                        dungeon.setScore(dungeon.getScore() + room.getSize());
                        dungeon.setCorrectionsFound(dungeon.getCorrectionsFound() + 1);
                    }
                }
            }
        }
    }
}
