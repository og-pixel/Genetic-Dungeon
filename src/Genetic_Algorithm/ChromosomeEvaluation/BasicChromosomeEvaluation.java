package Genetic_Algorithm.ChromosomeEvaluation;

import Algorithms.Algorithms;
import Chromosome.*;
import Genetic_Algorithm.MutatorStrategy.MutatorStrategy;
import Genetic_Algorithm.CrossoverStrategy.CrossoverStrategy;
import Genetic_Algorithm.SelectionStrategy.SelectionStrategy;
import Exceptions.VariableBoundsException;
import Genetic_Algorithm.Data.EvolutionResults;
import Genetic_Algorithm.FitnessStrategy.FitnessStrategy;
import Genetic_Algorithm.CorrectionStrategy.CorrectionStrategy;

import java.util.ArrayList;
import java.util.logging.Level;

public class BasicChromosomeEvaluation extends AbstractChromosomeEvaluation {

    private double populationSize;
    private int numberOfGenerations;
    private double selectionFraction;

    private ArrayList<FitnessStrategy> fitnessStrategyList;
    private MutatorStrategy mutatorStrategy;
    private SelectionStrategy selectionStrategy;
    private CrossoverStrategy crossoverStrategy;
    private ArrayList<CorrectionStrategy> correctionStrategyList;

    public BasicChromosomeEvaluation(double populationSize, int numberOfGenerations, double selectionFraction,
                                     ArrayList<FitnessStrategy> fitnessStrategyList, MutatorStrategy mutatorStrategy,
                                     SelectionStrategy selectionStrategy, ArrayList<CorrectionStrategy> correctionStrategyList,
                                     CrossoverStrategy crossoverStrategy){

        if(selectionFraction <= 0 || selectionFraction >= 1)throw new VariableBoundsException(0, 1);
        this.selectionFraction = selectionFraction;

        if(populationSize > 1000) System.err.println("NoiseStrategy size is beyond 1000, program might take a long time and" +
                "results probably won't be better");

        this.populationSize = populationSize;
        this.fitnessStrategyList = fitnessStrategyList;
        this.numberOfGenerations = numberOfGenerations;
        this.mutatorStrategy = mutatorStrategy;
        this.selectionStrategy = selectionStrategy;
        this.correctionStrategyList = correctionStrategyList;
        this.crossoverStrategy = crossoverStrategy;
        logger.setLevel(Level.SEVERE);
    }

    @Override
    public EvolutionResults crossoverPopulation(ArrayList<Chromosome> chromosomeList) {

        //Setup Phase
        ArrayList<Chromosome> newPopulation;
        //All results are saved here
        EvolutionResults evolutionResults = new EvolutionResults();

        double iteration = numberOfGenerations * 0.01; //every 1%
        int percentageDone = 0;

        //Correct Maps
        if(correctionStrategyList != null) {
            for (Chromosome chromosome : chromosomeList) {
                for (CorrectionStrategy correctionStrategy : correctionStrategyList) {
                    correctionStrategy.correctMap(chromosome);
                }
            }
        }

        //Run Project
        for (int generation = 0; generation < numberOfGenerations; generation++) {

            //Evaluate all dungeon based on all fitness implementations on the list
            for (FitnessStrategy fitnessStrategy : fitnessStrategyList) {
                for (Chromosome chromosome : chromosomeList) {
                    fitnessStrategy.evaluateMap(chromosome);
                }
            }

            selectionStrategy.selectFitIndividuals(chromosomeList, selectionFraction);
            //Save previous Generation
            evolutionResults.addGeneration(Algorithms.deepClone(chromosomeList));
            //TODO WORK HERE, its crossoverStrategy generator
            newPopulation = crossoverStrategy.createNewGeneration(chromosomeList, populationSize, selectionFraction);

            mutatorStrategy.mutateDungeons(newPopulation);//TODO it might not mutate sort makes no sense as i didnt evaluate it again
            chromosomeList = newPopulation;//TODO it might make it work or not
//            newPopulation.sort(Comparator.comparing(Chromosome::getFitnessScore).reversed());

            if(generation % iteration == 0) {
                //TODO while this isnt really necessary, it is nice for debugging
                logger.log(Level.INFO, generation + "th generation\nTop Chromosome Score: "
                        + chromosomeList.get(0).getFitnessScore() + "\nTop Chromosome Number of Rooms: "
                        + chromosomeList.get(0).getNumberOfRooms() + "\n"
                        + percentageDone + "%");
                percentageDone++;
            }
        }
        return evolutionResults;
    }
}