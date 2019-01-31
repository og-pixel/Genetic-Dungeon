package Dungeon;

import Dungeon.Tile.*;
import Errors.VariableBoundsIncorrect;
import java.util.ArrayList;

public class Dungeon {


    /* Variables */
    private int dungeonWidth, dungeonHeight;

    /* Dungeon is created as a matrix made out of (abstract) Tile objects */
    private ArrayList<ArrayList<Tile>> dungeonMatrix;



    /**
     * Constructor
     * @param dungeonWidth
     * @param dungeonHeight
     */
    public Dungeon(int dungeonWidth, int dungeonHeight, int noOfRooms, int maxRoomSize, int diceRoll, int minimumCorridorLength) {
        if (diceRoll < 1 || diceRoll > 100) throw new VariableBoundsIncorrect(1,100);

        this.dungeonWidth = dungeonWidth;
        this.dungeonHeight = dungeonHeight;

        dungeonMatrix = new ArrayList<>();
        for (int yAxis = 0; yAxis < dungeonHeight; yAxis++) {
            dungeonMatrix.add(new ArrayList<>());
            for (int xAxis = 0; xAxis < dungeonWidth; xAxis++) {
                dungeonMatrix.get(yAxis).add(new EmptyTile(xAxis, yAxis));
            }
        }
    }
    public int getDungeonWidth() {
        return dungeonWidth;
    }

    public int getDungeonHeight() {
        return dungeonHeight;
    }

    public ArrayList<ArrayList<Tile>> getDungeonMatrix() {
        return dungeonMatrix;
    }
}
