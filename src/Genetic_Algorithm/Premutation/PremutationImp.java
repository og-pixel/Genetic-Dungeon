package Genetic_Algorithm.Premutation;

import Map.GameMap;

import java.util.ArrayList;

public interface PremutationImp {
    void premutateDungeon(GameMap gameMap);
    void premutateDungeons(ArrayList<GameMap> gameMapList);
}
