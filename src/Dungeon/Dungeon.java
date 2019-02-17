package Dungeon;

import Algorithms.Matrix;
import Dungeon.Tile.*;

public class Dungeon {
    /* Variables */
    private int dungeonWidth, dungeonHeight;
    private int score; //TODO it might be just a chromosome thing later

    /* Dungeon is created as a matrix made out of (abstract) Tile objects */
    private Matrix<Tile> dungeonMatrix;

    /*
    * Genetic Algorithm component
    * */


    //TODO maybe I will create small Class to hold information
//    private int startNodeX;
//    private int startNodeY;
//
//    private int endNodeX;
//    private int endNodeY;
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

        createStartPosition();
        createEndPosition();

        score = 0;
    }


    public boolean createStartPosition(){
        for(int y  = 0; y < dungeonHeight; y++){
            for(int x  = 0; x < dungeonWidth; x++){
                if (dungeonMatrix.getElement(x,y).getTile() instanceof Corridor){
                    startPosition = new Position(x,y);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean createEndPosition(){
        for(int y = dungeonHeight; y == 0 ; y--){
            for(int x  = dungeonWidth; x == 0 ; x--){
                if (dungeonMatrix.getElement(x,y).getTile() instanceof Corridor){
                    endPosition = new Position(x,y);
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

    public void printDungeon(){
        for(int y = 0; y < dungeonHeight; y++){
            System.out.println();
            for(int x = 0; x < dungeonWidth; x++){
                System.out.print(dungeonMatrix.getElement(x, y));
            }
        }
        System.out.println();
        System.out.println("****************************");
    }
}
