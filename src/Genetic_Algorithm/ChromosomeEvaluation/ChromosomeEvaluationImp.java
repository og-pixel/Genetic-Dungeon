package Genetic_Algorithm.ChromosomeEvaluation;

import Genetic_Algorithm.Data.*;
import Map.GameMap;

import java.util.ArrayList;

public interface ChromosomeEvaluationImp {
    EvolutionResults crossoverPopulation(ArrayList<GameMap> gameMapList);
}
