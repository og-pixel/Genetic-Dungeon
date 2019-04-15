package Genetic_Algorithm.Population;

import Map.*;
import Exceptions.VariableBoundsException;

import java.util.ArrayList;
import java.util.Random;

public enum NoiseEnum implements NoiseImp, TileList{
    FILL {//TODO only problem with this implementation is that odds are in function, gotta move it to enum "constructor"
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
    },
    NOISE {
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
}
