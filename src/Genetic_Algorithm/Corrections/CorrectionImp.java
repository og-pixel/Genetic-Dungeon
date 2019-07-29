package Genetic_Algorithm.Corrections;

import Map.GameMap;

/**
 * Idea of this interface is to encourage genes to evolve towards our goal plan
 * one implementation might try to find empty spots around map and replace them with walls, decreasing room count
 * which would in turn give higher score
 */
public interface CorrectionImp {
    void correctMap(GameMap gameMap);
}
