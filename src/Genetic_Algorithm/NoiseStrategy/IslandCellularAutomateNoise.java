package Genetic_Algorithm.NoiseStrategy;

import Algorithms.Algorithms;
import Chromosome.*;
import DataStructure.Matrix;
import DataStructure.MutabilityMatrix;
import Exceptions.VariableBoundsException;

import java.util.ArrayList;
import java.util.Random;

import static Chromosome.TileList.*;
import static Chromosome.TileList.CORRIDOR;

public class IslandCellularAutomateNoise implements NoiseStrategy {

    public static final String IMPLEMENTATION = "cellular_island";

    @Override
    public ArrayList<Chromosome> createNoise(int width, int height, int numberOfMaps, double odds) {
        ArrayList<Chromosome> noisyMaps;
        noisyMaps = noiseMap(width, height, numberOfMaps, odds);

        return noisyMaps;
    }

    private ArrayList<Chromosome> noiseMap(int width, int height, int numberOfMaps, double odds) {
        if (odds <= 0 || odds > 1) throw new VariableBoundsException(0, 1.0);
        Random random = new Random();

        ArrayList<Chromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < numberOfMaps; i++) {
            chromosomeList.add(new Chromosome(width, height));

            for (Chromosome chromosome : chromosomeList) {

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (random.nextDouble() > odds) chromosome.getMapMatrix().put(x, y, CORRIDOR);
                        else chromosome.getMapMatrix().put(x, y, WALL);
                    }
                }
                cellularAutomate(chromosome);
            }
        }
        return chromosomeList;
    }


    private void cellularAutomate(Chromosome chromosome) {
        int count = 0;
        int mapWidth = chromosome.getMapWidth();
        int mapHeight = chromosome.getMapHeight();

        Matrix map = chromosome.getMapMatrix();

        Matrix newMatrix = new MutabilityMatrix(mapWidth, mapHeight);
        newMatrix.fillMatrix(NOT_A_NUMBER);

        for (int i = 0; i < 3; i++) {

            for (int y = 0; y < mapHeight; y++) {
                for (int x = 0; x < mapWidth; x++) {
                    if (map.getUp(x, y) == WALL) count++;
                    if (map.getDown(x, y) == WALL) count++;
                    if (map.getRight(x, y) == WALL) count++;
                    if (map.getLeft(x, y) == WALL) count++;

                    if (map.getUpLeft(x, y) == WALL) count++;
                    if (map.getUpRight(x, y) == WALL) count++;
                    if (map.getDownLeft(x, y) == WALL) count++;
                    if (map.getDownRight(x, y) == WALL) count++;

                    if (count == 4 && map.getElement(x, y) == WALL) newMatrix.put(x, y, WALL);
                    else if (count > 4) newMatrix.put(x, y, WALL);
                    else newMatrix.put(x, y, CORRIDOR);

                    count = 0;
                }
            }
            map = (Matrix) Algorithms.deepClone(newMatrix);
        }

        chromosome.setMapMatrix(newMatrix);
    }
}
