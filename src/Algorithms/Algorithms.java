package Algorithms;

import Dungeon.*;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.End;
import Dungeon.Tile.Tile;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


/**
 * Static class implementing a bunch of algorithms
 * used to evaluate dungeon and give them a score
 */
public class Algorithms implements TileList{

    //TODO I think I can rewrite flood a little bit so it can
    // check for start and finish node (I still need A*)

    //todo for now it returns a a matrix of booleans
    public static Matrix floodFill(Dungeon dungeon, int x, int y){
        //TODO i might not need that
//        if (dungeon.getDungeonMatrix().getElement(x, y) == null) {
//            return null;
//        }

        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();

        Matrix visitMap = new Matrix(dungeonWidth, dungeonHeight);
        visitMap.fillMatrix(0);

        flood(dungeon, visitMap, x, y);

        return visitMap;
    }

    private static void flood(Dungeon dungeon, Matrix visitMap, int x, int y){

        if(dungeon.getDungeonMatrix().getElement(x, y) == CORRIDOR) {
            visitMap.put(x, y, 1);

            if (x > 0 && dungeon.getDungeonMatrix().getElement(x - 1, y) == CORRIDOR) {
                if (visitMap.getElement(x - 1, y) == 0) {
                    flood(dungeon, visitMap, x - 1, y);
                }
            }
            if (x + 1 < dungeon.getDungeonWidth() && dungeon.getDungeonMatrix().getElement(x + 1, y) == CORRIDOR) {
                if (visitMap.getElement(x + 1, y) == 0) {
                    flood(dungeon, visitMap, x + 1, y);
                }
            }
            if (y > 0 && dungeon.getDungeonMatrix().getElement(x, y - 1) == CORRIDOR) {
                if (visitMap.getElement(x, y - 1) == 0) {
                    flood(dungeon, visitMap, x, y - 1);
                }
            }
            if (y + 1 < dungeon.getDungeonHeight() && dungeon.getDungeonMatrix().getElement(x, y + 1) == CORRIDOR) {
                if (visitMap.getElement(x, y + 1) == 0) {
                    flood(dungeon, visitMap, x, y + 1);
                }
            }
        }
    }



