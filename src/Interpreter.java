import Algorithms.*;
import Algorithms.CA.*;
import DataStructure.Matrix;
import Genetic_Algorithm.ChromosomeEvaluation.AttachLogChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.MeasureTimeChromosomeEvaluation;
import Genetic_Algorithm.Corrections.CorrectionImp;
import Genetic_Algorithm.Mutator.MutatorImp;
import Genetic_Algorithm.Offspring.OffspringImp;
import Genetic_Algorithm.Premutation.PremutationImp;
import Genetic_Algorithm.Selection.SelectionImp;
import Map.Map;
import Genetic_Algorithm.ChromosomeEvaluation.AbstractChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.BasicChromosomeEvaluation;
import Genetic_Algorithm.Data.EvolutionResults;
import Genetic_Algorithm.Fitness.FitnessEnum;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.Corrections.CorrectionEnum;
import Genetic_Algorithm.Mutator.MutatorEnum;
import Genetic_Algorithm.Offspring.OffspringEnum;
import Genetic_Algorithm.NoiseStrategy.NoiseEnum;
import Genetic_Algorithm.NoiseStrategy.NoiseImp;
import Genetic_Algorithm.Premutation.PremutationEnum;
import Genetic_Algorithm.Selection.SelectionEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Basic Interpreter is used for demonstrations of the entire project
 * it does read arguments and does something with them
 */
public class Interpreter {

    //All arguments that the program takes, usually divided into short one letter flag and
    // verbose alternative
    private final ArrayList<String> CREATE_ARGUMENT = new ArrayList(Arrays.asList("-c", "--create"));
    private final ArrayList<String> LOAD_ARGUMENT = new ArrayList(Arrays.asList("-l", "--load"));
    private final ArrayList<String> HELP_ARGUMENT = new ArrayList(Arrays.asList("-h", "--help"));
    private final ArrayList<String> VERBOSE_ARGUMENT = new ArrayList(Arrays.asList("-v", "--verbose"));
    private final ArrayList<String> FITNESS_ARGUMENT = new ArrayList(Arrays.asList("-f", "--fitness"));
    private final ArrayList<String> MUTATOR_ARGUMENT = new ArrayList(Arrays.asList("-m", "--mutator"));
    private final ArrayList<String> CA_ARGUMENT = new ArrayList(Arrays.asList("-a", "--cellular"));
    private final ArrayList<String> PREMUTATION_ARGUMENT = new ArrayList(Arrays.asList("-p", "--premutation"));
    private final ArrayList<String> CHROMOSOME_EVALUATION_ARGUMENT = new ArrayList(Arrays.asList("-e", "--evaluation"));
    private final ArrayList<String> CORRECTION_ARGUMENT = new ArrayList(Arrays.asList("-r", "--correction"));
    private final ArrayList<String> NOISE_ARGUMENT = new ArrayList(Arrays.asList("-n", "--noise"));
    private final ArrayList<String> SELECTION_ARGUMENT = new ArrayList(Arrays.asList("-s", "--selection"));
    private final ArrayList<String> OFFSPRING_ARGUMENT = new ArrayList(Arrays.asList("-g", "--offspring"));
    private final ArrayList<String> SAVE_LOCATION_ARGUMENT = new ArrayList(Arrays.asList("-o", "--output"));

    //Some data to display for user
    private String HELP_PAGE = "\nUsage: GMaps [ARGUMENT] [GENERATIONS] [NO.MAPS] [WIDTH] [HEIGHT]" +
            "\n\n[ARGUMENT]: " +
            "\n" + CREATE_ARGUMENT + "\t[OPTIONS]... \t\tCreate map evaluation" +
            "\n" + LOAD_ARGUMENT + "\t[SOURCE] [OPTIONS]... \t\tLoad premade maps evaluation" +
            "\n" + HELP_ARGUMENT + "\t \t\t\t\tDisplay this list" +
            "\n\n[HELP_PAGE]: " +
            "\n" + FITNESS_ARGUMENT + "\t[NAME]\t\t\tAdd [FITNESS] Function" +
            "\n" + SELECTION_ARGUMENT + "\t[NAME]\t\t\tAdd [SELECTION] Function" +
            "\n" + MUTATOR_ARGUMENT + "\t[NAME]\t\t\tAdd [MUTATOR] Function" +
            "\n" + CHROMOSOME_EVALUATION_ARGUMENT + "\t[NAME]\t\t\tAdd [CHROMOSOME EVALUATION] Function" +
            "\n" + NOISE_ARGUMENT + "\t[NAME]\t\t\tAdd [NOISE] Function" +
            "\n" + CA_ARGUMENT + "\t[NAME]\t\t\tAdd [CELLULAR AUTOMATE] Function" +
            "\n" + PREMUTATION_ARGUMENT + "\t[NAME]\t\t\tAdd [PREMUTATION] Function" +
            "\n" + CORRECTION_ARGUMENT + "\t[NAME]\t\t\tAdd [CORRECTION] Function" +
            "\n" + OFFSPRING_ARGUMENT + "\t[NAME]\t\t\tAdd [OFFSPRING] Function" +
            "\n" + VERBOSE_ARGUMENT + "\t\t\t\t\t\tVerbose output" +
            "\n\n\n Genetic Algorithm to work at minimum needs: [FITNESS]... [SELECTION] [OFFSPRING] [MUTATOR] [CHROMOSOME EVALUATION]" +
            "\n If you are creating maps, you need to add [NOISE]" +
            "\n If you are loading maps, [SOURCE] needs to specify a directory with them" +
            "\n Optional Genetic Options: [CELLULAR AUTOMATE] [PREMUTATION] [CORRECTION]";


