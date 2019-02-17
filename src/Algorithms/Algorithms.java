package Algorithms;

import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;


/**
 * Static class implementing a bunch of algorithms
 * used to evaluate dungeon and give them a score
 */
public class Algorithms {

//    public static ArrayList<ArrayList<Boolean>> visitMap;
//
//    public Algorithms(Dungeon dungeon){
//
//
//    }

    //todo for now it returns a a matrix of booleans
    public static Matrix<Boolean> floodFill(Dungeon dungeon, int x, int y){
        if (dungeon.getDungeonMatrix().getElement(x, y) == null) {
            return null;
        }

        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();




//        if(dungeon.getDungeonMatrix().get(y).get(x).getTile() instanceof )

//        Matrix<Boolean> visitMap = Matrix.createMatrix(dungeonWidth, dungeonHeight, false);
        Matrix<Boolean> visitMap = new Matrix<Boolean>(dungeonWidth, dungeonHeight);
        flood(dungeon, visitMap, x, y);


        return visitMap;
    }

    private static void flood(Dungeon dungeon, Matrix<Boolean> visitMap, int x, int y){

        if(dungeon.getDungeonMatrix().getElement(x, y) instanceof Corridor) {
            visitMap.putElementAt(true, x, y);

            if (x > 0 && dungeon.getDungeonMatrix().getElement(x - 1, y) instanceof Corridor) {
                if (!visitMap.getElement(x - 1, y)) {
                    flood(dungeon, visitMap, x - 1, y);
                }
            }
            if (x + 1 < dungeon.getDungeonWidth() && dungeon.getDungeonMatrix().getElement(x + 1, y) instanceof Corridor) {
                if (!visitMap.getElement(x + 1, y)) {
                    flood(dungeon, visitMap, x + 1, y);
                }
            }
            if (y > 0 && dungeon.getDungeonMatrix().getElement(x, y - 1) instanceof Corridor) {
                if (!visitMap.getElement(x, y - 1)) {
                    flood(dungeon, visitMap, x, y - 1);
                }
            }
            if (y + 1 < dungeon.getDungeonHeight() && dungeon.getDungeonMatrix().getElement(x, y + 1) instanceof Corridor) {
                if (!visitMap.getElement(x, y + 1)) {
                    flood(dungeon, visitMap, x, y + 1);
                }
            }
        }
    }

    public static void aStarTraverse(Dungeon dungeon){

    }

}
