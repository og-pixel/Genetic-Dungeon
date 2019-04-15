package Genetic_Algorithm.ChromosomeEvaluation;

import Genetic_Algorithm.Data.EvolutionResults;

//This wrapper adds functionality to how much time it took
public class MeasureTimeChromosomeEvaluation extends DecoratorChromosomeEvaluation {

    private AbstractChromosomeEvaluation abstractChromosomeEvaluation;


    public MeasureTimeChromosomeEvaluation(AbstractChromosomeEvaluation abstractChromosomeEvaluation) {
        this.abstractChromosomeEvaluation = abstractChromosomeEvaluation;
    }

    @Override
    public EvolutionResults crossoverPopulation() {
        long startTime = System.nanoTime();
        EvolutionResults k = abstractChromosomeEvaluation.crossoverPopulation();
        System.out.println("It took:" + ((System.nanoTime() - startTime) / 1000000000) + "seconds");
        return k;
    }
}