    private final String README = "\nExamples:" +
            "\njava GMaps --create --noise noise --fitness find_all_rooms --selection tournament --offspring default --mutator" +
            " default --cellular rule20 --premutation swap -r find_room 100 3000 30 25" +
            "\njava GMaps --load /home/user/folder/ 1 10 150 100";

    private final String AVAILABLE_OPTIONS =
                "\nAvailable [NAME]:\n" +
                        "\nFitness:" +
        "\n\t" + FitnessEnum.FIND_ALL_ROOMS.getImplementationName() +
        "\n\t" + FitnessEnum.IS_TRAVERSABLE.getImplementationName() +

                        "\nCorrections:" +
       "\n\t" + CorrectionEnum.FIND_ROOM.getImplementationName() +
       "\n\t" + CorrectionEnum.FIND_HOLES.getImplementationName() +

                        "\nMutations:" +
       "\n\t" + MutatorEnum.DEFAULT.getImplementationName() +
       "\n\t" + MutatorEnum.LOW.getImplementationName() +
       "\n\t" + MutatorEnum.LOWER.getImplementationName() +
       "\n\t" + MutatorEnum.LOWEST.getImplementationName() +

                        "\nNoiseStrategy Noise:" +
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
       "\n\t" + SelectionEnum.StochasticTwo.getImplementationName() +

                        "\n Offspring Selection Method:" +
       "\n\t" + OffspringEnum.DEFAULT.getImplementationName()+
       "\n\t" + OffspringEnum.DASD.getImplementationName() +

                        "\n Cellular Automate Method:" +
       "\n\t" + "rule20";


    //A whole generation of maps
    private ArrayList<Map> generationOfMaps;

    //Our Fitness implementations, we need at least one way
    // to evaluate a map
    private ArrayList<FitnessImp> fitnessList;

    private SelectionImp selection;
    private MutatorImp mutator;

    //if specified, saves maps to the output directory
    private String outputDirectory = null;


    //Chromosome collects all strategy elements
    // and evaluates our whole project
    private AbstractChromosomeEvaluation chromosomeEvaluation;

    //Noise Implementation, needed at the start
    // if maps start random
    private NoiseImp noise;
    //Cellular Automate is an outside factor to
    // scramble a map to look like a "cave"
    // used in Evolving Cellular Automate (ECA)
    private CellularAutomateImp cellularAutomateImp;
    private PremutationImp premutation;
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


    /**
     * Flags
     * */

    //Verbose should print logs to the terminal (normal should be files only)
    private boolean verbose = false;

