package Dungeon;

import Dungeon.Tile.*;
import Errors.VariableBoundsIncorrect;
import java.util.ArrayList;

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
}
