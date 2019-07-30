package Genetic_Algorithm.SelectionStrategy;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class RouletteSelectionStrategy implements SelectionStrategy {

    public static final String IMPLEMENTATION = "roulette";

    @Override
    public ArrayList<Chromosome> selectFitIndividuals(ArrayList<Chromosome> chromosome, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);
        //TODO i dont like limit variable
        double limit = chromosome.size() * selectionFraction;
        Random random = new Random();

        int rouletteSum = 0;
        //TODO there are chromosome and gameMaps due to my refractoring, CHANGE FFS
        for (Chromosome gameMaps : chromosome) rouletteSum += gameMaps.getFitnessScore();


        chromosome.sort(Comparator.comparing(Chromosome::getFitnessScore).reversed());
        ArrayList<Chromosome> selectedList = new ArrayList<>();

        int x = 0;
        int iterator = -1;
        while(selectedList.size() < limit){
            iterator++;
            x += random.nextInt(rouletteSum);
            if(x > rouletteSum){
                Chromosome selectedChromosome = chromosome.get(iterator);
                selectedChromosome = Algorithms.deepClone(selectedChromosome);

                selectedList.add(selectedChromosome);
                iterator = -1;
                x = 0;
            }
        }

        return selectedList;
    }
}
