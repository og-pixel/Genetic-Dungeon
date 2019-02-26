package Algorithms;

import Dungeon.*;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Tile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


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



    public static ArrayList<Point> pathList = null;
    public static Matrix<Point> visitMap = null;
    public static HashMap<Point,Boolean> hashVisit = null;

    //TODO create A*
    public static void aStarTraverse(Dungeon dungeon){
        Matrix dungeonMatrix = dungeon.getDungeonMatrix();
        hashVisit = new HashMap<Point, Boolean>();
        hashVisit.put(new Point(1,1), true);

        int x = dungeon.getStartPoint().getXPos();
        int y = dungeon.getStartPoint().getYPos();

        int endPositionX = dungeon.getEndPoint().getXPos();
        int endPositionY = dungeon.getEndPoint().getYPos();

        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();

        Point currentPosition = new Point(x, y);
        currentPosition.setManhattanDistance(getManhattanDistance(x, y,endPositionX,endPositionY));


        visitMap = new Matrix<Point>(dungeonWidth, dungeonHeight);
        visitMap.fillMatrix(null);//todo silly
        visitMap.put(x, y, currentPosition);

        pathList = new ArrayList<Point>();
        pathList.add(currentPosition);


        int pp = 0;
        while(currentPosition.getManhattanDistance() != 0) {
            x = currentPosition.getXPos();
            y = currentPosition.getYPos();

            if (dungeonMatrix.getUp(x, y) instanceof Corridor) {
                Point point = new Point(x, y - 1);
                visitMap.put(x, y - 1, point); //todo this needs an if statment to check for already exisiting
                visitMap.getElement(x, y - 1).setManhattanDistance(getManhattanDistance(x, y - 1, endPositionX, endPositionY));
                pathList.add(point);
            }

            if (dungeonMatrix.getRight(x, y) instanceof Corridor) {
                Point point = new Point(x + 1, y);
                visitMap.put(x + 1, y, point);
                visitMap.getElement(x + 1, y).setManhattanDistance(getManhattanDistance(x + 1, y, endPositionX, endPositionY));
                pathList.add(point);
            }

            if (dungeonMatrix.getDown(x, y) instanceof Corridor) {
                Point point = new Point(x, y + 1);
                visitMap.put(x, y + 1, point);
                visitMap.getElement(x, y + 1).setManhattanDistance(getManhattanDistance(x, y + 1, endPositionX, endPositionY));
                pathList.add(point);
            }

            if (dungeonMatrix.getLeft(x, y) instanceof Corridor) {
                Point point = new Point(x - 1, y);
                visitMap.put(x - 1, y, point);
                visitMap.getElement(x - 1, y).setManhattanDistance(getManhattanDistance(x - 1, y, endPositionX, endPositionY));
                pathList.add(point);
            }
            Collections.sort(pathList, Comparator.comparing(Point::getManhattanDistance));

            if(pathList.get(0).getManhattanDistance() < currentPosition.getManhattanDistance()){
                currentPosition = pathList.get(0);
            }
            if(pp >= 100){
                System.out.println(pathList);
                System.out.println(visitMap);
                break;
            }
            else pp++;
        }
    }


    public static void explore(Matrix<Boolean> visitMatrix, Matrix<Tile> dungeon, Point point){
        int x = point.getXPos();
        int y = point.getYPos();

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
