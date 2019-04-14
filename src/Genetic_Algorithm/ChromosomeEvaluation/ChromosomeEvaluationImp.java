package Genetic_Algorithm.ChromosomeEvaluation;

import Dungeon.Dungeon;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.Mutation.MutationsEnum;
import Genetic_Algorithm.Data.*;
import Genetic_Algorithm.Premutation.PremutationEnum;
import Genetic_Algorithm.Selection.SelectionEnum;
import Genetic_Algorithm.Selection.SelectionImp;

import java.util.ArrayList;

public interface ChromosomeEvaluationImp {//todo number of generations is how many times we should repeat the whole thing
    EvolutionDetails crossoverPopulation(ArrayList<Dungeon> dungeonList, ArrayList<FitnessImp> fitnessImpList, int numberOfGenerations, MutationsEnum mutation, SelectionEnum selection, PremutationEnum premutation);
    int getCrossoverPoint(Dungeon dungeon);
}
