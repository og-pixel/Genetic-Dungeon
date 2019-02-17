import Algorithms.Fitness;
import Algorithms.FindAllRooms;
import Algorithms.Matrix;
import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.EmptyTile;
import Dungeon.Tile.Tile;
import Dungeon.Tile.Wall;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args){
//        new Interpreter(args);
        //todo interpreter later
//        Matrix<Integer> intMatrix = new Matrix<Integer>(10,10);
//        Random random = new Random();
//
//        for(int i = 0; i < 10; i++) {
//            if (random.nextFloat() > 0.3) {
//                intMatrix.addColumn(5);
//            }else intMatrix.addRow(5);
//        }
//
//        intMatrix.fillMatrix(0);
//        System.out.println(intMatrix);
//        System.exit(1);

        ArrayList<Fitness> fitnessList = new ArrayList<>();
        fitnessList.add(new FindAllRooms());

        ArrayList<Dungeon> mapList = new ArrayList<>();
        for(int i = 0; i < 1; i++){
            mapList.add(new Dungeon(120, 70));//todo i changed it
            mapList.get(i).getDungeonMatrix().cellurarAutomata(new Corridor(0,0), new Wall(0,0), 0.6);//todo position is wrong
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
