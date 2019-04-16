import Algorithms.*;
import Algorithms.CA.*;
import Genetic_Algorithm.ManualCorrections.CorrectionImp;
import Genetic_Algorithm.Mutation.MutatorImp;
import Genetic_Algorithm.Offspring.OffspringImp;
import Genetic_Algorithm.Premutation.PremutationImp;
import Genetic_Algorithm.Selection.SelectionImp;
import Map.Map;
import Genetic_Algorithm.ChromosomeEvaluation.AbstractChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.BasicChromosomeEvaluation;
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Basic Interpreter is used for demonstrations of the entire project
 * it does read arguments and does something with them
 */
public class Interpreter {

    //TODO I will use scanner to read all functions
    private Scanner scanner;

    private final String CREATE_I = "-c";
    private final String LOAD_I = "-l";
    private final String HELP_I = "-h";
    private final String VERBOSE_I = "-v";
    private final String FITNESS_I = "-f";
    private final String MUTATOR_I = "-m";
    private final String CA_I = "-a";
    private final String PREMUTATOR_I = "-p";
    private final String CHROMOSOME_EVALUATION_I = "-e";
    private final String CORRECTION_I = "-r";
    private final String NOISE_I = "-n";
    private final String SELECTION_I = "-s";
    private final String OFFSPRING_I = "-o";

    private final String[] commandList = new String[]{
            CREATE_I, LOAD_I, HELP_I, VERBOSE_I, FITNESS_I, MUTATOR_I,
            CA_I, PREMUTATOR_I, CHROMOSOME_EVALUATION_I, CORRECTION_I,
            NOISE_I, SELECTION_I};

    //Some data to display for user
    private final String OPTION = "\nUsage: GMaps [ARGUMENT] [GENERATIONS] [NO.MAPS] [WIDTH] [HEIGHT]" +
            "\n\nARGUMENT: " +//todo is argument a good word?
            "\n" + CREATE_I + " [OPTIONS]... \t\tCreate map evaluation" +
            "\n" + LOAD_I + " [SOURCE] [OPTIONS]... \t\tLoad premade maps evaluation" +
            "\n" + HELP_I + "\t\t\t\t\tDisplay this list" +
            "\n\nOPTIONS: " +
            "\n" + FITNESS_I + " [NAME]\t\t\tAdd [FITNESS] Function" +
            "\n" + SELECTION_I + " [NAME]\t\t\tAdd [SELECTION] Function" +
            "\n" + MUTATOR_I + " [NAME]\t\t\tAdd [MUTATOR] Function" +
            "\n" + CHROMOSOME_EVALUATION_I + " [NAME]\t\t\tAdd [CHROMOSOME EVALUATION] Function" +
            "\n" + NOISE_I + " [NAME]\t\t\tAdd [NOISE] Function" +
            "\n" + CA_I + " [NAME]\t\t\tAdd [CELLULAR AUTOMATE] Function" +
            "\n" + PREMUTATOR_I + " [NAME]\t\t\tAdd [PREMUTATION] Function" +
            "\n" + CORRECTION_I + " [NAME]\t\t\tAdd [CORRECTION] Function" +
            "\n" + OFFSPRING_I + " [NAME]\t\t\tAdd [OFFSPRING] Function" +
            "\n" + VERBOSE_I + "\t\t\t\t\tVerbose output" +
            "\n\n\n Genetic Algorithm to work at minimum needs: [FITNESS]... [SELECTION] [OFFSPRING] [MUTATOR] [CHROMOSOME EVALUATION]" +
            "\n If you are creating maps, you need to add [NOISE]" +
            "\n If you are loading maps, [SOURCE] needs to specify a directory with them" +
            "\n Optional Genetic Options: [CELLULAR AUTOMATE] [PREMUTATION] [CORRECTION]";

    private final String README = "\nExamples:" +
            "\n-c -f=find_all_rooms -s=elite -e=find_all_rooms  10 20 15 10\t\tDuring 20 generations, create 10 maps of size 15 by 10 each" +
            "\n-l /home/user/folder/ 1 10 150 100\t\tDuring 10 generations, create 1 map of size 150 by 100 each";

    private String AVAILABLE_OPTIONS = "";

    //TODO this should have a list of all stategy pattern functions
    // that I have written (all mutators, all noise generators
    private final String ALL_FEATURES = "";

    //A whole generation of maps
    private ArrayList<Map> generationOfMaps;

    //Our Fitness implementations, we need at least one way
    // to evaluate a map
    private ArrayList<FitnessImp> fitnessList;

