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

//TODO i dont like limit variable
        double limit = chromosomeList.size() * selectionFraction;
        Random random = new Random();

//        int rouletteSum = 0;
        //TODO there are chromosomeList and gameMaps due to my refractoring, CHANGE FFS
//        for (Chromosome chromosome : chromosomeList) rouletteSum += chromosome.getFitnessScore();


        chromosomeList.sort(Comparator.comparing(Chromosome::getFitnessScore).reversed());
        ArrayList<Chromosome> selectedList = new ArrayList<>();





        //TODO this part is from the interet to test how it should actually work
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

            //TODO this number indicates acutally how many points I can have
            //TODO this is very crude but works
//            int halfOfArray = chromosomeList.size()/2;
//            int indexTwo;
//
//            if(index + halfOfArray > chromosomeList.size()){
//                indexTwo = Math.abs(index - halfOfArray);
//            }else {
//                indexTwo = Math.abs(index + halfOfArray);
//            }



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
