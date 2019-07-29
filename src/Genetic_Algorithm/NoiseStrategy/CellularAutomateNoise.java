package Genetic_Algorithm.NoiseStrategy;

import DataStructure.Matrix;
import Exceptions.VariableBoundsException;
import GameMap.GameMap;

import java.util.ArrayList;
import java.util.Random;

import static GameMap.TileList.CORRIDOR;
import static GameMap.TileList.WALL;

public class CellularAutomateNoise implements NoiseStrategy {
    @Override
    //TODO unlike when it was a separate thing, here I will noise maps and then CA them (less functionality
    // but its irrelevant, there will only be one CA
    public ArrayList<GameMap> createNoise(int width, int height, int numberOfMaps, double odds) {

        //TODO this for now is default random noise generator
        if (odds < 0.1 || odds > 1) throw new VariableBoundsException(0.1, 1.0); //TOOD i am not sure if i need that

        ArrayList<GameMap> gameMapList = new ArrayList<>();
        for (int i = 0; i < numberOfMaps; i++) {
            gameMapList.add(new GameMap(width, height));

            Random random = new Random();
            for (GameMap gameMap : gameMapList) {

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (random.nextDouble() > odds) gameMap.getMapMatrix().put(x, y, CORRIDOR);
                        else gameMap.getMapMatrix().put(x, y, WALL);
                    }
                }
            }
            gameMapList.get(i).createStartPosition();
            gameMapList.get(i).createEndPosition();
        }

        ArrayList<GameMap> k = new RandomNoiseStrategy().createNoise(width, height, numberOfMaps, odds);



        //TODO this is modified CA
//        Matrix newMap = new Matrix(mapWidth, mapHeight);
//        newMap.fillMatrix(-1);
//        for (int i = 0; i < 5; i++) {
//
//
//            //Very silly implementation
//            for (int y = 0; y < mapHeight; y++) {
//                for (int x = 0; x < mapWidth; x++) {
//                    int count = 0;
//
//                    if (map.getUp(x, y) == WALL) count++;
//                    if (map.getDown(x, y) == WALL) count++;
//                    if (map.getRight(x, y) == WALL) count++;
//                    if (map.getLeft(x, y) == WALL) count++;
//
//                    if ((x - 1) > 0 && (y - 1) > 0) {
//                        if (map.getElement(x - 1, y - 1) == WALL) count++;
//                    }
//
//                    if ((x + 1) < mapWidth && (y - 1) > 0) {
//                        if (map.getElement(x + 1, y - 1) == WALL) count++;
//                    }
//
//                    if ((x + 1) < mapWidth && (y + 1) < mapHeight) {
//                        if (map.getElement(x + 1, y + 1) == WALL) count++;
//                    }
//
//                    if ((x - 1) > 0 && (y + 1) < mapHeight) {
//                        if (map.getElement(x - 1, y + 1) == WALL) count++;
//                    }
//
//                    if (count == 4 && map.getElement(x, y) == WALL) newMap.put(x, y, WALL);
//                    else if (count > 4) newMap.put(x, y, WALL);
//                    else newMap.put(x, y, CORRIDOR);
//                }
//            }
//            map = newMap;
//        }
        throw new RuntimeException("This class is not even finished");
//        return null;
    }
}
