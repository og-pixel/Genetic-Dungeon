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

//        if(dungeon.getDungeonMatrix().get(y).get(x).getTile() instanceof )

        ArrayList<ArrayList<Boolean>> visitMap = Algorithms.createMatrix(dungeonWidth, dungeonHeight, false);
        flood(dungeon, visitMap, y, x);

        return visitMap;
    }

    private static void flood(Dungeon dungeon, ArrayList<ArrayList<Boolean>> visitMap, int y, int x){
        if(dungeon.getDungeonMatrix().get(y).get(x).getTile() instanceof Corridor) {
            visitMap.get(y).set(x, true);
            try {
                if (dungeon.getDungeonMatrix().get(y).get(x - 1).getTile() instanceof Corridor) {
                    if (!visitMap.get(y).get(x - 1)) {
                        flood(dungeon, visitMap, y, x - 1);
                    }
                }
                if (dungeon.getDungeonMatrix().get(y).get(x + 1).getTile() instanceof Corridor) {
                    if (!visitMap.get(y).get(x + 1)) {
                        flood(dungeon, visitMap, y, x + 1);
                    }
                }
                if (dungeon.getDungeonMatrix().get(y - 1).get(x).getTile() instanceof Corridor) {
                    if (!visitMap.get(y - 1).get(x)) {
                        flood(dungeon, visitMap, y - 1, x);
                    }
                }
                if (dungeon.getDungeonMatrix().get(y + 1).get(x).getTile() instanceof Corridor) {
                    if (!visitMap.get(y + 1).get(x)) {
                        flood(dungeon, visitMap, y + 1, x);
                    }
                }
            } catch (Exception e) {

            }
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
        ArrayList<ArrayList<T>> genericMatrix = new ArrayList<>();
        for(int yAxis = 0; yAxis < height; yAxis++){
            genericMatrix.add(new ArrayList<>());
            for(int xAxis = 0; xAxis < width; xAxis++){
                genericMatrix.get(yAxis).add(type);
            }
        }

        if(genericMatrix == null)throw new RuntimeException("Matrix cannot be null");
        return genericMatrix;
    }
}
