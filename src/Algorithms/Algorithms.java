package Algorithms;

import Dungeon.*;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Tile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;


/**
 * Static class implementing a bunch of algorithms
 * used to evaluate dungeon and give them a score
 */
public class Algorithms {

    //todo for now it returns a a matrix of booleans
    public static Matrix<Boolean> floodFill(Dungeon dungeon, int x, int y){
        if (dungeon.getDungeonMatrix().getElement(x, y) == null) {
            return null;
        }

        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();

        Matrix<Boolean> visitMap = new Matrix<Boolean>(dungeonWidth, dungeonHeight);
        visitMap.fillMatrix(false);

        flood(dungeon, visitMap, x, y);

        return visitMap;
    }

    private static void flood(Dungeon dungeon, Matrix<Boolean> visitMap, int x, int y){

        if(dungeon.getDungeonMatrix().getElement(x, y) instanceof Corridor) {
            visitMap.put(x, y, true);

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

    //TODO create A*
    public static void aStarTraverse(Dungeon dungeon){
        Matrix dungeonMatrix = dungeon.getDungeonMatrix();

        int startPositionX = dungeon.getStartPosition().getxPos();
        int startPositionY = dungeon.getStartPosition().getyPos();

        int endPositionX = dungeon.getEndPosition().getxPos();
        int endPositionY = dungeon.getEndPosition().getyPos();

        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();

        int manhattanDistance = getManhattanDistance(startPositionX,startPositionY,endPositionX,endPositionY);

        Matrix<Boolean> visitMatrix = new Matrix<Boolean>(dungeonWidth, dungeonHeight);
        visitMatrix.fillMatrix(false);

        visitMatrix.put(startPositionX, startPositionY, true);


        while(manhattanDistance != 0) {
            Tile upTile = null;
            Tile rightTile = null;
            Tile downTile = null;
            Tile leftTile = null;



            if (dungeonMatrix.getUp(startPositionX, startPositionY) instanceof Corridor) {

            }

            if (dungeonMatrix.getRight(startPositionX, startPositionY) instanceof Corridor) {

            }

            if (dungeonMatrix.getDown(startPositionX, startPositionY) instanceof Corridor) {

            }

            if (dungeonMatrix.getLeft(startPositionX, startPositionY) instanceof Corridor) {

            }


            manhattanDistance = getManhattanDistance(1,1,1,1);
        }
    }


    public static void explore(Matrix<Boolean> visitMatrix, Matrix<Tile> dungeon, Position position){
        int x = position.getxPos();
        int y = position.getyPos();

        visitMatrix.put(x, y, true);


        dungeon.getUp(x,y);
        dungeon.getRight(x,y);
        dungeon.getDown(x,y);
        dungeon.getLeft(x,y);



    }



    //todo maybe return double or something else
    public static int getManhattanDistance(int fromX, int fromY, int toX, int toY){
        return Math.abs(fromX-toX) + Math.abs(fromY-toY);
    }


    public static void writeToFile(String content, Dungeon dungeon) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        File file = new File(dungeon.toString() + " " + timestamp + " .txt");
        file.createNewFile();

        FileWriter fileWrite = new FileWriter(file);
        fileWrite.write(content);

        fileWrite.flush();
        fileWrite.close();
    }
}
