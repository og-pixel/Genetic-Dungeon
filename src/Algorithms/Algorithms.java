package Algorithms;

import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;

import java.util.ArrayList;

/**
 * Static class implementing a bunch of algorithms
 * used to evaluate dungeon and give them a score
 */
public class Algorithms {

    public static ArrayList<ArrayList<Boolean>> visitMap;

    public static void floodFill(Dungeon dungeon){
        ArrayList<Integer> list = new ArrayList<>();
        visitMap = new ArrayList<>();
        //TODO change the limit to the whole dungeon size
        // I might have mixed up width with height
        for(int i = 0; i < dungeon.getDungeonWidth(); i++){
            visitMap.add(new ArrayList<>());
            for(int z = 0; z < dungeon.getDungeonHeight(); z++){
                visitMap.get(i).add(false);
            }
        }

        for(int i = 0; i < dungeon.getDungeonWidth(); i++){
            for(int z = 0; z < dungeon.getDungeonHeight(); z++){
                flood(dungeon, i, z);
            }
        }
    }

    private static void flood(Dungeon dungeon, int x, int y){
        visitMap.get(x).set(y, true);
        try{
            if(dungeon.getDungeonMatrix().get(x).get(y - 1).getTile() instanceof Corridor){
               if(!visitMap.get(x).get(y - 1)){
                   flood(dungeon, x, y - 1);
               }
            }
            if(dungeon.getDungeonMatrix().get(x).get(y + 1).getTile() instanceof Corridor){
               if(!visitMap.get(x).get(y + 1)){
                   flood(dungeon, x, y + 1);
               }
            }
            if(dungeon.getDungeonMatrix().get(x - 1).get(y).getTile() instanceof Corridor){
               if(!visitMap.get(x - 1).get(y)){
                   flood(dungeon, x - 1, y);
               }
            }
            if(dungeon.getDungeonMatrix().get(x + 1).get(y).getTile() instanceof Corridor){
               if(!visitMap.get(x + 1).get(y)){
                   flood(dungeon, x + 1, y);
               }
            }
        }catch (Exception e){

        }
    }

    public static void aStarTraverse(Dungeon dungeon){

    }
}
