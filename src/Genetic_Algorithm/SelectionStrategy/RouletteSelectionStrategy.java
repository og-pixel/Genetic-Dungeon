package Genetic_Algorithm.SelectionStrategy;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class RouletteSelectionStrategy implements SelectionStrategy {

    public static final String IMPLEMENTATION = "roulette";

    @Override
    public ArrayList<Chromosome> selectFitIndividuals(ArrayList<Chromosome> chromosomeList, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);
        //TODO i dont like limit variable
        double limit = chromosomeList.size() * selectionFraction;
        Random random = new Random();

        int rouletteSum = 0;
        //TODO there are chromosomeList and gameMaps due to my refractoring, CHANGE FFS
        for (Chromosome chromosome : chromosomeList) rouletteSum += chromosome.getFitnessScore();


        chromosomeList.sort(Comparator.comparing(Chromosome::getFitnessScore).reversed());
        ArrayList<Chromosome> selectedList = new ArrayList<>();


        //TODO this part is from the interet to test how it should actually work
        double[] cumulativeFitnesses = new double[chromosomeList.size()];
        cumulativeFitnesses[0] = chromosomeList.get(0).getFitnessScore();

        for (int i = 1; i < chromosomeList.size(); i++)
        {
            double fitness = chromosomeList.get(i).getFitnessScore();

            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }






        //TODO its someone's code, it seems to be working but I dont get the part of
        // binary search and then why i have to invert the value and add 1
//        int x = 0;
        int iterator = -1;
        while(selectedList.size() < limit){
            iterator++;


            double randomFitness = random.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];

            int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);

            //If number is negative, it means its not on the list, instead it gives us a place where it WOULD be, which
            //in this case is the same (we look for close enough)
            if (index < 0)
                index = Math.abs(index + 1);

//            index = index - iterator;

            //TODO I cannot prevent duplicates, but I can still make it so
            // they are deep cloned


            Chromosome clonedChromosome;
            if(index == 0){
                clonedChromosome = Algorithms.deepClone(chromosomeList.get(index));
            }else {
                clonedChromosome = Algorithms.deepClone(chromosomeList.get(index - 1));
            }

            selectedList.add(clonedChromosome);


//            iterator++;
//            x += random.nextInt(rouletteSum);
//            if(x > rouletteSum){
//                Chromosome selectedChromosome = chromosomeList.get(iterator);
//                //TODO added remove to make sure it cannot be chosen again
//                chromosomeList.remove(iterator);
//                //TODO I think I either don't need it or it is doing something wrong
//                // I also think I need it to remove a choice from the list once done.
////                selectedChromosome = Algorithms.deepClone(selectedChromosome);
//
//
//                selectedList.add(selectedChromosome);
//                iterator = -1;
//                x = 0;
//            }
        }

        return selectedList;
    }
}
