package Genetic_Algorithm.FitnessStrategy;

import Map.GameMap;

/**
 * Interface defines what a fitness function needs to run
 */
public interface FitnessStrategy {
    /**
     * Evaluate gameMap based on some arbitrary rule (for example give a full score a gameMap that is fully traversable)
     * @param gameMap gameMap to be evaluated
     * @return score that the gameMap achieved
     */
    void evaluateMap(GameMap gameMap);

    /**
     * Each fitness function needs to have a way to evaluate each individual
     * However if program takes too much time, it should run an approximation that should be
     * much faster version of the same (just lest accurate)
     * @param gameMap gameMap to be evaluated
     * @return score that the gameMap achieved, it might be not accurate, but fast
     */
    void evaluateMapCheap(GameMap gameMap);
}

