import Algorithms.FindAllRooms;
import Algorithms.Fitness;
import Algorithms.Mutator;
import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Wall;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Interpreter {

    private final String CREATE = "-c";
    private final String HELP = "-h";

    private final String README = "Examples:\n-c 10 15 10 Create 10 maps of size 15 by 10\n" +
            "-c 1 100 100 Create 1 map of size 100 by 100";

    public Interpreter(String... args){
        interpretArguments(args);
    }

    private ArrayList<Fitness> fitnessList = null;

    private void interpretArguments(String... args){
        if(args.length <= 0){
            displayHelp();
            System.exit(1);
        }

        if(args[0].equals(CREATE)){
            int numberOfMaps = Integer.parseInt(args[1]);
            int width = Integer.parseInt(args[2]);
            int height = Integer.parseInt(args[3]);

            createFitness();
            ArrayList<Dungeon> mapList = new ArrayList<>(); //List of maps



            float timeNow = System.nanoTime();

            ////////////////////////////
            PopulationImp c = new Population();
            mapList = c.createPopulation(width, height, numberOfMaps, 0.4);
            ////////////////////////////
            CrossoverImp z = new CrossoverBehaviour();
            z.crossoverPopulation(mapList, fitnessList);
            ////////////////////////////

            System.out.print("Finished after: " + ((System.nanoTime() - timeNow) / 1000000000) + " seconds");
        }
    }

    private void displayHelp(){
        System.out.println(README);
    }



    private ArrayList<Dungeon> generatePopulation(int width, int height, int amount){
        ArrayList<Dungeon> list = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            list.add(new Dungeon(width, height));
        }
        return list;
    }

    private void createFitness(){
        fitnessList = new ArrayList<>();
        fitnessList.add(new FindAllRooms());
    }

}
