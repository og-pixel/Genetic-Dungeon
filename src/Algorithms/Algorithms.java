package Algorithms;

import DataStructure.Matrix;
import Chromosome.*;
import Exceptions.MatrixWrongDimensionsException;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


/**
 * Class implementing a bunch of static method algorithms
 * used to evaluate dungeon and give them a score
 */
public class Algorithms{

    //TODO this is a debug value to count how long i waste on deep cloning
    public static long TIME_ELAPSED = 0;

    public static Matrix floodFill(Chromosome chromosome, int x, int y){
        int dungeonWidth = chromosome.getMapWidth();
        int dungeonHeight = chromosome.getMapHeight();

        Matrix visitMap = new Matrix(dungeonWidth, dungeonHeight);
        visitMap.fillMatrix(0);

        flood(chromosome, visitMap, x, y);

        return visitMap;
    }

    private static void flood(Chromosome chromosome, Matrix visitMap, int x, int y){

        if(chromosome.getMapMatrix().getElement(x, y) == TileList.CORRIDOR) {
            visitMap.put(x, y, 1);

            if (x > 0 && chromosome.getMapMatrix().getElement(x - 1, y) == TileList.CORRIDOR) {
                if (visitMap.getElement(x - 1, y) == 0) {
                    flood(chromosome, visitMap, x - 1, y);
                }
            }
            if (x + 1 < chromosome.getMapWidth() && chromosome.getMapMatrix().getElement(x + 1, y) == TileList.CORRIDOR) {
                if (visitMap.getElement(x + 1, y) == 0) {
                    flood(chromosome, visitMap, x + 1, y);
                }
            }
            if (y > 0 && chromosome.getMapMatrix().getElement(x, y - 1) == TileList.CORRIDOR) {
                if (visitMap.getElement(x, y - 1) == 0) {
                    flood(chromosome, visitMap, x, y - 1);
                }
            }
            if (y + 1 < chromosome.getMapHeight() && chromosome.getMapMatrix().getElement(x, y + 1) == TileList.CORRIDOR) {
                if (visitMap.getElement(x, y + 1) == 0) {
                    flood(chromosome, visitMap, x, y + 1);
                }
            }
        }
    }


