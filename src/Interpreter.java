import Algorithms.*;
import Algorithms.CA.*;
import Map.Map;
import Genetic_Algorithm.ChromosomeEvaluation.AbstractChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.BasicChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.MeasureTimeChromosomeEvaluation;
import Genetic_Algorithm.Data.EvolutionResults;
import Genetic_Algorithm.Fitness.FitnessEnum;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.ManualCorrections.CorrectionEnum;
import Genetic_Algorithm.Mutation.MutationsEnum;
import Genetic_Algorithm.Offspring.OffspringEnum;
import Genetic_Algorithm.Population.NoiseEnum;
import Genetic_Algorithm.Population.NoiseImp;
import Genetic_Algorithm.Premutation.PremutationEnum;
import Genetic_Algorithm.Selection.SelectionEnum;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Basic Interpreter is used for demonstrations of the entire project
 * it does read arguments and does something with them
 */
public class Interpreter {

    //TODO get rid of minus symbol later
    private final String CREATE_I = "c";
    private final String HELP_I = "h";
    private final String VERBOSE_I = "v";

    private final String[] commandList = new String[]{
            CREATE_I, HELP_I, VERBOSE_I};

    //Some data to display for user
    private final String OPTION = "\nUsage: GMaps [options]" +
            "\nOptions: " +
            "\n-" + CREATE_I + "\t\tCreate map evaluation" +
            "\n-" + VERBOSE_I + "\t\tVerbose output ()" +
            "\n-" + HELP_I + "\t\tDisplay this list";


    private final String README = "\nExamples:" +
            "\n-c 10 20 15 10\t\tDuring 20 generations, create 10 maps of size 15 by 10 each" +
            "\n-c 1 10 150 100\t\tDuring 10 generations, create 1 map of size 150 by 100 each";


    //A whole generation of maps
    private ArrayList<Map> generationOfMaps;

    //Our Fitness implementations, we need at least one way
    // to evaluate a map
    private ArrayList<FitnessImp> fitnessImpList;

    //Noise Implementation, needed at the start
    // if maps start random
    private NoiseImp noiseImp;

    //Cellular Automate is an outside factor to
    // scramble a map to look like a "cave"
    // used in Evolving Cellular Automate (ECA)
    private CellularAutomateImp cellularAutomateImp;

    //Chromosome evaluation takes all elements of fitness, etc TODO ELABORATE
    // and scores todo i might describe it wrong
    private AbstractChromosomeEvaluation chromosomeEvaluationImp;

    //Results after running program
    //It holds all data made during the process
    // (All individuals and all generations)
    private EvolutionResults evolutionResults;

    //Used when parsing arguments parsing
    private int populationSize;
    private int numberOfGenerations;
    private int dungeonWidth;
    private int dungeonHeight;

    //TODO I might want to add flags
    private boolean verbose = false;
    /**
     * Constructor
     * Takes all string arguments meant to describe
     * how to generate maps and interprets them
     */
    Interpreter(String... args){
        interpretArguments(args);
    }


    //TODO look into the order and make sure it looks nice
    private void interpretArguments(String... args){
        if(args.length <= 0){
            displayHelp();
            System.exit(1);
        }

        populationSize = Integer.parseInt(args[1]);
        numberOfGenerations = Integer.parseInt(args[2]);
        dungeonWidth = Integer.parseInt(args[3]);
        dungeonHeight = Integer.parseInt(args[4]);

        fitnessImpList = new ArrayList<>();
        generationOfMaps = new ArrayList<>();

        //Measure time
        float timeNow = System.nanoTime();

        //Add Fitness to utilize
        addFitnessStrategy("find_all_rooms");
        //Add noise to empty maps
        addNoiseStrategy("fill");
        //Add Cellurar Automata rule to modify map a bit
        addCellularAutomataStrategy("rule20");
        noiseMaps();
        caMaps();

        //Add Evaluation Strategy, for now there is only basic
        addChromosomeEvaluationStrategy("basic");

        evaluateMaps();
        evolutionResults.saveResults();
        System.out.println("Finished after: " + ((System.nanoTime() - timeNow) / 1000000000) + " seconds");

        try {
            Algorithms.writeToFile("BEST", evolutionResults.findBest());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean addChromosomeEvaluationStrategy(String option){
        String choice = option.toLowerCase().trim();

        switch(choice){
            case "basic":
                chromosomeEvaluationImp = new MeasureTimeChromosomeEvaluation(
                        new BasicChromosomeEvaluation(0.1, populationSize, numberOfGenerations, generationOfMaps, fitnessImpList,
                                MutationsEnum.DEFAULT, SelectionEnum.Tournament, PremutationEnum.SWAP,
                CorrectionEnum.FIND_ROOM, OffspringEnum.DEFAULT));
                return true;
            default:
                return false;
        }
    }


    private boolean addNoiseStrategy(String option){
        String choice  = option.toLowerCase().trim();

        switch(choice) {
            case "noise":
                noiseImp = NoiseEnum.NOISE;
                return true;
            case "fill":
                noiseImp = NoiseEnum.FILL;
                return true;
            default:
                return false;
        }
    }

    private boolean addFitnessStrategy(String option){
        String choice  = option.toLowerCase().trim();

        switch(choice) {
            case "find_all_rooms":
                fitnessImpList.add(FitnessEnum.FIND_ALL_ROOMS);
                return true;
            default:
                return false;
        }
    }

    private boolean addCellularAutomataStrategy(String option){
        String choice  = option.toLowerCase().trim();

        switch(choice) {
            case "rule20":
                cellularAutomateImp = new Rule20CellularAutomate();
                return true;
            default:
                return false;
        }
    }

    private boolean noiseMaps(){
        //Create Noise for maps
        generationOfMaps = noiseImp.createNoise(dungeonWidth, dungeonHeight, populationSize, 0.6);
        return true;
    }

    private boolean caMaps(){
        //Run Cellular Automate
        for (int i = 0; i < generationOfMaps.size(); i++) {
            Matrix k = cellularAutomateImp.generateMap(generationOfMaps.get(i).getMapMatrix());
            Map kk = new Map(k);
            generationOfMaps.set(i, kk);
        }
        return true;
    }

    private boolean evaluateMaps(){
        evolutionResults = chromosomeEvaluationImp.crossoverPopulation();
        return true;
    }

    private void displayHelp(){
        System.out.println(OPTION);
        System.out.println(README);
    }
}
