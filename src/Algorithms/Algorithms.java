package Algorithms;

import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Static class implementing a bunch of algorithms
 * used to evaluate dungeon and give them a score
 */
public class Algorithms {

//    public static ArrayList<ArrayList<Boolean>> visitMap;

    public Algorithms(Dungeon dungeon){


    }


    public static ArrayList<ArrayList<Boolean>> floodFill(Dungeon dungeon, int x, int y){
        if (dungeon.getDungeonMatrix().get(y).get(x) == null) {
            return null;
        }

        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();




//        if(dungeon.getDungeonMatrix().get(y).get(x).getTile() instanceof )

        ArrayList<ArrayList<Boolean>> visitMap = Matrix.createMatrix(dungeonWidth, dungeonHeight, false);
        flood(dungeon, visitMap, x, y);


        return visitMap;
    }

    private static void flood(Dungeon dungeon, ArrayList<ArrayList<Boolean>> visitMap, int x, int y){

        if(dungeon.getDungeonMatrix().get(y).get(x).getTile() instanceof Corridor) {
            visitMap.get(y).set(x, true);
//            System.out.println("DEBUG x: " + x + "  y: " + y);


            if (x > 0 && dungeon.getDungeonMatrix().get(y).get(x - 1).getTile() instanceof Corridor) {
                if (!visitMap.get(y).get(x - 1)) {
                    flood(dungeon, visitMap, x - 1, y);
                }
            }
            if (x + 1 < dungeon.getDungeonWidth() && dungeon.getDungeonMatrix().get(y).get(x + 1).getTile() instanceof Corridor) {
                if (!visitMap.get(y).get(x + 1)) {
                    flood(dungeon, visitMap, x + 1, y);
                }
            }
            if (y > 0 && dungeon.getDungeonMatrix().get(y - 1).get(x).getTile() instanceof Corridor) {
                if (!visitMap.get(y - 1).get(x)) {
                    flood(dungeon, visitMap, x, y - 1);
                }
            }
            if (y + 1 < dungeon.getDungeonHeight() && dungeon.getDungeonMatrix().get(y + 1).get(x).getTile() instanceof Corridor) {
                if (!visitMap.get(y + 1).get(x)) {
                    flood(dungeon, visitMap, x, y + 1);
                }
            }
        }

//        heightCount.


    }

    public static void aStarTraverse(Dungeon dungeon){

    }

}
