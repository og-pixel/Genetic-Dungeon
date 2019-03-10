package Dungeon;

import Algorithms.Matrix;
import Dungeon.Tile.*;

import java.io.Serializable;

public class Dungeon implements Serializable {
    /* Variables */
    private int dungeonWidth, dungeonHeight;
    private int score; //TODO it might be just a chromosome thing later

    /* Dungeon is created as a matrix made out of (abstract) Tile objects */
    private Matrix<Tile> dungeonMatrix;

    //TODO maybe I will create small Class to hold information
    private Point startPoint;
    private Point endPoint;


    /**
     * Constructor
     * @param dungeonWidth
     * @param dungeonHeight
     */
    public Dungeon(int dungeonWidth, int dungeonHeight) {
        this.dungeonWidth = dungeonWidth;
        this.dungeonHeight = dungeonHeight;

        dungeonMatrix = new Matrix<Tile>(dungeonWidth, dungeonHeight);//todo tile numbers and all of them might be the same object

        score = 0;
    }

    //Wrap dungeon matrix to the Dungeon class,
    // TODO its not for real application
    public Dungeon(Matrix<Tile> dungeonMatrix){
        this.dungeonMatrix = dungeonMatrix;

        this.dungeonWidth = dungeonMatrix.getWidth();
        this.dungeonHeight = dungeonMatrix.getHeight();

        score = 0;
    }


    public boolean createStartPosition(){
        for(int y  = 0; y < dungeonHeight; y++){
            for(int x  = 0; x < dungeonWidth; x++){
                if (dungeonMatrix.getElement(x,y).getTile() instanceof Corridor){
                    startPoint = new Point(x,y);
                    dungeonMatrix.put(x,y,new Start(x,y));
                    return true;
                }
            }
        }
        return false;
    }

    public boolean createEndPosition(){
        for(int y = dungeonHeight - 1; y > 0; y--){
            for(int x = dungeonWidth - 1; x > 0; x--){
                if (dungeonMatrix.getElement(x,y).getTile() instanceof Corridor){
                    endPoint = new Point(x,y);
                    dungeonMatrix.put(x,y,new End(x,y));
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Matrix<Tile> getDungeonMatrix() {
        return dungeonMatrix;
    }

    public String dungeonToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int y = 0; y < dungeonHeight; y++){
            stringBuilder.append("\n");
            for(int x = 0; x < dungeonWidth; x++){
                stringBuilder.append(dungeonMatrix.getElement(x,y));
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
}
