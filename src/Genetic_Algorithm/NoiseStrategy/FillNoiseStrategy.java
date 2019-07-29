package Genetic_Algorithm.NoiseStrategy;

import Map.*;

import java.util.ArrayList;
import static Map.TileList.*;

public class FillNoiseStrategy implements NoiseImp {

    public static final String IMPLEMENTATION = "fill";

    @Override
    public ArrayList<GameMap> createNoise(int width, int height, int numberOfMaps, double odds) {
         ArrayList<GameMap> gameMapList = new ArrayList<>();
            for (int i = 0; i < numberOfMaps; i++) {
                gameMapList.add(new GameMap(width, height));

                gameMapList.get(i).getMapMatrix().fillMatrix(WALL);

                gameMapList.get(i).createStartPosition();
                gameMapList.get(i).createEndPosition();
            }
            return gameMapList;
    }
}
