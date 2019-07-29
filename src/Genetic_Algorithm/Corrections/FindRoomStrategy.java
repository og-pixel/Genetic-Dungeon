package Genetic_Algorithm.Corrections;

import DataStructure.Matrix;
import Map.GameMap;
import Algorithms.Algorithms;

import static Map.TileList.*;

public class FindRoomStrategy implements CorrectionImp {

    public static final String IMPLEMENTATION = "find_room";

    @Override
    public void correctMap(GameMap gameMap) {
        Matrix matrix = gameMap.getMapMatrix();

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
                        gameMap.setFitnessScore(gameMap.getFitnessScore() + room.getVolume());
                        gameMap.setCorrectionsFound(gameMap.getCorrectionsFound() + 1);
                    }
                }
            }
    }
}
