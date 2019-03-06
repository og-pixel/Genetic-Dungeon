package Algorithms;

import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Tile;
import Dungeon.Tile.Wall;
import Exceptions.VariableBoundsException;

import java.util.Random;

public class Mutator {

    private double odd;

    public Mutator(double odd){
        if(odd < 0.01 || odd > 1) throw new VariableBoundsException(0.01, 1);
        this.odd = 0.1;
    }


    public void mutateDungeon(Dungeon dungeon){
        Matrix dungeonMatrix = dungeon.getDungeonMatrix();
        Random random = new Random();
        for (int y = 0; y < dungeon.getDungeonHeight(); y++) {
            for (int x = 0; x < dungeon.getDungeonWidth(); x++) {
                if(random.nextDouble() <= odd){
                    if (dungeonMatrix.getElement(x,y) instanceof Corridor) dungeonMatrix.put(x, y, new Wall(x, y));
                    else dungeonMatrix.put(x, y, new Corridor(x, y));
                }
            }
        }
    }

}