package Genetic_Algorithm.Population;

import Dungeon.*;
import Exceptions.VariableBoundsException;

import java.util.ArrayList;
import java.util.Random;

public enum NoiseEnum implements NoiseImp, TileList{
    FILL {//TODO only problem with this implementation is that odds are in function, gotta move it to enum "constructor"
        @Override
        public ArrayList<Dungeon> createNoise(int width, int height, int numberOfMaps, double odds) {
            ArrayList<Dungeon> mapList = new ArrayList<>();

            for (int i = 0; i < numberOfMaps; i++) {
                mapList.add(new Dungeon(width, height));

                mapList.get(i).getDungeonMatrix().fillMatrix(WALL);

                mapList.get(i).createStartPosition();
                mapList.get(i).createEndPosition();
            }
            return mapList;
        }
    },
    NOISE {
        @Override
        public ArrayList<Dungeon> createNoise(int width, int height, int numberOfMaps, double odds) {
            if (odds < 0.1 || odds > 1) throw new VariableBoundsException(0.1, 1.0); //TOOD i am not sure if i need that

            ArrayList<Dungeon> mapList = new ArrayList<>();
            for (int i = 0; i < numberOfMaps; i++) {
                mapList.add(new Dungeon(width, height));

                Random random = new Random();
                for (Dungeon dungeon : mapList) {

                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            if (random.nextDouble() > odds) dungeon.getDungeonMatrix().put(x, y, CORRIDOR);
                            else dungeon.getDungeonMatrix().put(x, y, WALL);
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
