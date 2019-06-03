import Algorithms.*;
import Algorithms.CA.*;
import Genetic_Algorithm.ChromosomeEvaluation.AttachLogChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.MeasureTimeChromosomeEvaluation;
import Genetic_Algorithm.ManualCorrections.CorrectionImp;
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
import Genetic_Algorithm.ManualCorrections.CorrectionEnum;
import Genetic_Algorithm.Mutator.MutatorEnum;
import Genetic_Algorithm.Offspring.OffspringEnum;
import Genetic_Algorithm.Population.PopulationEnum;
import Genetic_Algorithm.Population.PopulationImp;
import Genetic_Algorithm.Premutation.PremutationEnum;
import Genetic_Algorithm.Selection.SelectionEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Basic Interpreter is used for demonstrations of the entire project
 * it does read arguments and does something with them
 */
public class Interpreter {

    //TODO I will use scanner to read all functions
    // maybe not
    private Scanner scanner;

    private final ArrayList<String> CREATE_L = new ArrayList(Arrays.asList("-c", "--create"));
    private final ArrayList<String> LOAD_L = new ArrayList();
    private final ArrayList<String> HELP_L = new ArrayList();
    private final ArrayList<String> VERBOSE_L = new ArrayList();
    private final ArrayList<String> FITNESS_L = new ArrayList();
    private final ArrayList<String> MUTATOR_L = new ArrayList();
    private final ArrayList<String> CA_L = new ArrayList();
    private final ArrayList<String> PREMUTATION_L = new ArrayList();
    private final ArrayList<String> CHROMOSOME_EVALUATION_L = new ArrayList();
    private final ArrayList<String> CORRECTION_L = new ArrayList();
    private final ArrayList<String> NOISE_L = new ArrayList();
    private final ArrayList<String> SELECTION_L = new ArrayList();
    private final ArrayList<String> OFFSPRING_L = new ArrayList();

    //Some data to display for user
    private String OPTION;

    private final String README = "\nExamples:" +
            "\njava GMaps --create --noise noise --fitness find_all_rooms --selection tournament --offspring default --mutator" +
            " default --cellular rule20 --premutation swap -r find_room 100 3000 30 25" +
            "\n-l /home/user/folder/ 1 10 150 100";

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

                        "\nPopulation Noise:" +
       "\n\t" + PopulationEnum.FILL.getImplementationName() +
       "\n\t" + PopulationEnum.NOISE.getImplementationName() +

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
    private PopulationImp noise;
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
    /**
     * Flags
     * */
    private boolean verbose = false;

    /**
     * Constructor
     * Takes all string arguments meant to describe
     * how to generate maps and interprets them
     */
    Interpreter(String... args) {
        fitnessList = new ArrayList<>();
        generationOfMaps = new ArrayList<>();
    	// CREATE_L.add("-c");
	    // CREATE_L.add("--create");
        HELP_L.add("-h");
	    HELP_L.add("--help");
	    LOAD_L.add("-l");
	    LOAD_L.add("--load");
        VERBOSE_L.add("-v");
        VERBOSE_L.add("--verbose");
        FITNESS_L.add("-f");
        FITNESS_L.add("--fitness");
        MUTATOR_L.add("-m");
        MUTATOR_L.add("--mutator");
        CA_L.add("-a");
        CA_L.add("--cellular");
        PREMUTATION_L.add("-p");
        PREMUTATION_L.add("--premutation");
        CHROMOSOME_EVALUATION_L.add("-e");
        CHROMOSOME_EVALUATION_L.add("--evaluation");
        CORRECTION_L.add("-r");
        CORRECTION_L.add("--correction");
        NOISE_L.add("-n");
        NOISE_L.add("--noise");
        SELECTION_L.add("-s");
        SELECTION_L.add("--selection");
        OFFSPRING_L.add("-o");
        OFFSPRING_L.add("--offspring");

        OPTION = "\nUsage: GMaps [ARGUMENT] [GENERATIONS] [NO.MAPS] [WIDTH] [HEIGHT]" +
            "\n\n[ARGUMENT]: " +
            "\n" + CREATE_L + "\t[OPTIONS]... \t\tCreate map evaluation" +
            "\n" + LOAD_L + "\t[SOURCE] [OPTIONS]... \t\tLoad premade maps evaluation" +
            "\n" + HELP_L + "\t \t\t\t\tDisplay this list" +
            "\n\n[OPTION]: " +
            "\n" + FITNESS_L + "\t[NAME]\t\t\tAdd [FITNESS] Function" +
            "\n" + SELECTION_L + "\t[NAME]\t\t\tAdd [SELECTION] Function" +
            "\n" + MUTATOR_L + "\t[NAME]\t\t\tAdd [MUTATOR] Function" +
            "\n" + CHROMOSOME_EVALUATION_L + "\t[NAME]\t\t\tAdd [CHROMOSOME EVALUATION] Function" +
            "\n" + NOISE_L + "\t[NAME]\t\t\tAdd [NOISE] Function" +
            "\n" + CA_L + "\t[NAME]\t\t\tAdd [CELLULAR AUTOMATE] Function" +
            "\n" + PREMUTATION_L + "\t[NAME]\t\t\tAdd [PREMUTATION] Function" +
            "\n" + CORRECTION_L + "\t[NAME]\t\t\tAdd [CORRECTION] Function" +
            "\n" + OFFSPRING_L + "\t[NAME]\t\t\tAdd [OFFSPRING] Function" +
            "\n" + VERBOSE_L + "\t\t\t\t\t\tVerbose output" +
            "\n\n\n Genetic Algorithm to work at minimum needs: [FITNESS]... [SELECTION] [OFFSPRING] [MUTATOR] [CHROMOSOME EVALUATION]" +
            "\n If you are creating maps, you need to add [NOISE]" +
            "\n If you are loading maps, [SOURCE] needs to specify a directory with them" +
            "\n Optional Genetic Options: [CELLULAR AUTOMATE] [PREMUTATION] [CORRECTION]";
        interpretArguments(args);

    }


