package Genetic_Algorithm.ChromosomeEvaluation;

import Algorithms.Algorithms;
import Dungeon.Dungeon;
import Genetic_Algorithm.ChromosomeEvaluation.AbstractChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.DecoratorChromosomeEvaluation;
import Genetic_Algorithm.Data.EvolutionDetails;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.ManualCorrections.CorrectionEnum;
import Genetic_Algorithm.Mutation.MutationsEnum;
import Genetic_Algorithm.Offspring.OffspringEnum;
import Genetic_Algorithm.Premutation.PremutationEnum;
import Genetic_Algorithm.Selection.SelectionEnum;

import java.io.IOException;
import java.util.ArrayList;

//This wrapper adds functionality to print last generation of maps
public class MeasureTimeChromosomeEvaluation extends DecoratorChromosomeEvaluation {

    private AbstractChromosomeEvaluation abstractChromosomeEvaluation;


    public MeasureTimeChromosomeEvaluation(AbstractChromosomeEvaluation abstractChromosomeEvaluation) {
        this.abstractChromosomeEvaluation = abstractChromosomeEvaluation;
    }

    @Override
    public EvolutionDetails crossoverPopulation() {
        long startTime = System.nanoTime();
        EvolutionDetails k = abstractChromosomeEvaluation.crossoverPopulation();//TODO its kinda like calling super

        System.out.println("It took:" + ((System.nanoTime() - startTime) / 1000000000) + "seconds");
        return k;
    }
}
