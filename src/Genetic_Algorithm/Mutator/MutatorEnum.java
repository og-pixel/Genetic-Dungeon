package Genetic_Algorithm.Mutator;

import DataStructure.Matrix;
import Map.GameMap;

import java.util.ArrayList;
import java.util.Random;
import static Map.TileList.*;

public enum MutatorEnum implements Mutator {
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
    public void mutateDungeon(GameMap gameMap) {
        Matrix dungeonMatrix = gameMap.getMapMatrix();
        Random random = new Random();
        for (int y = 0; y < gameMap.getMapHeight(); y++) {
            for (int x = 0; x < gameMap.getMapWidth(); x++) {
                if(random.nextDouble() <= odds){
                    if (dungeonMatrix.getElement(x,y) == CORRIDOR) dungeonMatrix.put(x, y, WALL);
                    else dungeonMatrix.put(x, y, CORRIDOR);

                    gameMap.setMutationCount(gameMap.getMutationCount() + 1);
                }
            }
        }
    }

    @Override
    public void mutateDungeons(ArrayList<GameMap> gameMapList) {
        for (GameMap gameMap : gameMapList) mutateDungeon(gameMap);
    }
}
