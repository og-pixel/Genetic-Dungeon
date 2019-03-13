package Genetic_Algorithm.Fitness;

import Dungeon.Dungeon;

/**
 * Interface defines what a fitness function needs to run it?
 */
public interface FitnessImp {
    /**
     * Each fitness function needs to have a way to evaluate each individual
     * @param dungeon dungeon map
     * @return score that the map achieved
     */
    double evaluateDungeon(Dungeon dungeon);
}

