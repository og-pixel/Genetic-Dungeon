package Genetic_Algorithm.Mutation;

import Dungeon.Dungeon;

import java.util.ArrayList;

public interface MutatorImp {
    void mutateDungeon(Dungeon dungeon);
    void mutateDungeons(ArrayList<Dungeon> dungeonList);
}
