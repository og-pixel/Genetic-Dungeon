package GameMap;

import DataStructure.Matrix;

import java.io.Serializable;

import static GameMap.TileList.*;


/**
 * GameMap is an extension to the Matrix class in map generation
 * It does contain information about its modifications
 */
public class GameMap implements Serializable{

    /* Variables */
    private int mapWidth, mapHeight;
    private double fitnessScore;

    //TODO I kinda don't like creating classes just to get points on the map better,
    // but I have no better idea ATM (maybe evaluation would just look for them and save)
    private Point startPoint;
    private Point endPoint;

    /*
    * GameMap is created as a matrix made out of number Tile objects representing different type of tile
    * (To be specified in TileList Interface)
    */
    private Matrix mapMatrix;

    /*
     * INFORMATION ABOUT DUNGEON AFTER EVALUATION
     */
    private int numberOfRooms = 0;
    private double wallToCorridorRatio = 0;
    private int mutationCount = 0;
    private int correctionsFound = 0; //todo maybe delete later

    /**
     * Constructor
     * @param mapWidth
     * @param mapHeight
     */
    public GameMap(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapMatrix = new Matrix(mapWidth, mapHeight);
        fitnessScore = 1;
    }

    //Constructor takes already existing matrix and reset rest of information
    public GameMap(Matrix mapMatrix) {
        this.mapWidth = mapMatrix.getWidth();
        this.mapHeight = mapMatrix.getHeight();
        this.mapMatrix = mapMatrix;

        //TODO for now i delclare those outside of constructor, I think it might be better
//        numberOfRooms = 0;
//        wallToCorridorRatio;
//        mutationCount;
//        correctionsFound;
        fitnessScore = 1;
    }

    public boolean createStartPosition() {
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (mapMatrix.getElement(x, y) == CORRIDOR) {
                    startPoint = new Point(x, y);
                    mapMatrix.put(x, y, START);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean createEndPosition() {
        for (int y = mapHeight - 1; y > 0; y--) {
            for (int x = mapWidth - 1; x > 0; x--) {
                if (mapMatrix.getElement(x, y) == CORRIDOR) {
                    endPoint = new Point(x, y);
                    mapMatrix.put(x, y, END);
                    return true;
                }
            }
        }
        return false;
    }

    //Getters
    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public double getFitnessScore() {
        return fitnessScore;
    }

    public int getCorrectionsFound() {
        return correctionsFound;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getMutationCount() {
        return mutationCount;
    }

    public Matrix getMapMatrix() {
        return mapMatrix;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    //Setters
    public void setFitnessScore(double fitnessScore) {
        this.fitnessScore = fitnessScore;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setMutationCount(int mutationCount) {
        this.mutationCount = mutationCount;
    }

    public void setCorrectionsFound(int correctionsFound) {
        this.correctionsFound = correctionsFound;
    }

    public void setMapMatrix(Matrix mapMatrix) {
        this.mapMatrix = mapMatrix;
    }

    /**
     * Get map, its information and return a string of it
     * @return Text representation od a map with additional information
     */
    public String mapToString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("DefaultRoom Count: ").append(numberOfRooms).append("\n");
        stringBuilder.append("DefaultMutatorStrategy Count: ").append(mutationCount).append("\n");
        stringBuilder.append("CorrectionStrategy Count: ").append(correctionsFound).append("\n");
        for (int y = 0; y < mapHeight; y++) {
            stringBuilder.append("\n");
            for (int x = 0; x < mapWidth; x++) {
                if (mapMatrix.getElement(x, y) == CORRIDOR)
                    stringBuilder.append(" ");
                else if (mapMatrix.getElement(x, y) == WALL)
                    stringBuilder.append("#");
                else if (mapMatrix.getElement(x, y) == START)
                    stringBuilder.append("s");
                else if (mapMatrix.getElement(x, y) == END)
                    stringBuilder.append("e");
            }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}

