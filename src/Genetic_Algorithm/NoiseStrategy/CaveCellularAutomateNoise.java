package Genetic_Algorithm.NoiseStrategy;

import DataStructure.Matrix;
import Exceptions.VariableBoundsException;
import Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Random;

import Algorithms.Algorithms;

import static Chromosome.TileList.*;

public class CaveCellularAutomateNoise implements NoiseStrategy {

    public static final String IMPLEMENTATION = "cellular";

    @Override
    public ArrayList<Chromosome> createNoise(int width, int height, int numberOfMaps, double odds) {
        ArrayList<Chromosome> noisyMaps;
        noisyMaps = preNoise(width, height, numberOfMaps, odds);

        return noisyMaps;
    }

    private ArrayList<Chromosome> preNoise(int width, int height, int numberOfMaps, double odds) {
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
        System.out.println("time elapsed: " + Algorithms.TIME_ELAPSED);
        return chromosomeList;
    }


    private void cellularAutomate(Chromosome chromosome){
        int count = 0;
        int mapWidth = chromosome.getMapWidth();
        int mapHeight = chromosome.getMapHeight();

        Matrix map = chromosome.getMapMatrix();

        Matrix newMatrix = new Matrix(mapWidth, mapHeight);
        newMatrix.fillMatrix(NOT_A_NUMBER);

        for (int i = 0; i < 3; i++) {


            for (int y = 0; y < mapHeight; y++) {
                for (int x = 0; x < mapWidth; x++) {
                    if (map.getUp(x, y) == WALL) count++;
                    if (map.getDown(x, y) == WALL) count++;
                    if (map.getRight(x, y) == WALL) count++;
                    if (map.getLeft(x, y) == WALL) count++;

                    //TODO this part is a replacement for commented text, just make sure its working
//                if (map.getUpLeft(x, y) == WALL) count++;
//                if (map.getUpRight(x, y) == WALL) count++;
//                if (map.getDownLeft(x, y) == WALL) count++;
//                if (map.getDownRight(x, y) == WALL) count++;

                    if ((x - 1) > 0 && (y - 1) > 0) {
                        if (map.getElement(x - 1, y - 1) == WALL) count++;
                    }

                    if ((x + 1) < mapWidth && (y - 1) > 0) {
                        if (map.getElement(x + 1, y - 1) == WALL) count++;
                    }

                    if ((x + 1) < mapWidth && (y + 1) < mapHeight) {
                        if (map.getElement(x + 1, y + 1) == WALL) count++;
                    }

                    if ((x - 1) > 0 && (y + 1) < mapHeight) {
                        if (map.getElement(x - 1, y + 1) == WALL) count++;
                    }


                    if (map.getElement(x, y) == WALL) {
                        if (count < 2) newMatrix.put(x, y, CORRIDOR); //Dead
                        else if (count > 3) newMatrix.put(x, y, CORRIDOR); //Dead
                        else newMatrix.put(x, y, WALL); //Still Alive
                    } else if (map.getElement(x, y) == CORRIDOR) {
                        if (count == 3) newMatrix.put(x, y, WALL); //"Birth"
                        else newMatrix.put(x, y, CORRIDOR); //Stay Dead
                    }

//                if (count == 4 && map.getElement(x, y) == WALL) newMatrix.put(x, y, WALL);
//                else if (count > 4) newMatrix.put(x, y, WALL);
//                else newMatrix.put(x, y, CORRIDOR);

//                System.out.println(newMatrix);

                    count = 0;
                }
            }
            map = (Matrix) Algorithms.deepClone(newMatrix);
        }
        chromosome.setMapMatrix(newMatrix);
    }
}
