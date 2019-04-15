package Genetic_Algorithm.ChromosomeEvaluation;

import Algorithms.Algorithms;
import Dungeon.Dungeon;
import Genetic_Algorithm.ChromosomeEvaluation.AbstractChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.DecoratorChromosomeEvaluation;
import Genetic_Algorithm.Data.EvolutionDetails;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.ManualCorrections.CorrectionEnum;
import Genetic_Algorithm.Mutation.MutationsEnum;
import Genetic_Algorithm.Premutation.PremutationEnum;
import Genetic_Algorithm.Selection.SelectionEnum;

import java.io.IOException;
import java.util.ArrayList;

//This wrapper adds functionality to print last generation of maps
public class PrintBasicChromosomeEvaluation extends DecoratorChromosomeEvaluation {

    private AbstractChromosomeEvaluation abstractChromosomeEvaluation;


    public PrintBasicChromosomeEvaluation(AbstractChromosomeEvaluation abstractChromosomeEvaluation) {
        this.abstractChromosomeEvaluation = abstractChromosomeEvaluation;
    }

    @Override
    public EvolutionDetails crossoverPopulation(ArrayList<Dungeon> mapList, ArrayList<FitnessImp> fitnessImpList, int numberOfGenerations, MutationsEnum mutation, SelectionEnum selection, PremutationEnum premutation, CorrectionEnum correction) {
        long startTime = System.nanoTime();
        EvolutionDetails k = abstractChromosomeEvaluation.crossoverPopulation(mapList, fitnessImpList, numberOfGenerations, mutation, selection, premutation, correction);//TODO its kinda like calling super
        //TODO for now there is no difference between regular and wrapper object (they do the same)

        System.out.println("It took:" + ((System.nanoTime() - startTime) / 1000000000) + "seconds");
        return k;
    }


    @Override
    public int getCrossoverPoint(Dungeon dungeon) {
        return 0;
    }
}