    //TODO create A*
    public static void aStarTraverse(Dungeon dungeon){
        Matrix dungeonMatrix = dungeon.getDungeonMatrix();

        ArrayList<Point> traverseList = new ArrayList<>();


        int startPositionX = dungeon.getStartPoint().getXPos();
        int startPositionY = dungeon.getStartPoint().getYPos();

        int endPositionX = dungeon.getEndPoint().getXPos();
        int endPositionY = dungeon.getEndPoint().getYPos();

        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();

        Point currentPosition = new Point(startPositionX, startPositionY);

        currentPosition.setSteps(1);
        currentPosition.setDistanceToFinish(getManhattanDistance(startPositionX, startPositionY,
                                                                    endPositionX, endPositionY));

        traverseList.add(currentPosition);

        int x = currentPosition.getXPos();
        int y = currentPosition.getYPos();

        int point = 0;
        int steps = 0;
        ArrayList<Point> list = new ArrayList<>();

        while(currentPosition.getDistanceToFinish() != 0) {
            x = currentPosition .getXPos();
            y = currentPosition.getYPos();
            int currentSteps = currentPosition.getSteps();

            boolean canExpand[] = new boolean[4];
            for (int i = 0; i < canExpand.length; i++) {
                canExpand[i] = false;
            }

            if (dungeonMatrix.getUp(x, y) == CORRIDOR ||
                    dungeonMatrix.getUp(x, y) == END) {
                Point point1 = new Point(x, y - 1, currentSteps + 1);
                point1.setSteps(Algorithms.getManhattanDistance(x, y - 1,
                        startPositionX, startPositionY));

                point1.setDistanceToFinish(Algorithms.getManhattanDistance(x, y - 1,
                        endPositionX, endPositionY));

                if(checkIfPointExists(point1, traverseList))canExpand[0] = true;
                else
                traverseList.add(point1);
            }

            if (dungeonMatrix.getRight(x, y) == CORRIDOR ||
                    dungeonMatrix.getRight(x, y) == END) {
                Point point2 = new Point(x + 1, y, currentSteps + 1);
                point2.setSteps(Algorithms.getManhattanDistance(x + 1, y,
                        startPositionX, startPositionY));

                point2.setDistanceToFinish(Algorithms.getManhattanDistance(x + 1, y,
                        endPositionX,endPositionY));

                if(checkIfPointExists(point2, traverseList))canExpand[1] = true;
                else
                traverseList.add(point2);
            }

            if (dungeonMatrix.getDown(x, y) == CORRIDOR ||
                    dungeonMatrix.getDown(x, y) == END) {
                Point point3 = new Point(x, y + 1, currentSteps + 1);
                point3.setSteps(Algorithms.getManhattanDistance(x, y + 1,
                        startPositionX, startPositionY));

                point3.setDistanceToFinish(Algorithms.getManhattanDistance(x, y + 1,
                        endPositionX,endPositionY));


                if(checkIfPointExists(point3, traverseList))canExpand[2] = true;
                else
                traverseList.add(point3);
            }

            if (dungeonMatrix.getLeft(x, y) == CORRIDOR ||
                    dungeonMatrix.getLeft(x, y) == END) {
                Point point4 = new Point(x - 1, y, currentSteps + 1);
                point4.setSteps(Algorithms.getManhattanDistance(x - 1, y,
                        startPositionX, startPositionY));

                point4.setDistanceToFinish(Algorithms.getManhattanDistance(x - 1, y,
                        endPositionX,endPositionY));

                if(checkIfPointExists(point4, traverseList))canExpand[3] = true;
                else
                traverseList.add(point4);
            }

            list.add(currentPosition);

            if(!canExpand[0] && !canExpand[1] && !canExpand[2] && !canExpand[3]) {
                currentPosition.setTotalCost(99999);
            }



            Collections.sort(traverseList, Comparator.comparing(Point::getDistanceToFinish));

//            TODO DODODODOD
            // because of this, it is working, but not like real A* would
            for (int i = 1; i < traverseList.size(); i++) {
                if(currentPosition.getTotalCost() == traverseList.get(i).getTotalCost()){
                    currentPosition = traverseList.get(new Random().nextInt(traverseList.size()));
                    break;
                }
            }

//            if(currentPosition.getXPos() == endPositionX &&
//                    currentPosition.getYPos() == endPositionY){
//                break
//                System.out.println();
//            }


            if(point > 1500000){
                System.err.println("Could not find ending");

                Matrix visitMap = new Matrix(dungeonWidth, dungeonHeight);
                visitMap.fillMatrix(0);

                for(int i = 0; i < traverseList.size(); i++) {
                    visitMap.put(traverseList.get(i).getXPos(),
                            traverseList.get(i).getYPos(), 1);
                }

                try {
                    StringBuilder sb = new StringBuilder();
                    for (int c = 0; c < visitMap.getHeight(); c++) {
                        sb.append("\n");
                        for (int z = 0; z < visitMap.getWidth(); z++) {
                            if(visitMap.getElement(z, c) == 1)sb.append("░");
                            else sb.append("x");
                        }
                    }



                    writeToFile(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }
                else point++;
        }
        System.err.println(traverseList);
    }

    //TODO check if point is already on list
    private static boolean checkIfPointExists(Point point, ArrayList<Point> traverseList){
        for (int i = 0; i < traverseList.size(); i++) {
            if(point.getXPos() == traverseList.get(i).getXPos()
                    && point.getYPos() == traverseList.get(i).getYPos()){
                return true;
            }
        }
        return false;
    }


    public static void explore(Matrix visitMatrix, Matrix dungeon, Point point){
        int x = point.getXPos();
        int y = point.getYPos();

        visitMatrix.put(x, y, 1);


        dungeon.getUp(x,y);
        dungeon.getRight(x,y);
        dungeon.getDown(x,y);
        dungeon.getLeft(x,y);
    }



    //todo maybe return double or something else
    public static int getManhattanDistance(int fromX, int fromY, int toX, int toY){
        return Math.abs(fromX-toX) + Math.abs(fromY-toY);
    }


    /**
     * Compare two dungeon maps and get a "distance" between them,
     * which is how many elements are actually different between each other
     * COMPARE ONE TO TWO
     * @param dungeon1
     * @param dungeon2
     * @return
     */
    public static int getHammingDistance(Dungeon dungeon1, Dungeon dungeon2){
        Matrix matrix1 = dungeon1.getDungeonMatrix();
        Matrix matrix2 = dungeon2.getDungeonMatrix();
        int distance = 0;

        for (int y = 0; y < dungeon1.getDungeonHeight(); y++) {
            for (int x = 0; x < dungeon2.getDungeonWidth(); x++) {
                if (matrix1.getElement(x, y) != matrix2.getElement(x, y)) distance++;
            }
        }

        return distance;
    }



    //TODO rewrite
    public static void writeToFile(String content, Dungeon dungeon) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        int score = (int) dungeon.getScore();
        File file = new File("a" + score + " : " + content + " : " + timestamp.toString() + " : " +  ".txt");
        file.createNewFile();

        FileWriter fileWrite = new FileWriter(file);
        content = dungeon.dungeonToString();
        fileWrite.write(content);

        fileWrite.flush();
        fileWrite.close();
    }

    public static void writeToFile(String content) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        File file = new File(timestamp + " aStar.txt");
        file.createNewFile();

        FileWriter fileWrite = new FileWriter(file);
        fileWrite.write(content);

        fileWrite.flush();
        fileWrite.close();
    }





    //THis method is so fucking dumb
    public static Dungeon deepClone(Dungeon dungeon){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(dungeon);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Dungeon) ois.readObject();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //THis method is so fucking dumb
    public static ArrayList<Dungeon> deepClone(ArrayList<Dungeon> dungeon){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(dungeon);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (ArrayList<Dungeon>) ois.readObject();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
