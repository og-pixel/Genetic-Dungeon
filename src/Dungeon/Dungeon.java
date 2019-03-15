package Dungeon;

import Algorithms.Matrix;

import java.io.Serializable;

public class Dungeon implements Serializable, TileList{

    /* Variables */
    private int dungeonWidth, dungeonHeight;
    private double score; //TODO it might be just a chromosome thing later

    /* Dungeon is created as a matrix made out of (abstract) Tile objects */
    private Matrix dungeonMatrix;

    //TODO maybe I will create small Class to hold information
    private Point startPoint;
    private Point endPoint;


    /**
     * INFORMATION ABOUT DUNGEON AFTER EVALUATION
     */
    private int numberOfRooms;
    private double wallToCorridorRatio;
    private int mutationCount;
    private int specialMutationCount; //todo this is stupid


    /**
     * Constructor
     *
     * @param dungeonWidth
     * @param dungeonHeight
     */
    public Dungeon(int dungeonWidth, int dungeonHeight) {
        this.dungeonWidth = dungeonWidth;
        this.dungeonHeight = dungeonHeight;

        dungeonMatrix = new Matrix(dungeonWidth, dungeonHeight);//todo tile numbers and all of them might be the same object

        score = -1;
    }

    //Wrap dungeon matrix to the Dungeon class,
    // TODO its not for real application
    public Dungeon(Matrix dungeonMatrix) {
        this.dungeonMatrix = dungeonMatrix;

        this.dungeonWidth = dungeonMatrix.getWidth();
        this.dungeonHeight = dungeonMatrix.getHeight();

        score = -1;
    }


    public boolean createStartPosition() {
        for (int y = 0; y < dungeonHeight; y++) {
            for (int x = 0; x < dungeonWidth; x++) {
                if (dungeonMatrix.getElement(x, y) == CORRIDOR) {
                    startPoint = new Point(x, y);
                    dungeonMatrix.put(x, y, START);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean createEndPosition() {
        for (int y = dungeonHeight - 1; y > 0; y--) {
            for (int x = dungeonWidth - 1; x > 0; x--) {
                if (dungeonMatrix.getElement(x, y) == CORRIDOR) {
                    endPoint = new Point(x, y);
                    dungeonMatrix.put(x, y, END);
                    return true;
                }
            }
        }
        return false;
    }

    public int getDungeonWidth() {
        return dungeonWidth;
    }

    public int getDungeonHeight() {
        return dungeonHeight;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Matrix getDungeonMatrix() {
        return dungeonMatrix;
    }

    public String dungeonToString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Room Count: ").append(numberOfRooms).append("\n");
        stringBuilder.append("Mutation Count: ").append(mutationCount).append("\n");
        stringBuilder.append("Special Mutation Count: ").append(specialMutationCount).append("\n");
        for (int y = 0; y < dungeonHeight; y++) {
            stringBuilder.append("\n");
            for (int x = 0; x < dungeonWidth; x++) {
                if (dungeonMatrix.getElement(x, y) == CORRIDOR)
                    stringBuilder.append(" ");
                else if (dungeonMatrix.getElement(x, y) == WALL)
                    stringBuilder.append("#");
                else if (dungeonMatrix.getElement(x, y) == START)
                    stringBuilder.append("s");
                else if (dungeonMatrix.getElement(x, y) == END)
                    stringBuilder.append("e");
            }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getMutationCount() {
        return mutationCount;
    }

    public void setMutationCount(int mutationCount) {
        this.mutationCount = mutationCount;
    }

    public int getSpecialMutationCount() {
        return specialMutationCount;
    }

    public void setSpecialMutationCount(int specialMutationCount) {
        this.specialMutationCount = specialMutationCount;
    }
}
