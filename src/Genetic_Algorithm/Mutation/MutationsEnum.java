package Genetic_Algorithm.Mutation;

import Algorithms.Matrix;
import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.*;
import Dungeon.*;

import java.util.ArrayList;
import java.util.Random;

public enum MutationsEnum implements MutatorImp, TileList{
    DEFAULT(0.01),
    SLIGHT(0.006),
    LOW(0.005),
    LOWER(0.002),
    HIGH(0.1);


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
                    dungeon.setMutationCount(dungeon.getMutationCount() + 1);//todo i know its kinda bad but it happens rarely and its for testing
                    if (random.nextFloat() <= 0.01){
                        try{
                            dungeonMatrix.put(x, y - 1, CORRIDOR);
                        }catch (Exception e){}
                        try{
                            dungeonMatrix.put(x, y, CORRIDOR);
                        }catch (Exception e){}
                        try{
                            dungeonMatrix.put(x, y + 1, CORRIDOR);
                        }catch (Exception e){}
                        dungeon.setSpecialMutationCount(dungeon.getSpecialMutationCount() + 1);//todo i know its kinda bad but it happens rarely and its for testing
                    }
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
