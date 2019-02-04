import Algorithms.Fitness;
import Algorithms.Idea;
import Dungeon.Dungeon;

import java.util.ArrayList;
public class Main {

    public static void main(String[] args) throws InterruptedException {
//        new Interpreter(args);
        //todo interpreter later
        ArrayList<Fitness> fitnessList = new ArrayList<>();
        fitnessList.add(new Idea());



        ArrayList<Dungeon> mapList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            mapList.add(new Dungeon(30, 15, true));
            mapList.get(mapList.size() - 1).printDungeon();
        }



        for(int i = 0; i < fitnessList.size(); i++) {
            for(int x = 0; x < mapList.size(); x++){
                fitnessList.get(i).evaluateDungeon(mapList.get(x));
            }
        }

    }
}
