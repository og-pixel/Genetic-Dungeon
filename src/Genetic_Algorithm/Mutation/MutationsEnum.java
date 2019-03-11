package Genetic_Algorithm.Mutation;

import Algorithms.Matrix;
import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Wall;

import java.util.ArrayList;
import java.util.Random;

public enum MutationsEnum implements MutatorImp{
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
                    if (dungeonMatrix.getElement(x,y) instanceof Corridor) dungeonMatrix.put(x, y, new Wall(x, y));
                    else dungeonMatrix.put(x, y, new Corridor(x, y));
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
