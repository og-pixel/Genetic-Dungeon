package Genetic_Algorithm.ChromosomeEvaluation;

import Genetic_Algorithm.Data.EvolutionResults;
import Chromosome.*;

import java.util.ArrayList;
import java.util.logging.Level;

public class AttachLogChromosomeEvaluation extends DecoratorChromosomeEvaluation {

    private AbstractChromosomeEvaluation abstractChromosomeEvaluation;

    public AttachLogChromosomeEvaluation(AbstractChromosomeEvaluation abstractChromosomeEvaluation){
        this.abstractChromosomeEvaluation = abstractChromosomeEvaluation;
    }


    @Override
    public EvolutionResults crossoverPopulation(ArrayList<Chromosome> chromosomeList) {
        abstractChromosomeEvaluation.getLogger().setLevel(Level.INFO);
        EvolutionResults k = abstractChromosomeEvaluation.crossoverPopulation(chromosomeList);
        return k;
    }
}
