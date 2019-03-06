import Dungeon.Dungeon;
import Genetic_Algorithm.*;

import java.util.ArrayList;

public class Interpreter {

    private final String CREATE = "-c";
    private final String HELP = "-h";

    private final String README = "Examples:\n-c 10 15 10 Create 10 maps of size 15 by 10\n" +
            "-c 1 100 100 Create 1 map of size 100 by 100";

    public Interpreter(String... args){
        interpretArguments(args);
    }

    private void interpretArguments(String... args){
        if(args.length <= 0){
            displayHelp();
            System.exit(1);
        }

        if(args[0].equals(CREATE)){
            int population = Integer.parseInt(args[1]);
            int generations = Integer.parseInt(args[2]);
            int dungeonWidth = Integer.parseInt(args[3]);
            int dungeonHeight = Integer.parseInt(args[4]);

            float timeNow = System.nanoTime();

            //List of maps
            ArrayList<Dungeon> mapList = new ArrayList<>(); //List of maps
            ArrayList<Dungeon> nextGeneration = new ArrayList<>(); //List of maps

            //Create Fitness
            ArrayList<FitnessImp> fitnessImpList = new ArrayList<>();
            fitnessImpList.add(new FindAllRoomsFitness());

            ////////////////////////////
            //Fill maps with cellurar automata data (random 1's and 0's with loaded odds)
            PopulationImp c = new CellularPopulation();
            mapList = c.createPopulation(dungeonWidth, dungeonHeight, population, 0.4); // 60/40 maps

            ////////////////////////////
            //Crossover population and return a new population
            CrossoverImp z = new CrossoverBehaviour(0.1, population);
            nextGeneration = z.crossoverPopulation(mapList, fitnessImpList, generations);

            ////////////////////////////
            //Mutate all maps
            MutatorImp defaultMutator = new DefaultMutator(0.1);
            defaultMutator.mutateDungeons(nextGeneration);


            System.out.print("Finished after: " + ((System.nanoTime() - timeNow) / 1000000000) + " seconds");
        }
    }


//    //Creates all fitness that I have, currently only one
//    private void createFitness(){
//        fitnessImpList = new ArrayList<>();
//        fitnessImpList.add(new FindAllRoomsFitness());
//    }

    private void displayHelp(){
        System.out.println(README);
    }
}
