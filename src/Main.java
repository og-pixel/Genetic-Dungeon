import Algorithms.Fitness;
import Algorithms.FindAllRooms;
import Algorithms.Matrix;
import Dungeon.Dungeon;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        new Interpreter(args);
        //todo interpreter later


        Matrix intMatrix = new Matrix(10,10, 5);

        Random random = new Random();

        for(int i = 0; i < 1000; i++) {
            if (random.nextFloat() > 0.3) {
                intMatrix.addColumn(5);
            }else intMatrix.addRow(5);
        }
        System.out.println(intMatrix);






        System.exit(1);






        ArrayList<Fitness> fitnessList = new ArrayList<>();
        fitnessList.add(new FindAllRooms());



        ArrayList<Dungeon> mapList = new ArrayList<>();
        for(int i = 0; i < 1; i++){
            mapList.add(new Dungeon(15, 10, true));
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
