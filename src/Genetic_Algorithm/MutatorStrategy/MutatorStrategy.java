package Genetic_Algorithm.MutatorStrategy;

import GameMap.GameMap;

import java.util.ArrayList;

/**
 * Iterate over maps and mutate them
 */
public interface MutatorStrategy {
    void mutateDungeon(GameMap gameMap);
    void mutateDungeons(ArrayList<GameMap> gameMapList);
}
