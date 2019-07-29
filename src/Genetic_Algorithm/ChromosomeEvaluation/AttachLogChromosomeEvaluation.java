package Genetic_Algorithm.ChromosomeEvaluation;

import Genetic_Algorithm.Data.EvolutionResults;
import GameMap.GameMap;

import java.util.ArrayList;
import java.util.logging.Level;

public class AttachLogChromosomeEvaluation extends DecoratorChromosomeEvaluation {

    private AbstractChromosomeEvaluation abstractChromosomeEvaluation;

    public AttachLogChromosomeEvaluation(AbstractChromosomeEvaluation abstractChromosomeEvaluation){
        this.abstractChromosomeEvaluation = abstractChromosomeEvaluation;
    }


    @Override
    public EvolutionResults crossoverPopulation(ArrayList<GameMap> gameMapList) {
        abstractChromosomeEvaluation.getLogger().setLevel(Level.INFO);
        EvolutionResults k = abstractChromosomeEvaluation.crossoverPopulation(gameMapList);
        return k;
    }
}
