package Genetic_Algorithm;

import Dungeon.Dungeon;

import java.util.ArrayList;

public interface ChromosomeEvaluationImp {//todo number of generations is how many times we should repeat the whole thing
    ArrayList<Dungeon> crossoverPopulation(ArrayList<Dungeon> dungeonList, ArrayList<FitnessImp> fitnessImpList, int numberOfGenerations, MutationsEnum mutation);
    int getCrossoverPoint(Dungeon dungeon);
}
