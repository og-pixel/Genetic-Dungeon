package Genetic_Algorithm.MutatorStrategy;

import DataStructure.Matrix;
import Exceptions.NegativeNumberException;
import Chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Random;

import static Chromosome.TileList.CORRIDOR;
import static Chromosome.TileList.WALL;

public class DefaultMutatorStrategy implements MutatorStrategy {
    private final double odds;
    public static final String IMPLEMENTATION = "default_mutator";

    public DefaultMutatorStrategy(double odds) {
        if(odds < 0)throw new NegativeNumberException();
        if(odds >= 3){
            System.err.println("Genetic Mutation works best if its value is held between 0.01% and 2%.\n" +
                    "Anything other than that and its no longer searching.");
        }

        this.odds = odds;
    }

    @Override
    public void mutateDungeon(Chromosome chromosome) {
        Matrix dungeonMatrix = chromosome.getMapMatrix();
        Random random = new Random();
        for (int y = 0; y < chromosome.getMapHeight(); y++) {
            for (int x = 0; x < chromosome.getMapWidth(); x++) {
                if(random.nextDouble() <= odds){
                    if (dungeonMatrix.getElement(x,y) == CORRIDOR) {
                        dungeonMatrix.put(x, y, WALL);
                    }
                    else dungeonMatrix.put(x, y, CORRIDOR);

                    chromosome.setMutationCount(chromosome.getMutationCount() + 1);
                }
            }
        }
    }

    @Override
    public void mutateDungeons(ArrayList<Chromosome> chromosomeList) {
        for (Chromosome chromosome : chromosomeList) mutateDungeon(chromosome);
    }
}
