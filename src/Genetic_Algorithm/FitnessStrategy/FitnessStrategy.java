package Genetic_Algorithm.FitnessStrategy;

import Chromosome.*;

/**
 * Interface defines what a fitness function needs to run
 */
public interface FitnessStrategy {
    /**
     * Evaluate chromosome based on some arbitrary rule (for example give a full score a chromosome that is fully traversable)
     * @param chromosome chromosome to be evaluated
     * @return score that the chromosome achieved
     */
    void evaluateMap(Chromosome chromosome);

    /**
     * Each fitness function needs to have a way to evaluate each individual
     * However if program takes too much time, it should run an approximation that should be
     * much faster version of the same (just lest accurate)
     * @param chromosome chromosome to be evaluated
     * @return score that the chromosome achieved, it might be not accurate, but fast
     */
    void evaluateMapCheap(Chromosome chromosome);
}

