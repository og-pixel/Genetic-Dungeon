package Genetic_Algorithm.Offspring;

import Map.GameMap;

import java.util.ArrayList;

public interface OffspringImp {
    ArrayList<GameMap> createNewGeneration(ArrayList<GameMap> list, double populationSize, double selectionFraction);
}
