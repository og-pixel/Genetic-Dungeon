package Genetic_Algorithm.CorrectionStrategy;

import DataStructure.Matrix;
import Chromosome.*;
import Algorithms.Algorithms;

import static Chromosome.TileList.*;

public class FindRoomStrategy implements CorrectionStrategy {

    public static final String IMPLEMENTATION = "find_room";

    @Override
    public void correctMap(Chromosome chromosome) {
        Matrix matrix = chromosome.getMapMatrix();

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
                        chromosome.setFitnessScore(chromosome.getFitnessScore() + room.getVolume());
                        chromosome.setCorrectionsFound(chromosome.getCorrectionsFound() + 1);
                    }
                }
            }
    }
}
