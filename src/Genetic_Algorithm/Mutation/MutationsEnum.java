package Genetic_Algorithm.Mutation;

import Algorithms.Matrix;
import Map.Map;
import Map.*;

import java.util.ArrayList;
import java.util.Random;

public enum MutationsEnum implements MutatorImp, TileList{
    DEFAULT(0.01),
    SLIGHT(0.006),
    LOW(0.005),
    LOWER(0.002);

    private final double odds;
    MutationsEnum(double odds) {
        this.odds = odds;
    }

    @Override
    public void mutateDungeon(Map map) {
        Matrix dungeonMatrix = map.getMapMatrix();
        Random random = new Random();
        for (int y = 0; y < map.getMapHeight(); y++) {
            for (int x = 0; x < map.getMapWidth(); x++) {
                if(random.nextDouble() <= odds){
                    if (dungeonMatrix.getElement(x,y) == CORRIDOR) dungeonMatrix.put(x, y, WALL);
                    else dungeonMatrix.put(x, y, CORRIDOR);
                    map.setMutationCount(map.getMutationCount() + 1);
                }
            }
        }
    }

    @Override
    public void mutateDungeons(ArrayList<Map> mapList) {
        for (Map map : mapList) {
            mutateDungeon(map);
        }
    }
}