    private SelectionImp selection;
    private MutatorImp mutator;
    //Chromosome evaluation takes all elements of fitness, etc TODO ELABORATE
    // and scores todo i might describe it wrong
    private AbstractChromosomeEvaluation chromosomeEvaluation;
    //Noise Implementation, needed at the start
    // if maps start random
    private NoiseImp noise;
    //Cellular Automate is an outside factor to
    // scramble a map to look like a "cave"
    // used in Evolving Cellular Automate (ECA)
    private CellularAutomateImp cellularAutomateImp;
    //TODO premutations aren't really useful
    private PremutationImp premutation;
    //TODO corrections aren't useful FOR NOW
    // I am trying to force looking for shapes and
    // patterns
    private CorrectionImp correction;
    private OffspringImp offspring;

    //Results after running program
    //It holds all data made during the process
    // (All individuals and all generations)
    private EvolutionResults evolutionResults;

    //Used when parsing arguments
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
    Interpreter(String... args) {
        fitnessList = new ArrayList<>();
        generationOfMaps = new ArrayList<>();
        interpretArguments(args);
    }


    //TODO look into the order and make sure it looks nice
    private void interpretArguments(String... args) {
        if (args.length <= 0) {
            displayHelp();
            System.exit(1);
        }

//        for (int i = 0; i < args.length; i++) {
        switch (args[0]) {
            case CREATE_I:
                //todo this minus for is for last 4 arguments always beign
                // numbers
                for (int i = 1; i < args.length - 4; i++) {
                    findOptions(i, args);
                }
                break;
            case LOAD_I:
                System.out.println("Finish this path");
                break;
            case HELP_I:
                displayHelp();
                System.exit(0);
                break;
        }

        //TODO need better checking
        try {
            populationSize = Integer.parseInt(args[args.length - 4]);
            numberOfGenerations = Integer.parseInt(args[args.length - 3]);
            dungeonWidth = Integer.parseInt(args[args.length - 2]);
            dungeonHeight = Integer.parseInt(args[args.length- 1]);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing Number");
            System.exit(1);
        }

        //Preparation, either create maps from noise or load some
        if(args[0].equals(CREATE_I)) {
            if (noise == null)System.exit(1);//todo change to throw exception
            else{
                noiseMaps();
            }
        }else if(args[0].equals(LOAD_I))System.exit(1);//todo finish this path

        if(cellularAutomateImp != null)caMaps();


//        //Add Fitness to utilize
//        addFitnessStrategy("find_all_rooms");
//        //Add noise to empty maps
//        addNoiseStrategy("fill");
//        //Add Cellurar Automata rule to modify map a bit
//        addCellularAutomataStrategy("rule20");
//        noiseMaps();
//        caMaps();
//
//        //Add Evaluation Strategy, for now there is only basic
//        addChromosomeEvaluationStrategy("basic");

        chromosomeEvaluation = new BasicChromosomeEvaluation(0.1, populationSize, numberOfGenerations,
                fitnessList, mutator, selection, premutation, correction, offspring);


        evolutionResults = chromosomeEvaluation.crossoverPopulation(generationOfMaps);
        evolutionResults.saveAllResults();

        try {
            Algorithms.writeToFile("BEST", evolutionResults.findBest());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findOptions(int i, String... args) {
        switch (args[i]) {
            case FITNESS_I:
                addFitnessStrategy(args[i + 1]);
                break;
            case VERBOSE_I:
                System.out.println("Finish this path");
                System.exit(1);
                //TODO set flag or run logger
                break;
            case MUTATOR_I:
                addMutatorStrategy(args[i + 1]);
                break;
            case CA_I:
                addCellularAutomataStrategy(args[i + 1]);
                break;
            case PREMUTATOR_I:
                addPremutationStrategy(args[i + 1]);
                break;
            case CHROMOSOME_EVALUATION_I:
                addChromosomeEvaluationStrategy(args[i + 1]);
                break;
            case CORRECTION_I:
                addCorrectionStrategy(args[i + 1]);
                break;
            case NOISE_I:
                addNoiseStrategy(args[i + 1]);
                break;
            case SELECTION_I:
                addSelectionStrategy(args[i + 1]);
                break;
            case OFFSPRING_I:
                addOffspringStrategy(args[i + 1]);
            break;
            default:
                break;
        }
    }


    private boolean addChromosomeEvaluationStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case "basic":
                chromosomeEvaluation = new BasicChromosomeEvaluation(0.1, populationSize,
                        numberOfGenerations, fitnessList, mutator, selection, premutation,
                        correction, offspring);
                return true;
            default:
                return false;
        }
    }


