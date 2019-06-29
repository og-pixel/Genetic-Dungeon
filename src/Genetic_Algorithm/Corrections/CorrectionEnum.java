package Genetic_Algorithm.Corrections;

import Algorithms.Algorithms;
import DataStructure.Matrix;
import Map.*;

public enum CorrectionEnum implements CorrectionImp, TileList {
    //Silly and not working right atm
    FIND_HOLES("find_holes"){
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
    },
    FIND_ROOM("find_room") {
        @Override
        public void correctMap(Map map) {
            Matrix matrix = map.getMapMatrix();

            //TODO rooms need to be a template too
            //TODO room hard expect door at the right side (hole)
            Matrix room = new Matrix(5, 5);
            room.replaceRow(0, new long[]{UNIVERSAL, WALL, UNIVERSAL, WALL, UNIVERSAL});
            room.replaceRow(1, new long[]{WALL, CORRIDOR, CORRIDOR, CORRIDOR, WALL});
            room.replaceRow(2, new long[]{UNIVERSAL, CORRIDOR, CORRIDOR, CORRIDOR, UNIVERSAL});
            room.replaceRow(3, new long[]{WALL, CORRIDOR, CORRIDOR, CORRIDOR, WALL});
            room.replaceRow(4, new long[]{UNIVERSAL, WALL, UNIVERSAL, WALL, UNIVERSAL});

            Matrix cutMatrix;
            for (int y = 0; y < matrix.getHeight() - room.getHeight(); y = y + room.getHeight()) {
                for (int x = 0; x < matrix.getWidth() - room.getWidth(); x = x + room.getWidth()) {
                    cutMatrix = matrix.cutMatrix(x, y, room.getWidth(), room.getHeight());
                    if (Algorithms.getHammingDistance(room, cutMatrix) <= 0) {
                        map.setFitnessScore(map.getFitnessScore() + room.getVolume());
                        map.setCorrectionsFound(map.getCorrectionsFound() + 1);
                    }
                }
            }
        }
    };
    //TODO add
    // MACHINE_LEARNING_MAPS()

    CorrectionEnum(String implementationName){
        this.implementationName = implementationName;
    }
    private String implementationName;
    public String getImplementationName() {
        return implementationName;
    }
}
