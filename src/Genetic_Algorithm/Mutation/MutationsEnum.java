package Genetic_Algorithm.Mutation;

import Algorithms.Matrix;
import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.*;
import Dungeon.*;

import java.util.ArrayList;
import java.util.Random;

public enum MutationsEnum implements MutatorImp, TileList{
    DEFAULT(0.1),
    CRAZY(0.5);

    private final double odds;

    MutationsEnum(double odds) {
        this.odds = odds;
    }


    @Override
    public void mutateDungeon(Dungeon dungeon) {
        Matrix dungeonMatrix = dungeon.getDungeonMatrix();
        Random random = new Random();
        for (int y = 0; y < dungeon.getDungeonHeight(); y++) {
            for (int x = 0; x < dungeon.getDungeonWidth(); x++) {
                if(random.nextDouble() <= odds){
                    if (dungeonMatrix.getElement(x,y) == CORRIDOR) dungeonMatrix.put(x, y, WALL);
                    else dungeonMatrix.put(x, y, CORRIDOR);
                }
            }
        }
    }

    @Override
    public void mutateDungeons(ArrayList<Dungeon> dungeonList) {
        for (Dungeon dungeon : dungeonList) {
            mutateDungeon(dungeon);
        }
    }
}
