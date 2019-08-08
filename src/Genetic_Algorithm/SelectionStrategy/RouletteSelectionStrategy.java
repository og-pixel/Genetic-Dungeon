package Genetic_Algorithm.SelectionStrategy;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import Chromosome.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class RouletteSelectionStrategy implements SelectionStrategy {

    public static final String IMPLEMENTATION = "roulette";

    @Override
    public ArrayList<Chromosome> selectFitIndividuals(ArrayList<Chromosome> chromosomeList, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);

        double limit = chromosomeList.size() * selectionFraction;
        Random random = new Random();

        int rouletteSum = 0;
        for (Chromosome chromosome : chromosomeList) rouletteSum += chromosome.getFitnessScore();

        chromosomeList.sort(Comparator.comparing(Chromosome::getFitnessScore).reversed());
        ArrayList<Chromosome> selectedList = new ArrayList<>();

        double[] cumulativeFitnesses = new double[chromosomeList.size()];
        cumulativeFitnesses[0] = chromosomeList.get(0).getFitnessScore();

        for (int i = 1; i < chromosomeList.size(); i++) {
            double fitness = chromosomeList.get(i).getFitnessScore();
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }

        while(selectedList.size() < limit){
            double randomFitness = random.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];

            int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);

            //In Binary Search, if a number is negative, it means it is not on the list, instead it gives us a place where it WOULD be, which
            //in this case is the same (we look for close enough)
            if (index < 0)
                index = Math.abs(index + 1);


            //TODO I cannot prevent duplicates, but I can still make it so
            // they are deep cloned


            Chromosome clonedChromosome;
            if(index == 0){
                clonedChromosome = Algorithms.deepClone(chromosomeList.get(index));
            }else {
                clonedChromosome = Algorithms.deepClone(chromosomeList.get(index - 1));
            }

            selectedList.add(clonedChromosome);
        }
        return selectedList;
    }
}
