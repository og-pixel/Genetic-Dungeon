package Genetic_Algorithm.Offspring;

import Dungeon.Dungeon;

import java.util.ArrayList;

public interface OffspringImp {
    ArrayList<Dungeon> createNewGeneration(ArrayList<Dungeon> list, double popSize, double topPop);
}
