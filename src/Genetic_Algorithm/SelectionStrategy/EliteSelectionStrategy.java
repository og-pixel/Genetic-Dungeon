package Genetic_Algorithm.SelectionStrategy;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Comparator;

public class EliteSelectionStrategy implements SelectionStrategy {
    public static final String IMPLEMENTATION = "elite";

    @Override
    public ArrayList<Chromosome> selectFitIndividuals(ArrayList<Chromosome> chromosome, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);

        //First elements are the most fit
        chromosome.sort(Comparator.comparing(Chromosome::getFitnessScore).reversed());
        chromosome.subList((int)(chromosome.size() * selectionFraction), chromosome.size()).clear();

        chromosome = Algorithms.deepClone(chromosome);
        return chromosome;
    }
}
