package Genetic_Algorithm.PermutationStrategy;

import GameMap.GameMap;

import java.util.ArrayList;

public interface PermutationStrategy {
    void permutateDungeon(GameMap gameMap);
    void permutateDungeons(ArrayList<GameMap> gameMapList);
}
