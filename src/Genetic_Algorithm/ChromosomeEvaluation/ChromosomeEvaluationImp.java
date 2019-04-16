package Genetic_Algorithm.ChromosomeEvaluation;

import Genetic_Algorithm.Data.*;
import Map.Map;
import java.util.ArrayList;

public interface ChromosomeEvaluationImp {
    EvolutionResults crossoverPopulation(ArrayList<Map> mapList);
}
