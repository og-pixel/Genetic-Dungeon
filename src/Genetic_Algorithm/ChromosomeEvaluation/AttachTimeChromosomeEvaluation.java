package Genetic_Algorithm.ChromosomeEvaluation;

import Genetic_Algorithm.Data.EvolutionResults;
import Chromosome.*;

import java.util.ArrayList;
import java.util.logging.Level;

//This wrapper adds functionality to how much time it took
public class AttachTimeChromosomeEvaluation extends DecoratorChromosomeEvaluation {

    private AbstractChromosomeEvaluation chromosomeEvaluation;

    public AttachTimeChromosomeEvaluation(AbstractChromosomeEvaluation chromosomeEvaluation) {
        this.chromosomeEvaluation = chromosomeEvaluation;
    }

    @Override
    public EvolutionResults crossoverPopulation(ArrayList<Chromosome> chromosomeList) {
        long startTime = System.nanoTime();
        EvolutionResults k = chromosomeEvaluation.crossoverPopulation(chromosomeList);
        getLogger().setLevel(Level.INFO);
        getLogger().log(Level.INFO, "Evaluation took: " + ((System.nanoTime() - startTime) / 1000000000) + " seconds");
        getLogger().setLevel(Level.SEVERE);
        return k;
    }
}
