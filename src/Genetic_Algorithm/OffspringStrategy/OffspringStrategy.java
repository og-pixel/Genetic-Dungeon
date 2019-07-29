package Genetic_Algorithm.OffspringStrategy;

import GameMap.GameMap;

import java.util.ArrayList;

public interface OffspringStrategy {
    ArrayList<GameMap> createNewGeneration(ArrayList<GameMap> list, double populationSize, double selectionFraction);
}
