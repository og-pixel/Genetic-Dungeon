package Algorithms;

import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import com.sun.javafx.scene.traversal.Algorithm;

import java.util.ArrayList;

/**
 * Static class implementing a bunch of algorithms
 * used to evaluate dungeon and give them a score
 */
public class Algorithms {

//    public static ArrayList<ArrayList<Boolean>> visitMap;

    public Algorithms(Dungeon dungeon){


    }


    public static ArrayList<ArrayList<Boolean>> floodFill(Dungeon dungeon, int x, int y){
        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();

        ArrayList<ArrayList<Boolean>> visitMap = Algorithms.createMatrix(dungeonWidth, dungeonHeight, false);
        flood(dungeon, visitMap, x, y);

        return visitMap;
    }

    private static void flood(Dungeon dungeon, ArrayList<ArrayList<Boolean>> visitMap, int x, int y){
        if(dungeon.getDungeonMatrix().get(x).get(y).getTile() instanceof Corridor){
            visitMap.get(x).set(y, true);
        }
        try{
            if(dungeon.getDungeonMatrix().get(x).get(y - 1).getTile() instanceof Corridor){
               if(!visitMap.get(x).get(y - 1)){
                   flood(dungeon, visitMap, x, y - 1);
               }
            }
            if(dungeon.getDungeonMatrix().get(x).get(y + 1).getTile() instanceof Corridor){
               if(!visitMap.get(x).get(y + 1)){
                   flood(dungeon, visitMap, x, y + 1);
               }
            }
            if(dungeon.getDungeonMatrix().get(x - 1).get(y).getTile() instanceof Corridor){
               if(!visitMap.get(x - 1).get(y)){
                   flood(dungeon, visitMap, x - 1, y);
               }
            }
            if(dungeon.getDungeonMatrix().get(x + 1).get(y).getTile() instanceof Corridor){
               if(!visitMap.get(x + 1).get(y)){
                   flood(dungeon, visitMap, x + 1, y);
               }
            }
        }catch (Exception e){

        }
    }

    public static void aStarTraverse(Dungeon dungeon){

    }

    /**
     * todo it will fill it with one object
     * @param width
     * @param height
     * @param type
     * @param <T>
     * @return
     */
    public static <T> ArrayList<ArrayList<T>> createMatrix(int width, int height, T type){
        ArrayList<ArrayList<T>> genericMatrix = null;
        for(int yAxis = 0; yAxis < height; yAxis++){
            genericMatrix = new ArrayList<>();
            for(int xAxis = 0; xAxis < width; xAxis++){
                genericMatrix.get(yAxis).add(type);
            }
        }
        if(genericMatrix == null)throw new RuntimeException("Matrix cannot be null");
        return genericMatrix;
    }
}
