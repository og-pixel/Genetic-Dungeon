package Genetic_Algorithm.Fitness;

import Dungeon.Dungeon;

/**
 * Interface defines what a fitness function needs to run it?
 */
public interface FitnessImp {
    /**
     * @param dungeon dungeon map
     * @return score that the map achieved
     */
    double evaluateDungeon(Dungeon dungeon);


    /**
     * Each fitness function needs to have a way to evaluate each individual
     * However if program takes too much times, it should run an approximtion that should be
     * much faster version of the same (just lest accurate)
     * TODO I am not 100% sure if I will need it tho
     * @param dungeon
     * @return
     */
    double evaluateDungeonCheap(Dungeon dungeon);
}

