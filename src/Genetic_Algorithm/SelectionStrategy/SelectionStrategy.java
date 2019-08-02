package Genetic_Algorithm.SelectionStrategy;

import Chromosome.*;

import java.util.ArrayList;
import java.util.List;

public interface SelectionStrategy {
    ArrayList<Chromosome> selectFitIndividuals(ArrayList<Chromosome> chromosomeList, double selectionFraction);
}
