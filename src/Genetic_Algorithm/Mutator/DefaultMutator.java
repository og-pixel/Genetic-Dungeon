package Genetic_Algorithm.Mutator;

import DataStructure.Matrix;
import Exceptions.NegativeNumberException;
import Map.GameMap;

import java.util.ArrayList;
import java.util.Random;

import static Map.TileList.CORRIDOR;
import static Map.TileList.WALL;

public class DefaultMutator implements IMutator {
    private final double odds;

    public DefaultMutator(double odds) {
        if(odds <= 0)throw new NegativeNumberException();
        if(odds >= 3){
            System.err.println("DefaultMutator works best if its value is held between 0.01% and 2%, " +
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
