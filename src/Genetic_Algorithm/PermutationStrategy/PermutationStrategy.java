package Genetic_Algorithm.PermutationStrategy;

import Map.GameMap;

import java.util.ArrayList;

public interface PermutationStrategy {
    void permutateDungeon(GameMap gameMap);
    void permutateDungeons(ArrayList<GameMap> gameMapList);
}
