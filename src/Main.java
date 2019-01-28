import Individual.*;

import java.util.ArrayList;

public class Main {

    public static ArrayList<Individual> population;
    public static Runnable timer;

    public static void main(String[] args) throws InterruptedException {

//        Dungeon_GA dungeon = new Dungeon_GA(200, 100);
//        dungeon.test();

        population = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            population.add(new Individual(100));
        }

        Fitness.runPopulation(population);

    }
}
