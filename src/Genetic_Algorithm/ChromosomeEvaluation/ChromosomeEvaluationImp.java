package Genetic_Algorithm.ChromosomeEvaluation;

import Dungeon.Dungeon;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.Mutation.MutationsEnum;
import Genetic_Algorithm.Data.*;

import java.util.ArrayList;

public interface ChromosomeEvaluationImp {//todo number of generations is how many times we should repeat the whole thing
    EvolutionDetails crossoverPopulation(ArrayList<Dungeon> dungeonList, ArrayList<FitnessImp> fitnessImpList, int numberOfGenerations, MutationsEnum mutation);
    int getCrossoverPoint(Dungeon dungeon);
}
