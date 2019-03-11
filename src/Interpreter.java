import Algorithms.*;
import Algorithms.CA.*;
import Dungeon.Dungeon;
import Dungeon.Tile.Tile;
import Genetic_Algorithm.ChromosomeEvaluation.AbstractChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.BasicChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.PrintBasicChromosomeEvaluation;
import Genetic_Algorithm.Fitness.FitnessEnum;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.Mutation.MutationsEnum;
import Genetic_Algorithm.Population.NoisePopulation;
import Genetic_Algorithm.Population.PopulationImp;

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
            fitnessImpList.add(FitnessEnum.FIND_ALL_ROOMS);
            fitnessImpList.add(FitnessEnum.IS_TRAVERSABLE); //TODO I can either have an abstract between implementation and class or not bother at all, I can do both!

            ////////////////////////////
            //Fill maps with cellurar automata data (random 1's and 0's with loaded odds)
            PopulationImp c = new NoisePopulation();
            mapList = c.createPopulation(dungeonWidth, dungeonHeight, population, 0.4); // 60/40 maps


            ////////////////////////////
            //TODO
            CellurarAutomataImp ca = new Rule20CellurarAutomata();
            for (int i = 0; i < mapList.size(); i++) {
                Matrix<Tile> k = ca.generateMap(mapList.get(i).getDungeonMatrix());
                Dungeon kk = new Dungeon(k);
                mapList.set(i, kk);
            }


            ////////////////////////////
            //Crossover population and return a new population
            AbstractChromosomeEvaluation z = new BasicChromosomeEvaluation(0.5, population);
            z = new PrintBasicChromosomeEvaluation(z); //ADDING this wraps my object and adds filewriting
            nextGeneration = z.crossoverPopulation(mapList, fitnessImpList, generations, MutationsEnum.DEFAULT);//TODO i moved mutation but it actually has to use thi variable now



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
