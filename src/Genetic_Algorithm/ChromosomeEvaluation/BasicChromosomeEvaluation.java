package Genetic_Algorithm.ChromosomeEvaluation;

import Algorithms.Algorithms;
import Map.Map;
import Exceptions.VariableBoundsException;
import Genetic_Algorithm.Data.EvolutionResults;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.Corrections.CorrectionImp;
import Genetic_Algorithm.Mutator.MutatorImp;
import Genetic_Algorithm.Offspring.OffspringImp;
import Genetic_Algorithm.Premutation.PremutationImp;
import Genetic_Algorithm.Selection.SelectionImp;

import java.util.ArrayList;
import java.util.logging.Level;

public class BasicChromosomeEvaluation extends AbstractChromosomeEvaluation {

    //TODO need to work on top pop and pop size, they are slighlty silly
    private double populationSize;
    private int numberOfGenerations;
    private double selectionFraction;


    //TODO these are all strategies, as I said, I want them in decorators mostly
    private ArrayList<FitnessImp> fitnessImpList;
    private MutatorImp mutation;
    private SelectionImp selection;
    private PremutationImp premutation;
    private CorrectionImp correction;
    private OffspringImp offspring;

    //TODO make basic chromosome do excatly that, just basic, maybe only use fitness, add decorators to
    // add more features if possible (it should be, it all in steps)


    //TODO since I can order decorators in whatever order I want, it should make it even more exciting!
    public BasicChromosomeEvaluation(double populationSize, int numberOfGenerations, double selectionFraction,
                                     ArrayList<FitnessImp> fitnessImpList,
                                     MutatorImp mutation, SelectionImp selection, PremutationImp premutation,
                                     CorrectionImp correction, OffspringImp offspring){

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
        this.fitnessImpList = fitnessImpList;
        this.numberOfGenerations = numberOfGenerations;
        this.mutation = mutation;
        this.selection = selection;
        this.premutation = premutation;
        this.correction = correction;
        this.offspring = offspring;


        //todo this sets logger to be turned off, used in decorator, maybe overkill? but then i can attach however i fancy
        logger.setLevel(Level.SEVERE);


    }

    @Override
    public EvolutionResults crossoverPopulation(ArrayList<Map> mapList) {

        //Setup Phase
        ArrayList<Map> newPopulation;
        //All results are saved here
        EvolutionResults evolutionResults = new EvolutionResults();

        double iteration = numberOfGenerations * 0.01; //every 1%
        int percentageDone = 0;


        //Run Project
        for (int generation = 0; generation < numberOfGenerations; generation++) {


            //Evaluate all dungeon based on all fitness implementations on the list
            for (FitnessImp fitnessImp : fitnessImpList) {
                for (Map map : mapList) fitnessImp.evaluateMap(map);
            }

            //Correct Maps
            //TODO make better error checking in interpeter
            if(correction == null) {
                System.out.println("todo, not found correction strategy");
            }
            else{
                for (Map map : mapList) correction.correctMap(map);
            }

            //Selection
            //todo i should not do so I write code like
            // map = selection.doSomething(map);
            // it should me
            // selection.doSomething(map)
            // and make sure it does update accordingly
            mapList = selection.selectFitIndividuals(mapList, selectionFraction);



            //Save previous Generation
            evolutionResults.addGeneration(Algorithms.deepClone(mapList));






            //TODO WORK HERE, its offspring generator
            newPopulation = offspring.createNewGeneration(mapList, populationSize, selectionFraction);


            mutation.mutateDungeons(newPopulation);//TODO it might not mutate sort makes no sense as i didnt evaluate it again

            mapList = newPopulation;//TODO it might make it work or not
//            newPopulation.sort(Comparator.comparing(Map::getFitnessScore).reversed());



            if(generation % iteration == 0) {
                //TODO while this isnt really necessary, it is nice for debugging
                logger.log(Level.INFO, generation + "th generation\nTop Map Score: "
                        + mapList.get(0).getFitnessScore() + "\nTop Map Number of Rooms: "
                        + mapList.get(0).getNumberOfRooms() + "\n"
                        + percentageDone + "%");
                percentageDone++;
            }
        }

        return evolutionResults;
    }

    private void createOffspring(){


    }
}