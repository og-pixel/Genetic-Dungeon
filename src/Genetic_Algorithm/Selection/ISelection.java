package Genetic_Algorithm.Selection;

import Map.GameMap;

import java.util.ArrayList;

public interface ISelection {
    ArrayList<GameMap> selectFitIndividuals(ArrayList<GameMap> gameMap, double selectionFraction);
}
