import Algorithms.FindAllRooms;
import Algorithms.Fitness;
import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Wall;

import java.util.ArrayList;

public class Interpreter {

    private final String CREATE = "-c";

    public Interpreter(String... args){
        interpretArguments(args);
    }

    private ArrayList<Fitness> fitnessList = null;

    private void interpretArguments(String... args){
        if(args[0].equals(CREATE)){
            int numberOfMaps = Integer.parseInt(args[1]);
            int width = Integer.parseInt(args[2]);
            int height = Integer.parseInt(args[3]);

            createFitness();
            ArrayList<Dungeon> mapList = new ArrayList<>();
            for(int i = 0; i < numberOfMaps; i++){
                mapList.add(new Dungeon(width, height));
                mapList.get(i).getDungeonMatrix().cellularAutomate(new Corridor(0,0), new Wall(0,0), 0.6);//todo position is wrong
            }

            float timeNow = System.nanoTime();
            for(int i = 0; i < fitnessList.size(); i++) {
                for(int x = 0; x < mapList.size(); x++){
                    fitnessList.get(i).evaluateDungeon(mapList.get(x));
                }
            }
            System.out.print("Finished after: " + ((System.nanoTime() - timeNow) / 1000000000) + " seconds");
        }
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
