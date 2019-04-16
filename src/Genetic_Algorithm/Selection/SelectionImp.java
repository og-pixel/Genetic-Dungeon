package Genetic_Algorithm.Selection;

import Map.Map;

import java.util.ArrayList;

public interface SelectionImp {
    ArrayList<Map> selectFitIndividuals(ArrayList<Map> map, double selectionFraction);
}
