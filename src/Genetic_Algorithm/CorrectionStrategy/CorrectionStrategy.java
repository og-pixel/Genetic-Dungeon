package Genetic_Algorithm.CorrectionStrategy;

import Chromosome.*;

/**
 * Idea of this interface is to encourage genes to evolve towards our goal plan
 * one implementation might try to find empty spots around map and replace them with walls, decreasing room count
 * which would in turn give higher score
 */
public interface CorrectionStrategy {
    void correctMap(Chromosome chromosome);
}