    private boolean addNoiseStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case "noise":
                noise = NoiseEnum.NOISE;
                return true;
            case "fill":
                noise = NoiseEnum.FILL;
                return true;
            default:
                return false;
        }
    }

    private boolean addFitnessStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case "find_all_rooms":
                fitnessList.add(FitnessEnum.FIND_ALL_ROOMS);
                return true;
            case "is_traversable":
                fitnessList.add(FitnessEnum.IS_TRAVERSABLE);
            default:
                return false;
        }
    }

    private boolean addCellularAutomataStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case "rule20":
                cellularAutomateImp = new Rule20CellularAutomate();
                return true;
            default:
                return false;
        }
    }

    //TODO there could be a list of premutations, they are also kinda meh anyway
    private boolean addPremutationStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case "swap":
                premutation = PremutationEnum.SWAP;
                return true;
            default:
                return false;
        }
    }

    //TODO this might be a list
    private boolean addCorrectionStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case "find_room":
                correction = CorrectionEnum.FIND_ROOM;
                return true;
            case "find_holes":
                correction = CorrectionEnum.FIND_HOLES;
                return true;
            default:
                return false;
        }
    }

    private boolean addSelectionStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case "elite":
                selection = SelectionEnum.ELITE;
                return true;
            case "tournament":
                selection = SelectionEnum.TOURNAMENT;
                return true;
            default:
                return false;
        }
    }

    private boolean addOffspringStrategy(String option) {
        String choice = option.toLowerCase().trim();

        if(choice.equals(OffspringEnum.DEFAULT.getImplementationName())){
            offspring = OffspringEnum.DEFAULT;
            return true;
        }else if(choice.equals(OffspringEnum.DASD.getImplementationName())){
            offspring = OffspringEnum.DEFAULT;
            return true;
        }else{
            return false;
        }
    }

    private boolean addMutatorStrategy(String option) {
        String choice = option.toLowerCase().trim();

        if(choice.equals(MutationsEnum.DEFAULT.getImplementationName())){
            mutator = MutationsEnum.DEFAULT;
            return true;
        }else if(choice.equals(MutationsEnum.LOW.getImplementationName())){
            mutator = MutationsEnum.LOW;
            return true;
        }else if(choice.equals(MutationsEnum.LOWER.getImplementationName())){
            mutator = MutationsEnum.LOWER;
            return true;
        }else if(choice.equals(MutationsEnum.LOWEST.getImplementationName())){
            mutator = MutationsEnum.LOWEST;
            return true;
        }else{
            return false;
        }
    }

    private boolean noiseMaps() {
        //Create Noise for maps
        generationOfMaps = noise.createNoise(dungeonWidth, dungeonHeight, populationSize, 0.55);
        return true;
    }

    private boolean caMaps() {
        //Run Cellular Automate
        for (int i = 0; i < generationOfMaps.size(); i++) {
            Matrix k = cellularAutomateImp.generateMap(generationOfMaps.get(i).getMapMatrix());
            Map kk = new Map(k);
            generationOfMaps.set(i, kk);
        }
        return true;
    }

    private void displayHelp() {
        AVAILABLE_OPTIONS =
                "\nAvailable [OPTIONS]:\n" +
                        "\nFitness:" +
        "\n\t" + FitnessEnum.FIND_ALL_ROOMS.getImplementationName() +
        "\n\t" + FitnessEnum.IS_TRAVERSABLE.getImplementationName() +

                        "\nCorrections:" +
       "\n\t" + CorrectionEnum.FIND_ROOM.getImplementationName() +
       "\n\t" + CorrectionEnum.FIND_HOLES.getImplementationName() +

                        "\nMutations:" +
       "\n\t" + MutationsEnum.DEFAULT.getImplementationName() +
       "\n\t" + MutationsEnum.LOW.getImplementationName() +
       "\n\t" + MutationsEnum.LOWER.getImplementationName() +
       "\n\t" + MutationsEnum.LOWEST.getImplementationName() +

                        "\nPopulation Noise:" +
       "\n\t" + NoiseEnum.FILL.getImplementationName() +
       "\n\t" + NoiseEnum.NOISE.getImplementationName() +

                        "\nPremutation:" +
       "\n\t" + PremutationEnum.SWAP.getImplementationName() +
       "\n\t" + PremutationEnum.INVERSION.getImplementationName() +
       "\n\t" + PremutationEnum.SCRAMBLE.getImplementationName() +

                        "\nSelection Method:" +
       "\n\t" + SelectionEnum.ELITE.getImplementationName() +
       "\n\t" + SelectionEnum.TOURNAMENT.getImplementationName() +
       "\n\t" + SelectionEnum.ROULETTE.getImplementationName() +
       "\n\t" + SelectionEnum.RANK.getImplementationName() +
       "\n\t" + SelectionEnum.StochasticTwo.getImplementationName();

       System.out.println(OPTION);
       System.out.println(ALL_FEATURES);
       System.out.println(README);
       System.out.println(AVAILABLE_OPTIONS);
    }
}