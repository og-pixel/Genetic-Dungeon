package Algorithms;

import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Tile;
import com.sun.javafx.scene.traversal.Algorithm;

import java.util.ArrayList;

public class Idea implements Fitness{

    @Override
    public void evaluateDungeon(Dungeon dungeon) {
        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();

        int roomSize = 0;
        int roomCount = 0;
        int wallCount = 0;

        ArrayList<ArrayList<Boolean>> visitMap;

        for(int y = 0; y < dungeon.getDungeonHeight(); y++){
            for(int x = 0; x < dungeon.getDungeonWidth(); x++){

                if(dungeon.getDungeonMatrix().get(y).get(x).getTile() instanceof Corridor) {
                    visitMap = Algorithms.floodFill(dungeon, x, y);
                    roomSize = roomSize + countVisited(visitMap);
                    wallCount = (dungeonWidth * dungeonHeight) - roomSize;

                    //Currently score is given by calculating ratio of wall to corridor (so 50
                    dungeon.setScore(roomCount * 10 + roomSize - wallCount); //todo this way i set score so far
                    System.out.println(roomCount + "\n" + roomSize + "\n" + wallCount);
                    System.out.println("dungeon score: " + dungeon.getScore() + "\n");
                    printMap(visitMap);
                    return;
                }
            }
        }

//        return roomCount * 10 + roomSize - wallCount;
    }

    private int countVisited(ArrayList<ArrayList<Boolean>> visitMap){
        int counter = 0;
        for(int y = 0; y < visitMap.size(); y++){
            for(int x = 0; x < visitMap.get(y).size(); x++){
                if(visitMap.get(y).get(x))counter++;
            }
        }
        return counter;
    }
    public void printMap(ArrayList<ArrayList<Boolean>> visitMap){
        for(int y = 0; y < visitMap.size(); y++){
            System.out.println();
            for(int x = 0; x < visitMap.get(y).size(); x++){
                if(visitMap.get(y).get(x)) System.out.print(" ");
                    else System.out.print("░");
            }
        }
        System.out.println();
        System.out.println("****************************");
    }
}
