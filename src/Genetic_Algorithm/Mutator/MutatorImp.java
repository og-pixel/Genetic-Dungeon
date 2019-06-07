package Genetic_Algorithm.Mutator;

import Map.Map;

import java.util.ArrayList;

/**
 * Iterate over maps and mutate them
 */
public interface MutatorImp {
    void mutateDungeon(Map map);
    void mutateDungeons(ArrayList<Map> mapList);
}