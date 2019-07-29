package Genetic_Algorithm.SelectionStrategy;

import GameMap.GameMap;

import java.util.ArrayList;

public interface SelectionStrategy {
    ArrayList<GameMap> selectFitIndividuals(ArrayList<GameMap> gameMap, double selectionFraction);
}
