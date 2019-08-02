package Genetic_Algorithm.ChromosomeEvaluation;

import Genetic_Algorithm.Data.EvolutionResults;
import Chromosome.*;

import java.util.ArrayList;
import java.util.logging.Level;

//This wrapper adds functionality to how much time it took
public class MeasureTimeChromosomeEvaluation extends DecoratorChromosomeEvaluation {

    private AbstractChromosomeEvaluation abstractChromosomeEvaluation;

    public MeasureTimeChromosomeEvaluation(AbstractChromosomeEvaluation abstractChromosomeEvaluation) {
        this.abstractChromosomeEvaluation = abstractChromosomeEvaluation;
    }

    @Override
    public EvolutionResults crossoverPopulation(ArrayList<Chromosome> chromosomeList) {
        long startTime = System.nanoTime();
        EvolutionResults k = abstractChromosomeEvaluation.crossoverPopulation(chromosomeList);
        getLogger().setLevel(Level.INFO);
        getLogger().log(Level.INFO, "It took: " + ((System.nanoTime() - startTime) / 1000000000) + " seconds");
        getLogger().setLevel(Level.SEVERE);
        return k;
    }
}
