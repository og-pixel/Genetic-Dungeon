package Genetic_Algorithm.PermutationStrategy;

import Chromosome.Chromosome;

import java.util.ArrayList;

public interface PermutationStrategy {
    void permutateDungeon(Chromosome chromosome);
    void permutateDungeons(ArrayList<Chromosome> chromosomeList);
}
