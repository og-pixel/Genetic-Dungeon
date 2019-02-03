package Algorithms;

import Dungeon.Dungeon;
import Dungeon.Tile.Tile;
import com.sun.javafx.scene.traversal.Algorithm;

import java.util.ArrayList;

public class Idea implements Fitness{

    @Override
    public int evaluateDungeon(Dungeon dungeon) {
        ArrayList<ArrayList<Tile>> matrix = dungeon.getDungeonMatrix();
        
        for(int y = 0; y < dungeon.getDungeonHeight(); y++){
            for(int x = 0; x < dungeon.getDungeonWidth(); x++){
                matrix.
            }
        }
        Algorithms.floodFill(dungeon);


        dungeon.setScore(1); //todo this way i set score so far
        return 0;
    }

}
