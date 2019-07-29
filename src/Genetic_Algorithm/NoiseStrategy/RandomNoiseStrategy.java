package Genetic_Algorithm.NoiseStrategy;

import Exceptions.VariableBoundsException;
import GameMap.*;

import java.util.ArrayList;
import java.util.Random;
import static GameMap.TileList.*;

public class RandomNoiseStrategy implements NoiseStrategy {

    public static final String IMPLEMENTATION = "random_noise";

    @Override
    public ArrayList<GameMap> createNoise(int width, int height, int numberOfMaps, double odds) {
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
        return gameMapList;
    }
}
