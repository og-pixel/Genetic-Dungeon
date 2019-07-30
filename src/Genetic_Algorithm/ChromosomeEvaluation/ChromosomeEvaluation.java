package Genetic_Algorithm.ChromosomeEvaluation;

import Genetic_Algorithm.Data.*;
import GameMap.GameMap;

import java.util.ArrayList;

public interface ChromosomeEvaluation {
    EvolutionResults crossoverPopulation(ArrayList<GameMap> gameMapList);
}
