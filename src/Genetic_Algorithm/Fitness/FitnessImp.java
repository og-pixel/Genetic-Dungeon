package Genetic_Algorithm.Fitness;

import Map.Map;

/**
 * Interface defines what a fitness function needs to run
 */
public interface FitnessImp {
    /**
     * Evaluate map based on some arbitrary rule (for example give a full score a map that is fully traversable)
     * @param map map to be evaluated
     * @return score that the map achieved
     */
    void evaluateMap(Map map);

    /**
     * Each fitness function needs to have a way to evaluate each individual
     * However if program takes too much times, it should run an approximation that should be
     * much faster version of the same (just lest accurate)
     * @param map map to be evaluated
     * @return score that the map achieved, it might be not accurate, but fast
     */
    void evaluateMapCheap(Map map);
}