    //TODO look into the order and make sure it looks nice
    private void interpretArguments(String... args) {
        if (args.length <= 0) {
            displayHelp();
            System.exit(0);
        }
        if(CREATE_L.contains(args[0])){
            //todo this minus for is for last 4 arguments always beign
            // numbers
            for (int i = 1; i < args.length - 4; i++) {
                findOptions(i, args);
            }
        }
        else if(LOAD_L.contains(args[0])) {
            System.out.println("Finish developing loading maps");
        }
        else if(HELP_L.contains(args[0])){
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
            System.err.println("Error parsing Number");
            System.exit(1);
        }

        //Preparation, either create maps from noise or load some
        if(CREATE_L.contains(args[0])) {
            if (noise == null){
                throw new RuntimeException("todo error");
                //System.exit(1);//todo change to throw exception
            }
            else{
                noiseMaps();
            }
        }else if(args[0].equals(LOAD_L.get(0)))System.exit(1);//todo finish this path

        if(cellularAutomateImp != null)caMaps();

        //TODO first Idont add chromosome to list, second i need error checking if bbasic parts are missing
        //TODO so this is the only place I p
        chromosomeEvaluation = new BasicChromosomeEvaluation( populationSize, numberOfGenerations, 0.2,
                fitnessList, mutator, selection, premutation, correction, offspring);

        chromosomeEvaluation = new AttachLogChromosomeEvaluation(chromosomeEvaluation);
        chromosomeEvaluation = new MeasureTimeChromosomeEvaluation(chromosomeEvaluation);

        evolutionResults = chromosomeEvaluation.crossoverPopulation(generationOfMaps);
        evolutionResults.saveAllResults();

        try {
            Algorithms.writeToFile("BEST", evolutionResults.findBest());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findOptions(int i, String... args) {

        if(FITNESS_L.contains(args[i])){
            addFitnessStrategy(args[i + 1]);
        }
        else if(VERBOSE_L.contains(args[i])){
            System.out.println("Finish this path");
            verbose = true;
            System.exit(1);
        }
        else if(MUTATOR_L.contains(args[i])){
            addMutatorStrategy(args[i + 1]);
        }
        else if(CA_L.contains(args[i])){
            addCellularAutomataStrategy(args[i + 1]);
        }
        else if(PREMUTATION_L.contains(args[i])){
            addPremutationStrategy(args[i + 1]);
        }
        else if(CHROMOSOME_EVALUATION_L.contains(args[i])){
            addChromosomeEvaluationStrategy(args[i + 1]);
        }
        else if(CORRECTION_L.contains(args[i])){
            addCorrectionStrategy(args[i + 1]);
        }
        else if(NOISE_L.contains(args[i])){
            addNoiseStrategy(args[i + 1]);
        }
        else if(SELECTION_L.contains(args[i])){
            addSelectionStrategy(args[i + 1]);
        }
        else if(OFFSPRING_L.contains(args[i])){
            addOffspringStrategy(args[i + 1]);
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
                noise = PopulationEnum.NOISE;
                return true;
            case "fill":
                noise = PopulationEnum.FILL;
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

       System.out.println(OPTION);
       System.out.println(ALL_FEATURES);
       System.out.println(README);
       System.out.println(AVAILABLE_OPTIONS);
    }
}
