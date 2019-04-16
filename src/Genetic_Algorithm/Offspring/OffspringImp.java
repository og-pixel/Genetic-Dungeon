package Genetic_Algorithm.Offspring;

import Map.Map;

import java.util.ArrayList;

public interface OffspringImp {
    ArrayList<Map> createNewGeneration(ArrayList<Map> list, double populationSize, double selectionFraction);
}
