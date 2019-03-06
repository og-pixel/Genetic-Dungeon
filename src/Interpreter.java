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
            ArrayList<Dungeon> newPopulation = new ArrayList<>();

            for(int i = 0; i < numberOfMaps; i++){
                mapList.add(new Dungeon(width, height));
                mapList.get(i).getDungeonMatrix().cellularAutomate(new Corridor(0,0), new Wall(0,0), 0.4);//todo position is wrong
                mapList.get(i).createStartPosition();
                mapList.get(i).createEndPosition();
            }

            Mutator mutator = new Mutator(0.1);

            float timeNow = System.nanoTime();
            float score;

            int GENERATIONS = 10;

            for (int generation = 0; generation < GENERATIONS; generation++) {


                for (int i = 0; i < fitnessList.size(); i++) {
                    for (int x = 0; x < mapList.size(); x++) {
                        mapList.get(x).setScore(
                                fitnessList.get(i).evaluateDungeon(mapList.get(x)));
                        mutator.mutateDungeon(mapList.get(x));
                    }
                }

                Collections.sort(mapList, Comparator.comparing(Dungeon::getScore));

                double TOP_POP = 0.1; //10% of pop will mate todo final etc
                double POP_SIZE = 100;

                Random random = new Random();
                while (newPopulation.size() < POP_SIZE) {
                    int randomPick = (int) (mapList.size() * TOP_POP);
                    if (randomPick <= 0) randomPick = 1;

                    newPopulation.add(mapList.get(random.nextInt(randomPick)));//todo simplify as it looks like shit
                    newPopulation.get(newPopulation.size() - 1).createStartPosition();
                    newPopulation.get(newPopulation.size() - 1).createEndPosition(); //todo i really dislike how i write the whole thing but should be enough for now
                }

                mapList = newPopulation; //new pop!


            }








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
