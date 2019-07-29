package Genetic_Algorithm.Mutator;

import Map.GameMap;

import java.util.ArrayList;

/**
 * Iterate over maps and mutate them
 */
public interface IMutator {
    void mutateDungeon(GameMap gameMap);
    void mutateDungeons(ArrayList<GameMap> gameMapList);
}
