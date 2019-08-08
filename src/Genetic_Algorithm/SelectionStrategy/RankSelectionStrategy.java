package Genetic_Algorithm.SelectionStrategy;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import Chromosome.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class RankSelectionStrategy implements SelectionStrategy {

    public static final String IMPLEMENTATION = "rank";

    @Override
    public ArrayList<Chromosome> selectFitIndividuals(ArrayList<Chromosome> chromosomeList, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);

        double limit = chromosomeList.size() * selectionFraction;
        Random random = new Random();

        chromosomeList.sort(Comparator.comparing(Chromosome::getFitnessScore).reversed());
        ArrayList<Chromosome> selectedList = new ArrayList<>();

        double[] cumulativeRank = new double[chromosomeList.size()];
        cumulativeRank[0] = 1.0;

        for (double i = 1.0; i < chromosomeList.size(); i++) {
            double sum = cumulativeRank[(int) i - 1];

            double p  = (i / chromosomeList.size());
            cumulativeRank[(int) i] = sum + p;
        }

        while(selectedList.size() < limit){
            double randomFitness = random.nextDouble() * cumulativeRank[cumulativeRank.length - 1];

            int index = Arrays.binarySearch(cumulativeRank, randomFitness);

            //If number is negative, it means its not on the list, instead it gives us a place where it WOULD be, which
            //in this case is the same (we look for close enough)
            if (index < 0)
                index = Math.abs(index + 1);

            //TODO I cannot prevent duplicates, but I can still make it so
            // they are deep cloned
            Chromosome clonedChromosome;
//            Chromosome clonedChromosome2;
            if(index == 0){
                clonedChromosome = Algorithms.deepClone(chromosomeList.get(index));
//                clonedChromosome2 = Algorithms.deepClone(chromosomeList.get(indexTwo));
            }else {
                clonedChromosome = Algorithms.deepClone(chromosomeList.get(index - 1));
//                clonedChromosome2 = Algorithms.deepClone(chromosomeList.get(indexTwo - 1));
            }
            selectedList.add(clonedChromosome);
//            selectedList.add(clonedChromosome2);
        }
        return selectedList;
    }
}
