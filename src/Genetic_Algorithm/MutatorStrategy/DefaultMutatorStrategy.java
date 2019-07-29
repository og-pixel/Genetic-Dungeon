package Genetic_Algorithm.MutatorStrategy;

import DataStructure.Matrix;
import Exceptions.NegativeNumberException;
import GameMap.GameMap;

import java.util.ArrayList;
import java.util.Random;

import static GameMap.TileList.CORRIDOR;
import static GameMap.TileList.WALL;

public class DefaultMutatorStrategy implements MutatorStrategy {
    private final double odds;
    public static final String IMPLEMENTATION = "default_mutator";

    public DefaultMutatorStrategy(double odds) {
        if(odds <= 0)throw new NegativeNumberException();
        if(odds >= 3){
            System.err.println("DefaultMutatorStrategy works best if its value is held between 0.01% and 2%, " +
                    "anything other than that and its no longer searching.");
        }

        this.odds = odds;
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
