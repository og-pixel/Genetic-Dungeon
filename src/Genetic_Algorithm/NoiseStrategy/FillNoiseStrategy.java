package Genetic_Algorithm.NoiseStrategy;

import Map.*;

import java.util.ArrayList;
import static Map.TileList.*;

public class FillNoiseStrategy implements NoiseImp {

    public static final String IMPLEMENTATION = "fill";

    @Override
    public ArrayList<Map> createNoise(int width, int height, int numberOfMaps, double odds) {
         ArrayList<Map> mapList = new ArrayList<>();
            for (int i = 0; i < numberOfMaps; i++) {
                mapList.add(new Map(width, height));

                mapList.get(i).getMapMatrix().fillMatrix(WALL);

                mapList.get(i).createStartPosition();
                mapList.get(i).createEndPosition();
            }
            return mapList;
    }
}
