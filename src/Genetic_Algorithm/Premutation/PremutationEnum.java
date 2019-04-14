package Genetic_Algorithm.Premutation;

import Dungeon.Dungeon;

import java.util.ArrayList;

public enum PremutationEnum implements PremutationImp{
    SWAP{},
    SCRAMBLE{},
    INVERSION{};

    @Override
    public void premutateDungeon(Dungeon dungeon) {

    }

    @Override
    public void premutateDungeons(ArrayList<Dungeon> dungeonList) {

    }
}
