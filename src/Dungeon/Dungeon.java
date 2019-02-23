package Dungeon;

import Algorithms.Matrix;
import Dungeon.Tile.*;

public class Dungeon {
    /* Variables */
    private int dungeonWidth, dungeonHeight;
    private int score; //TODO it might be just a chromosome thing later

    /* Dungeon is created as a matrix made out of (abstract) Tile objects */
    private Matrix<Tile> dungeonMatrix;

    //TODO maybe I will create small Class to hold information
    private Position startPosition;
    private Position endPosition;


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


    public boolean createStartPosition(){
        for(int y  = 0; y < dungeonHeight; y++){
            for(int x  = 0; x < dungeonWidth; x++){
                if (dungeonMatrix.getElement(x,y).getTile() instanceof Corridor){
                    startPosition = new Position(x,y);
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
                    endPosition = new Position(x,y);
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

    public String printDungeon(){
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

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }
}