    //If maps aren't too different from each other
    // it means maps aren't really evolving, and we can end sooner
    private boolean endExecutionSooner = false;

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
            System.exit(0);
        }


        if(CREATE_ARGUMENT.contains(args[0])){
            //todo this minus for is for last 4 arguments always beign
            // numbers
            for (int i = 1; i < args.length - 4; i++) {
                findOptions(i, args);
            }
        }
        else if(LOAD_ARGUMENT.contains(args[0])) {
            System.out.println("Finish developing loading maps");
        }
        else if(HELP_ARGUMENT.contains(args[0])){
            displayHelp();
            System.exit(0);
        }


        //TODO need better checking
        try {
            populationSize = Integer.parseInt(args[args.length - 4]);
            numberOfGenerations = Integer.parseInt(args[args.length - 3]);
            dungeonWidth = Integer.parseInt(args[args.length - 2]);
            dungeonHeight = Integer.parseInt(args[args.length- 1]);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing dungeon variables, you need to supply" +
                    "\nPopulation Size" +
                    "\nNumber of Generations" +
                    "\nWidth of Maps" +
                    "\nHeight of Maps");
            System.exit(1);
        }

        //Preparation, either create maps from noise or load some
        if(CREATE_ARGUMENT.contains(args[0])) {
            if (noise == null){
                throw new RuntimeException("todo error");
            }
            else{
                noiseMaps();
            }
        }else if(args[0].equals(LOAD_ARGUMENT.get(0)))System.exit(1);//todo finish this path

        if(cellularAutomateImp != null)caMaps();

        //TODO first Idont add chromosome to list, second i need error checking if bbasic parts are missing
        //TODO so this is the only place I p
        chromosomeEvaluation = new BasicChromosomeEvaluation( populationSize, numberOfGenerations, 0.2,
                fitnessList, mutator, selection, premutation, correction, offspring);

//        chromosomeEvaluation = new AttachLogChromosomeEvaluation(chromosomeEvaluation);
        chromosomeEvaluation = new MeasureTimeChromosomeEvaluation(chromosomeEvaluation);

        evolutionResults = chromosomeEvaluation.crossoverPopulation(generationOfMaps);

        //todo its a little silly
        if(outputDirectory != null)evolutionResults.saveAllResults(outputDirectory);
        else evolutionResults.saveAllResults();

        try {
            Algorithms.writeToFile("BEST", evolutionResults.findBest());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findOptions(int i, String... args) {
        //todo for better argument finding, I could jump x amount of times in array based on
        // how many arguments my function took (and it would return this number I guess)
        if(FITNESS_ARGUMENT.contains(args[i])){
            addFitnessStrategy(args[i + 1]);
        }
        else if(VERBOSE_ARGUMENT.contains(args[i])){
            System.out.println("Finish this path");
            verbose = true;
            System.exit(1);
        }
        else if(MUTATOR_ARGUMENT.contains(args[i])){
            addMutatorStrategy(args[i + 1]);
        }
        else if(CA_ARGUMENT.contains(args[i])){
            addCellularAutomataStrategy(args[i + 1]);
        }
        else if(PREMUTATION_ARGUMENT.contains(args[i])){
            addPremutationStrategy(args[i + 1]);
        }
        else if(CHROMOSOME_EVALUATION_ARGUMENT.contains(args[i])){
            addChromosomeEvaluationStrategy(args[i + 1]);
        }
        else if(CORRECTION_ARGUMENT.contains(args[i])){
            addCorrectionStrategy(args[i + 1]);
        }
        else if(NOISE_ARGUMENT.contains(args[i])){
            addNoiseStrategy(args[i + 1]);
        }
        else if(SELECTION_ARGUMENT.contains(args[i])){
            addSelectionStrategy(args[i + 1]);
        }
        else if(OFFSPRING_ARGUMENT.contains(args[i])){
            addOffspringStrategy(args[i + 1]);
        }
        else if(SAVE_LOCATION_ARGUMENT.contains(args[i])){
            outputDirectory = args[i + 1];
        }
    }

    private boolean addChromosomeEvaluationStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case "basic":
                chromosomeEvaluation = new BasicChromosomeEvaluation(populationSize,
                        numberOfGenerations, 0.3, fitnessList, mutator, selection, premutation,
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

        if(choice.equals(MutatorEnum.DEFAULT.getImplementationName())){
            mutator = MutatorEnum.DEFAULT;
            return true;
        }else if(choice.equals(MutatorEnum.LOW.getImplementationName())){
            mutator = MutatorEnum.LOW;
            return true;
        }else if(choice.equals(MutatorEnum.LOWER.getImplementationName())){
            mutator = MutatorEnum.LOWER;
            return true;
        }else if(choice.equals(MutatorEnum.LOWEST.getImplementationName())){
            mutator = MutatorEnum.LOWEST;
            return true;
        }
        else if(choice.equals(MutatorEnum.HIGH.getImplementationName())){
            mutator = MutatorEnum.HIGH;
            return true;
        }
        else if(choice.equals(MutatorEnum.HIGHEST.getImplementationName())){
            mutator = MutatorEnum.HIGHEST;
            return true;
        }
        else{
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
       System.out.println(HELP_PAGE);
       System.out.println(README);
       System.out.println(AVAILABLE_OPTIONS);
    }
}
