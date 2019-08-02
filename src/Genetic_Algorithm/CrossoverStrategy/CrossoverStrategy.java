package Genetic_Algorithm.CrossoverStrategy;

import Chromosome.*;

import java.util.ArrayList;

public interface CrossoverStrategy {
    ArrayList<Chromosome> createNewGeneration(ArrayList<Chromosome> list, double populationSize, double selectionFraction);
}
