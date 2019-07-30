package Genetic_Algorithm.NoiseStrategy;

import DataStructure.Matrix;
import Exceptions.VariableBoundsException;
import GameMap.GameMap;

import java.util.ArrayList;
import java.util.Random;

import static GameMap.TileList.*;

public class CellularAutomateNoise implements NoiseStrategy {

    public static final String IMPLEMENTATION = "cellular";

    @Override
    //TODO unlike when it was a separate thing, here I will noise maps and then CA them (less functionality
    // but its irrelevant, there will only be one CA
    public ArrayList<GameMap> createNoise(int width, int height, int numberOfMaps, double odds) {
        ArrayList<GameMap> noisyMaps;
        noisyMaps = preNoise(width, height, numberOfMaps, odds);

        return noisyMaps;
    }

    private ArrayList<GameMap> preNoise(int width, int height, int numberOfMaps, double odds) {
        if (odds <= 0 || odds > 1) throw new VariableBoundsException(0, 1.0);
        Random random = new Random();

        ArrayList<GameMap> gameMapList = new ArrayList<>();
        for (int i = 0; i < numberOfMaps; i++) {
            gameMapList.add(new GameMap(width, height));

            for (GameMap gameMap : gameMapList) {

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (random.nextDouble() > odds) gameMap.getMapMatrix().put(x, y, CORRIDOR);
                        else gameMap.getMapMatrix().put(x, y, WALL);
                    }
                }
                cellularAutomata(gameMap);
            }
        }
        return gameMapList;
    }


    private void cellularAutomata(GameMap gameMap){
        int count = 0;
        int mapWidth = gameMap.getMapWidth();
        int mapHeight = gameMap.getMapHeight();

        Matrix map = gameMap.getMapMatrix();

        Matrix newMatrix = new Matrix(mapWidth, mapHeight);
        newMatrix.fillMatrix(NOT_A_NUMBER);

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (map.getUp(x, y) == WALL) count++;
                if (map.getDown(x, y) == WALL) count++;
                if (map.getRight(x, y) == WALL) count++;
                if (map.getLeft(x, y) == WALL) count++;

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

                if (count == 4 && map.getElement(x, y) == WALL) newMatrix.put(x, y, WALL);
                else if (count > 4) newMatrix.put(x, y, WALL);
                else newMatrix.put(x, y, CORRIDOR);

                count = 0;
            }
        }
        gameMap.setMapMatrix(newMatrix);
    }
}
