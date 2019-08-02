package Genetic_Algorithm.ChromosomeEvaluation;

import Genetic_Algorithm.Data.*;
import Chromosome.*;

import java.util.ArrayList;

public interface ChromosomeEvaluation {
    EvolutionResults crossoverPopulation(ArrayList<Chromosome> chromosomeList);
}
