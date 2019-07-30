package Genetic_Algorithm.SelectionStrategy;

import Exceptions.VariableBoundsException;
import Chromosome.Chromosome;

import java.util.ArrayList;

public class StochasticTwoSelectionStrategy implements SelectionStrategy {
    public static final String IMPLEMENTATION = "stochastic_two";

    @Override
    public ArrayList<Chromosome> selectFitIndividuals(ArrayList<Chromosome> chromosome, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);

        return null;
    }
}
