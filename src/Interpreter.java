import Algorithms.*;
import Algorithms.CA.*;
import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Tile;
import Dungeon.Tile.Wall;
import Genetic_Algorithm.*;

import java.io.IOException;
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
            fitnessImpList.add(new IsTraversableFitness(1)); //TODO I can either have an abstract between implementation and class or not bother at all, I can do both!

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

            ////////////////////////////
            //Crossover population and return a new population
            ChromosomeEvaluationImp z = new BasicChromosomeEvaluation(0.5, population);

//            ArrayList<Dungeon> newMapList = new ArrayList<Dungeon>();
//            newMapList.add(new Dungeon(10,10));
//            newMapList.add(new Dungeon(10,10));
//            newMapList.get(0).getDungeonMatrix().fillMatrix(new Corridor(0,0));
//            newMapList.get(1).getDungeonMatrix().fillMatrix(new Wall(0,0));


            nextGeneration = z.crossoverPopulation(mapList, fitnessImpList, generations);

            ////////////////////////////
            //Mutate all maps
            MutatorImp defaultMutator = new DefaultMutator(0.1);
            defaultMutator.mutateDungeons(nextGeneration);


                 //TODO this part is for printing and it shoulkd be a separate thing really
            for (int i = 0; i < mapList.size(); i++) {
                try {
                    Algorithms.writeToFile("", mapList.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


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
