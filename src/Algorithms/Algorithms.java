package Algorithms;

import Dungeon_GA.Dungeon;

import java.util.ArrayList;

public class Algorithms {

    private static int numberOfRooms;

    public static void floodFill(Dungeon dungeon){
        ArrayList<Integer> list = new ArrayList<>();
        numberOfRooms = 0;

        //TODO change the limit to the whole dungeon size
        // I might have mixed up width with height
        for(int i = 0; i < dungeon.getDungeonHeight(); i++){
            for(int z = 0; z < dungeon.getDungeonWidth(); z++){
                flood();
            }
        }
    }

    public static void flood(){

    }

    public static void aStarTraverse(Dungeon dungeon){

    }
}
