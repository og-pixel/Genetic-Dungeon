import Algorithms.Fitness;
import Algorithms.FindAllRooms;
import Dungeon.Dungeon;

import java.util.ArrayList;
public class Main {

    public static void main(String[] args) throws InterruptedException {
//        new Interpreter(args);
        //todo interpreter later

        ArrayList<Fitness> fitnessList = new ArrayList<>();
        fitnessList.add(new FindAllRooms());



        ArrayList<Dungeon> mapList = new ArrayList<>();
        for(int i = 0; i < 1; i++){
            mapList.add(new Dungeon(5, 10, true));
//            mapList.get(mapList.size() - 1).printDungeon();
        }


        float timeNow = System.nanoTime();

        for(int i = 0; i < fitnessList.size(); i++) {
            for(int x = 0; x < mapList.size(); x++){
                fitnessList.get(i).evaluateDungeon(mapList.get(x));
                mapList.get(x).printDungeon();
            }
        }

        System.out.print("Finished after: " + ((System.nanoTime() - timeNow) / 1000000000) + " seconds");
    }
}
