package Genetic_Algorithm.ChromosomeEvaluation;

import Algorithms.Algorithms;
import Chromosome.Chromosome;
import Genetic_Algorithm.MutatorStrategy.MutatorStrategy;
import Genetic_Algorithm.OffspringStrategy.OffspringStrategy;
import Genetic_Algorithm.PermutationStrategy.PermutationStrategy;
import Genetic_Algorithm.SelectionStrategy.SelectionStrategy;
import Exceptions.VariableBoundsException;
import Genetic_Algorithm.Data.EvolutionResults;
import Genetic_Algorithm.FitnessStrategy.FitnessStrategy;
import Genetic_Algorithm.CorrectionStrategy.CorrectionStrategy;

import java.util.ArrayList;
import java.util.logging.Level;

public class BasicChromosomeEvaluation extends AbstractChromosomeEvaluation {

    //TODO need to work on top pop and pop size, they are slighlty silly
    private double populationSize;
    private int numberOfGenerations;
    private double selectionFraction;


    //TODO these are all strategies, as I said, I want them in decorators mostly
    private ArrayList<FitnessStrategy> fitnessStrategyList;
    private MutatorStrategy mutation;
    private SelectionStrategy selectionStrategy;
    private PermutationStrategy permutationStrategy;
    private CorrectionStrategy correctionStrategy;
    private OffspringStrategy offspringStrategy;

    //TODO make basic chromosome do excatly that, just basic, maybe only use fitness, add decorators to
    // add more features if possible (it should be, it all in steps)


    //TODO since I can order decorators in whatever order I want, it should make it even more exciting!
    public BasicChromosomeEvaluation(double populationSize, int numberOfGenerations, double selectionFraction,
                                     ArrayList<FitnessStrategy> fitnessStrategyList,
                                     MutatorStrategy mutation, SelectionStrategy selectionStrategy, PermutationStrategy permutationStrategy,
                                     CorrectionStrategy correctionStrategy, OffspringStrategy offspringStrategy){

        if(selectionFraction <= 0 || selectionFraction >= 1)throw new VariableBoundsException(0, 1);
        this.selectionFraction = selectionFraction;

        if(populationSize > 1000) System.err.println("NoiseStrategy size is beyond 1000, program might take a long time and" +
                "results probably won't be better");
//        if(topPopulation != 0.1) System.err.println("Default crossover behaviour recommends 0.1 (10%) of the best " +
//                "population to mate");

        this.populationSize = populationSize;
//        TOP_POP = topPopulation * populationSize;

        //If there are less maps than to make even 1, then we have to force it
//        if(TOP_POP < 1)TOP_POP = 1;//todo consider 2

        //TODO consctreuctor now can determinate whenever yoou can use the method or not (it can have methods to complete what's missing)
        this.fitnessStrategyList = fitnessStrategyList;
        this.numberOfGenerations = numberOfGenerations;
        this.mutation = mutation;
        this.selectionStrategy = selectionStrategy;
        this.permutationStrategy = permutationStrategy;
        this.correctionStrategy = correctionStrategy;
        this.offspringStrategy = offspringStrategy;


        //todo this sets logger to be turned off, used in decorator, maybe overkill? but then i can attach however i fancy
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


        //Run Project
        for (int generation = 0; generation < numberOfGenerations; generation++) {


            //Evaluate all dungeon based on all fitness implementations on the list
            for (FitnessStrategy fitnessStrategy : fitnessStrategyList) {
                for (Chromosome chromosome : chromosomeList) fitnessStrategy.evaluateMap(chromosome);
            }

            //Correct Maps
            //TODO make better error checking in interpeter
            if(correctionStrategy == null) {

//                System.out.println("todo, not found correctionStrategy strategy, this is fine for debug");
            }
            else{
                for (Chromosome chromosome : chromosomeList) correctionStrategy.correctMap(chromosome);
            }

            //SelectionStrategy
            //todo i should not do so I write code like
            // map = selectionStrategy.doSomething(map);
            // it should be
            // selectionStrategy.doSomething(map)
            // and make sure it does update accordingly

            /*chromosomeList = */
            selectionStrategy.selectFitIndividuals(chromosomeList, selectionFraction);



            //Save previous Generation
            evolutionResults.addGeneration(Algorithms.deepClone(chromosomeList));






            //TODO WORK HERE, its offspringStrategy generator
            newPopulation = offspringStrategy.createNewGeneration(chromosomeList, populationSize, selectionFraction);


            mutation.mutateDungeons(newPopulation);//TODO it might not mutate sort makes no sense as i didnt evaluate it again

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

    private void createOffspring(){


    }
}