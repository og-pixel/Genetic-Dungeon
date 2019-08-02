package Genetic_Algorithm.MutatorStrategy;

import Chromosome.Chromosome;
import Exceptions.NegativeNumberException;
import Genetic_Algorithm.MutatorStrategy.MutatorStrategy;

import java.util.ArrayList;
import java.util.Random;

import static Chromosome.TileList.CORRIDOR;
import static Chromosome.TileList.WALL;

public class SwapPermutationStrategy implements MutatorStrategy {
    private double odds;
    private Random random;
    public static final String IMPLEMENTATION = "swap";

    public static int swap = 0;
    public static int noSwap = 0;

    public SwapPermutationStrategy(double odds) {
        if(odds < 0)throw new NegativeNumberException();
        if(odds >= 3){
            System.err.println("Genetic Mutation works best if its value is held between 0.01% and 2%.\n" +
                    "Anything other than that and its no longer searching.");
        }

        this.odds = odds;
        random = new Random();
    }

    @Override
    public void mutateDungeon(Chromosome chromosome) {
        int dungeonHeight = chromosome.getMapHeight();
        int dungeonWidth = chromosome.getMapWidth();
        int x1 ,y1;
        int x2, y2;
        for (int y = 0; y < dungeonHeight; y++) {
            for (int x = 0; x < dungeonWidth; x++) {
                if(random.nextDouble() <= odds) {
                    //While it is a loop, it is meant to be run once majority ot the time
                    do {
                        x1 = random.nextInt(dungeonWidth);
                        y1 = random.nextInt(dungeonHeight);

                        x2 = random.nextInt(dungeonWidth);
                        y2 = random.nextInt(dungeonHeight);
                    }while (x1 != x2 || y1 != y2);


                    //TODO delete those swap later
                    if(chromosome.getMapMatrix().swapElements(x1, y1, x2, y2)){
                        swap++;
                        chromosome.setMutationCount(chromosome.getMutationCount() + 1);
                    }else noSwap++;
                }
            }
        }

        //TODO swap works nicely only if there is some kind of bit flip mutation
        for (int y = 0; y < chromosome.getMapHeight(); y++) {
            for (int x = 0; x < chromosome.getMapWidth(); x++) {
                if(random.nextDouble() <= 0.0001){
                    if (chromosome.getMapMatrix().getElement(x,y) == CORRIDOR) {
                        chromosome.getMapMatrix().put(x, y, WALL);
                    }
                    else chromosome.getMapMatrix().put(x, y, CORRIDOR);

                    chromosome.setMutationCount(chromosome.getMutationCount() + 1);
                }
            }
        }
    }

    @Override
    public void mutateDungeons(ArrayList<Chromosome> chromosomeList) {
        for (Chromosome chromosome : chromosomeList) {
            mutateDungeon(chromosome);
        }
    }
}
