package Genetic_Algorithm.Mutator;

import DataStructure.Matrix;
import Map.Map;
import Map.*;

import java.util.ArrayList;
import java.util.Random;

public enum MutatorEnum implements MutatorImp, TileList{
    DEFAULT("default", 0.01), //1%
    HIGH("high", 0.02),       //2%
    HIGHEST("highest", 0.03), //3%
    LOW("low", 0.007),         //0.7%
    LOWER("lower", 0.005),     //0.5%
    LOWEST("lowest", 0.003);   //0.3%


    private final double odds;
    private String implementationName;

    MutatorEnum(String implementationName, double odds) {
        this.implementationName = implementationName;
        this.odds = odds;
    }
    public String getImplementationName() {
        return implementationName;
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
        for (Map map : mapList) mutateDungeon(map);
    }
}
