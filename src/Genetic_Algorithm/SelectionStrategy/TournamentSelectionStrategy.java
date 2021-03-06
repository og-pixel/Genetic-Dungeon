package Genetic_Algorithm.SelectionStrategy;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import Chromosome.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class TournamentSelectionStrategy implements SelectionStrategy {

    public static final String IMPLEMENTATION = "tournament";

    @Override
    public ArrayList<Chromosome> selectFitIndividuals(ArrayList<Chromosome> chromosomeList, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);

        double limit = chromosomeList.size() * selectionFraction;
        Random random = new Random();

        chromosomeList.sort(Comparator.comparing(Chromosome::getFitnessScore).reversed());
        ArrayList<Chromosome> selectedList = new ArrayList<>();

        while(selectedList.size() < limit) {
            ArrayList<Chromosome> tournamentList = new ArrayList<>();
            for (int i = 0; i < limit; i++) {
                Chromosome selectedChromosome = chromosomeList.get(random.nextInt(chromosomeList.size()));
                tournamentList.add(selectedChromosome);
            }

            tournamentList.sort((Comparator.comparing(Chromosome::getFitnessScore).reversed()));
            selectedList.add(Algorithms.deepClone(tournamentList.get(0)));
        }
        return selectedList;
    }
}