    //TODO not working ATM
    public static void aStarTraverse(Chromosome chromosome){
        Matrix dungeonMatrix = chromosome.getMapMatrix();

        ArrayList<Point> traverseList = new ArrayList<>();


        int startPositionX = chromosome.getStartPoint().getXPos();
        int startPositionY = chromosome.getStartPoint().getYPos();

        int endPositionX = chromosome.getEndPoint().getXPos();
        int endPositionY = chromosome.getEndPoint().getYPos();

        int dungeonWidth = chromosome.getMapWidth();
        int dungeonHeight = chromosome.getMapHeight();

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

            if (dungeonMatrix.getUp(x, y) == TileList.CORRIDOR ||
                    dungeonMatrix.getUp(x, y) == TileList.END) {
                Point point1 = new Point(x, y - 1, currentSteps + 1);
                point1.setSteps(Algorithms.getManhattanDistance(x, y - 1,
                        startPositionX, startPositionY));

                point1.setDistanceToFinish(Algorithms.getManhattanDistance(x, y - 1,
                        endPositionX, endPositionY));

                if(pointExists(point1, traverseList))canExpand[0] = true;
                else
                traverseList.add(point1);
            }

            if (dungeonMatrix.getRight(x, y) == TileList.CORRIDOR ||
                    dungeonMatrix.getRight(x, y) == TileList.END) {
                Point point2 = new Point(x + 1, y, currentSteps + 1);
                point2.setSteps(Algorithms.getManhattanDistance(x + 1, y,
                        startPositionX, startPositionY));

                point2.setDistanceToFinish(Algorithms.getManhattanDistance(x + 1, y,
                        endPositionX,endPositionY));

                if(pointExists(point2, traverseList))canExpand[1] = true;
                else
                traverseList.add(point2);
            }

            if (dungeonMatrix.getDown(x, y) == TileList.CORRIDOR ||
                    dungeonMatrix.getDown(x, y) == TileList.END) {
                Point point3 = new Point(x, y + 1, currentSteps + 1);
                point3.setSteps(Algorithms.getManhattanDistance(x, y + 1,
                        startPositionX, startPositionY));

                point3.setDistanceToFinish(Algorithms.getManhattanDistance(x, y + 1,
                        endPositionX,endPositionY));


                if(pointExists(point3, traverseList))canExpand[2] = true;
                else
                traverseList.add(point3);
            }

            if (dungeonMatrix.getLeft(x, y) == TileList.CORRIDOR ||
                    dungeonMatrix.getLeft(x, y) == TileList.END) {
                Point point4 = new Point(x - 1, y, currentSteps + 1);
                point4.setSteps(Algorithms.getManhattanDistance(x - 1, y,
                        startPositionX, startPositionY));

                point4.setDistanceToFinish(Algorithms.getManhattanDistance(x - 1, y,
                        endPositionX,endPositionY));

                if(pointExists(point4, traverseList))canExpand[3] = true;
                else
                traverseList.add(point4);
            }

            list.add(currentPosition);

            if(!canExpand[0] && !canExpand[1] && !canExpand[2] && !canExpand[3]) {
                currentPosition.setTotalCost(99999);
            }



            Collections.sort(traverseList, Comparator.comparing(Point::getDistanceToFinish));

//            TODO not working ATM
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

    /**
     * check if point is already on list
     * @param point
     * @param traverseList
     * @return
     */
    private static boolean pointExists(Point point, ArrayList<Point> traverseList){
        for (Point value : traverseList) {
            if (point.getXPos() == value.getXPos()
                    && point.getYPos() == value.getYPos()) {

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


    /**
     * Get the closest possible distance (in manhattan-like direction)
     * from point a to point b
     * @param  pointAX point a on X coordinate
     * @param pointAY point a on Y coordinate
     * @param pointBX point b on X coordinate
     * @param  pointBY point b on Y coordinate
     * @return manhattanDistance between these two points on the map
     */
    public static int getManhattanDistance(int pointAX, int pointAY, int pointBX, int pointBY){
        return Math.abs(pointAX - pointBX) + Math.abs(pointAY - pointBY);
    }

    /**
     * Compare two dungeon maps and get a "distance" between them,
     * which is how many elements are actually different between each other
     * @param matrix1
     * @param matrix2
     * @return
     */
    public static int getHammingDistance(Matrix matrix1, Matrix matrix2){
        if(matrix1.getWidth() != matrix2.getWidth()
                || matrix1.getHeight() != matrix2.getHeight()){
            throw new MatrixWrongDimensionsException();
        }
        int distance = 0;

        for (int y = 0; y < matrix1.getHeight(); y++) {
            for (int x = 0; x < matrix2.getWidth(); x++) {
                //To increase a distance element needs to be different
                // AND does not contain UNIVERSAL (don't care) value
                if (matrix1.getElement(x, y) != TileList.UNIVERSAL ||
                        matrix1.getElement(x, y) != matrix2.getElement(x, y)){
                    distance++;
                }
            }
        }
        return distance;
    }

    /**
     * Wrapper method that takes dungeon maps instead
      * @param chromosome1
     * @param chromosome2
     * @return
     */
    public static int getHammingDistance(Chromosome chromosome1, Chromosome chromosome2){
        Matrix matrix1 = chromosome1.getMapMatrix();
        Matrix matrix2 = chromosome2.getMapMatrix();

        return getHammingDistance(matrix1, matrix2);
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

    public static void writeToFile(String content, Object object) throws IOException {
        if(object instanceof Chromosome)writeToFile(content, (Chromosome) object);
        else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            File file = new File(content + ":" + timestamp + ".txt");
            file.createNewFile();

            FileWriter fileWrite = new FileWriter(file);
            fileWrite.write(String.valueOf(object));

            fileWrite.flush();
            fileWrite.close();
        }
    }

    private static void writeToFile(String content, Chromosome chromosome) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        int score = (int) chromosome.getFitnessScore();
        File file = new File( content + ": " + score + ": " + timestamp.toString() + ":" +  ".txt");
        file.createNewFile();

        FileWriter fileWrite = new FileWriter(file);
        content = chromosome.mapToString();
        fileWrite.write(content);

        fileWrite.flush();
        fileWrite.close();
    }

    public static Object deepClone(Object object){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Chromosome deepClone(Chromosome chromosome){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(chromosome);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Chromosome) ois.readObject();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Chromosome> deepClone(ArrayList<Chromosome> chromosome){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(chromosome);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (ArrayList<Chromosome>) ois.readObject();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
