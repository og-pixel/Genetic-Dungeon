package Genetic_Algorithm.MutatorStrategy;

import Chromosome.*;

import java.util.ArrayList;

/**
 * Iterate over maps and mutate them
 */
public interface MutatorStrategy {
    void mutateDungeon(Chromosome chromosome);
    void mutateDungeons(ArrayList<Chromosome> chromosomeList);
}
