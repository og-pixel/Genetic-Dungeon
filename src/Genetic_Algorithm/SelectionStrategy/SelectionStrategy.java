package Genetic_Algorithm.SelectionStrategy;

import Chromosome.Chromosome;

import java.util.ArrayList;

public interface SelectionStrategy {
    ArrayList<Chromosome> selectFitIndividuals(ArrayList<Chromosome> chromosome, double selectionFraction);
}
