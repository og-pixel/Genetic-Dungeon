package Genetic_Algorithm.SelectionStrategy;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Comparator;

public class EliteSelectionStrategy implements SelectionStrategy {
    public static final String IMPLEMENTATION = "elite";

    @Override
    public ArrayList<Chromosome> selectFitIndividuals(ArrayList<Chromosome> chromosomeList, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);

        //First elements are the most fit
        chromosomeList.sort(Comparator.comparing(Chromosome::getFitnessScore).reversed());
        chromosomeList.subList((int)(chromosomeList.size() * selectionFraction), chromosomeList.size()).clear();

        chromosomeList = Algorithms.deepClone(chromosomeList);
        return chromosomeList;
    }
}
