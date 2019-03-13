package Genetic_Algorithm.ManualCorrections;

import Dungeon.Dungeon;

/**
 * Idea of this interface is to enoucrage genes to eveolve towards our goal plan
 * one implementation might try to find empty spots around map and repalce them with walls, decreasing room count
 * which would in turn give higher score
 */
public interface CorrectionImp {
    void correct(Dungeon dungeon);
}
