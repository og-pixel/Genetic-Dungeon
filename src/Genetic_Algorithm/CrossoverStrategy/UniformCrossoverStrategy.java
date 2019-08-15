package Genetic_Algorithm.CrossoverStrategy;

import Algorithms.Algorithms;
import Chromosome.*;
import Exceptions.VariableBoundsException;

import java.util.ArrayList;
import java.util.Random;

import static Chromosome.TileList.*;

public class UniformCrossoverStrategy implements CrossoverStrategy {

    public static final String IMPLEMENTATION = "uniform";

    //How much the first parent's genes will be likely to be carried over
    // 0.5 indicates 50/50 split, 1.0 indicates first parent's total dominance (no real crossover)
    private double parentBias;

    public UniformCrossoverStrategy(){
        this.parentBias = 0.4;
    }

    public UniformCrossoverStrategy(double parentBias){
        if(parentBias < 0 || parentBias > 1) throw new VariableBoundsException(0.0, 1.0);

        this.parentBias = parentBias;
    }

    @Override
    public ArrayList<Chromosome> createNewGeneration(ArrayList<Chromosome> chromosomeList, double populationSize, double selectionFraction) {

        Random random = new Random();
        ArrayList<Chromosome> newPopulation = new ArrayList<>();

        while (newPopulation.size() < populationSize) {
            //THIS is mutation part
            int randomPick = random.nextInt(chromosomeList.size());

            Chromosome parent1 = chromosomeList.get(randomPick);

            randomPick = random.nextInt(chromosomeList.size());
            Chromosome parent2 = chromosomeList.get(randomPick);

            Chromosome child = Algorithms.deepClone(parent1);

            for (int y = 0; y < child.getMapHeight(); y++) {
                for (int x = 0; x < child.getMapWidth(); x++) {

                    //Favour First Parent
                    if(random.nextDouble() >= parentBias) {
                        child.getMapMatrix().put(x, y, parent1.getMapMatrix().getElement(x, y));
                    }
                    else {
                        child.getMapMatrix().put(x, y, parent2.getMapMatrix().getElement(x, y));
                    }

                }
            }

            newPopulation.add(child);
        }
        return newPopulation;
    }
}
