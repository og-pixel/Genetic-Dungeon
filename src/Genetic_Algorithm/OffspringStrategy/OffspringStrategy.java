package Genetic_Algorithm.OffspringStrategy;

import Chromosome.Chromosome;

import java.util.ArrayList;

public interface OffspringStrategy {
    ArrayList<Chromosome> createNewGeneration(ArrayList<Chromosome> list, double populationSize, double selectionFraction);
}
