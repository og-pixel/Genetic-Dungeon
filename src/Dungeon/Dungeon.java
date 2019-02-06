package Dungeon;

import Dungeon.Tile.*;
import Errors.VariableBoundsIncorrect;
import java.util.ArrayList;
import java.util.Random;

public class Dungeon {
    /* Variables */
    private int dungeonWidth, dungeonHeight;
    private int score; //TODO it might be just a chromosome thing later

    /* Dungeon is created as a matrix made out of (abstract) Tile objects */
    private ArrayList<ArrayList<Tile>> dungeonMatrix;

    /*
    * Genetic Algorithm component
    * */



    /**
     * Constructor
     * @param dungeonWidth
     * @param dungeonHeight
     */
    public Dungeon(int dungeonWidth, int dungeonHeight) {
//        if (diceRoll < 1 || diceRoll > 100) throw new VariableBoundsIncorrect(1,100);

        this.dungeonWidth = dungeonWidth;
        this.dungeonHeight = dungeonHeight;

        dungeonMatrix = new ArrayList<>();
        for (int yAxis = 0; yAxis < dungeonHeight; yAxis++) {
            dungeonMatrix.add(new ArrayList<>());
            for (int xAxis = 0; xAxis < dungeonWidth; xAxis++) {
                dungeonMatrix.get(yAxis).add(new EmptyTile(xAxis, yAxis));
            }
        }
        score = 0;
    }

    public Dungeon(int dungeonWidth, int dungeonHeight, boolean bool) {
        this.dungeonWidth = dungeonWidth;
        this.dungeonHeight = dungeonHeight;
        Random random = new Random();

        dungeonMatrix = new ArrayList<>();
        for (int yAxis = 0; yAxis < dungeonHeight; yAxis++) {
            dungeonMatrix.add(new ArrayList<>());
            for (int xAxis = 0; xAxis < dungeonWidth; xAxis++) {
                if(random.nextFloat() < 0.55)dungeonMatrix.get(yAxis).add(new Wall(xAxis, yAxis));
                else dungeonMatrix.get(yAxis).add(new Corridor(xAxis, yAxis));
            }
        }
        score = 0;
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

    public ArrayList<ArrayList<Tile>> getDungeonMatrix() {
        return dungeonMatrix;
    }

    public void printDungeon(){
        for(int y = 0; y < dungeonHeight; y++){
            System.out.println();
            for(int x = 0; x < dungeonWidth; x++){
                System.out.print(dungeonMatrix.get(y).get(x));
            }
        }
        System.out.println();
        System.out.println("****************************");
    }
}
