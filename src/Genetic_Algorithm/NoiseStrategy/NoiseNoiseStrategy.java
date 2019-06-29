package Genetic_Algorithm.NoiseStrategy;

import Exceptions.VariableBoundsException;
import Map.*;

import java.util.ArrayList;
import java.util.Random;

public class NoiseNoiseStrategy implements NoiseImp, TileList {

    public static final String IMPLEMENTATION = "noise";

    @Override
    public ArrayList<Map> createNoise(int width, int height, int numberOfMaps, double odds) {
        if (odds < 0.1 || odds > 1) throw new VariableBoundsException(0.1, 1.0); //TOOD i am not sure if i need that

        ArrayList<Map> mapList = new ArrayList<>();
        for (int i = 0; i < numberOfMaps; i++) {
            mapList.add(new Map(width, height));

            Random random = new Random();
            for (Map map : mapList) {

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (random.nextDouble() > odds) map.getMapMatrix().put(x, y, CORRIDOR);
                        else map.getMapMatrix().put(x, y, WALL);
                    }
                }
            }
            mapList.get(i).createStartPosition();
            mapList.get(i).createEndPosition();
        }
        return mapList;
    }
}
