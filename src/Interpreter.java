import Algorithms.*;
import Exceptions.VariableBoundsException;
import Genetic_Algorithm.ChromosomeEvaluation.AttachLogChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.AttachTimeChromosomeEvaluation;
import Genetic_Algorithm.CorrectionStrategy.*;
import Genetic_Algorithm.CrossoverStrategy.UniformCrossoverStrategy;
import Genetic_Algorithm.FitnessStrategy.FitnessStrategy;
import Genetic_Algorithm.MutatorStrategy.*;
import Genetic_Algorithm.NoiseStrategy.FillNoiseStrategy;
import Genetic_Algorithm.NoiseStrategy.RandomNoiseStrategy;
import Genetic_Algorithm.CrossoverStrategy.DefaultCrossoverStrategy;
import Genetic_Algorithm.CrossoverStrategy.CrossoverStrategy;
import Genetic_Algorithm.SelectionStrategy.*;
import Chromosome.*;
import Genetic_Algorithm.ChromosomeEvaluation.AbstractChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.BasicChromosomeEvaluation;
import Genetic_Algorithm.Data.EvolutionResults;
import Genetic_Algorithm.FitnessStrategy.*;
import Genetic_Algorithm.NoiseStrategy.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Basic Interpreter is used for demonstrations of the entire project
 * it does read arguments and does something with them
 */
public class Interpreter {

    /**
     * Constructor
     * Takes all string arguments meant to describe
     * how to generate maps and interprets them
     */
    public Interpreter(String... args) {
        interpretArguments(args);
    }


    private void interpretArguments(String... args) {
        if (args.length <= 0) {
            displayHelp();
            System.exit(0);
        }

        if (CREATE_ARGUMENT.contains(args[0])) {
            for (int i = 1; i < args.length - 4; i++) {
                findOptions(i, args);
            }
        } else if (LOAD_ARGUMENT.contains(args[0])) {
            System.out.println("Finish developing loading maps");
        } else if (HELP_ARGUMENT.contains(args[0])) {
            displayHelp();
            System.exit(0);
        }

        try {
            populationSize = Integer.parseInt(args[args.length - 4]);
            numberOfGenerations = Integer.parseInt(args[args.length - 3]);
            dungeonWidth = Integer.parseInt(args[args.length - 2]);
            dungeonHeight = Integer.parseInt(args[args.length - 1]);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing dungeon variables, you need to supply" +
                    "\nPopulation Size" +
                    "\nNumber of Generations" +
                    "\nWidth of Maps" +
                    "\nHeight of Maps");
            System.exit(1);
        }

        //Preparation, either create maps from noiseStrategy or load some
        if (CREATE_ARGUMENT.contains(args[0])) {
            if (noiseStrategy == null) {
                throw new RuntimeException("todo error");
            } else {
                noiseMaps();
            }
        } else if (args[0].equals(LOAD_ARGUMENT.get(0)))
            System.exit(1);//todo finish this path

        try {
            chromosomeEvaluation = new BasicChromosomeEvaluation(populationSize, numberOfGenerations, 0.2,
                    fitnessStrategyList, mutatorStrategy, selectionStrategy, correctionStrategyList, crossoverStrategy);
            chromosomeEvaluation = new AttachLogChromosomeEvaluation(chromosomeEvaluation);
            chromosomeEvaluation = new AttachTimeChromosomeEvaluation(chromosomeEvaluation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        evolutionResults = chromosomeEvaluation.crossoverPopulation(chromosomeGeneration);

        if (outputDirectory != null) {
            evolutionResults.saveAllResults(outputDirectory);
        } else {
            evolutionResults.saveAllResults();
        }
    }

    private void findOptions(int i, String... args) {
        if(FITNESS_ARGUMENT.contains(args[i])){
            addFitnessStrategy(args[i + 1]);
        }
        else if(VERBOSE_ARGUMENT.contains(args[i])){
            System.out.println("Finish this path");
            verbose = true;
            System.exit(1);
        }
        else if(MUTATOR_ARGUMENT.contains(args[i])){
            addMutatorStrategy(args[i + 1], args[i + 2]);
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
        else if(CROSSOVER_ARGUMENT.contains(args[i])){
            //TODO this one in particular takes an argument
            addCrossoverStrategy(args[i + 1]);
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
                        numberOfGenerations, 0.3, fitnessStrategyList, mutatorStrategy,
                        selectionStrategy, correctionStrategyList, crossoverStrategy);
                return true;
            default:
                return false;
        }
    }

    private boolean addNoiseStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case RandomNoiseStrategy.IMPLEMENTATION:
                noiseStrategy = new RandomNoiseStrategy();
                return true;
            case FillNoiseStrategy.IMPLEMENTATION:
                noiseStrategy = new FillNoiseStrategy();
                return true;
            case CaveCellularAutomateNoise.IMPLEMENTATION:
                noiseStrategy = new CaveCellularAutomateNoise();
                return true;
            case IslandCellularAutomateNoise.IMPLEMENTATION:
                noiseStrategy = new IslandCellularAutomateNoise();
                return true;
            default:
                return false;
        }
    }

    private boolean addFitnessStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case FindAllRoomsFitnessStrategy.IMPLEMENTATION :
                fitnessStrategyList.add(new FindAllRoomsFitnessStrategy());
                return true;
            case IsTraversableStrategy.IMPLEMENTATION :
                fitnessStrategyList.add(new IsTraversableStrategy());
                return true;
            default:
                return false;
        }
    }

    private boolean addCorrectionStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case FindRoomStrategy.IMPLEMENTATION:
                correctionStrategyList.add(new FindRoomStrategy());
                return true;
            case FindHolesStrategy.IMPLEMENTATION:
                correctionStrategyList.add(new FindHolesStrategy());
                return true;
            case AddPermanentWallsStrategy.IMPLEMENTATION:
                correctionStrategyList.add(new AddPermanentWallsStrategy());
                return true;
            case AddPermanentRoomsStrategy.IMPLEMENTATION:
                //TODO this count number
                correctionStrategyList.add(new AddPermanentRoomsStrategy(10));
                return true;
            default:
                return false;
        }
    }

    private boolean addSelectionStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case EliteSelectionStrategy.IMPLEMENTATION:
                selectionStrategy = new EliteSelectionStrategy();
                return true;
            case TournamentSelectionStrategy.IMPLEMENTATION:
                selectionStrategy = new TournamentSelectionStrategy();
                return true;
            case RouletteSelectionStrategy.IMPLEMENTATION:
                selectionStrategy = new RouletteSelectionStrategy();
                return true;
            case StochasticTwoSelectionStrategy.IMPLEMENTATION:
                selectionStrategy = new StochasticTwoSelectionStrategy();
                return true;
            case RankSelectionStrategy.IMPLEMENTATION:
                selectionStrategy = new RankSelectionStrategy();
                return true;
            default:
                return false;
        }
    }

    private boolean addCrossoverStrategy(String option) {
        String choice = option.toLowerCase().trim();

        switch (choice) {
            case DefaultCrossoverStrategy.IMPLEMENTATION:
                crossoverStrategy = new DefaultCrossoverStrategy();
                return true;
            case UniformCrossoverStrategy.IMPLEMENTATION:
                crossoverStrategy = new UniformCrossoverStrategy();
                return true;
            default:
                return false;
        }
    }


    private boolean addMutatorStrategy(String option, String odds) {
        option = option.toLowerCase().trim();
        //TODO change this odds and odds2
        double odds2;

        try{
            odds2 = Double.parseDouble(odds);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return false;
        }

        switch (option) {
            case DefaultMutatorStrategy.IMPLEMENTATION:
                mutatorStrategy = new DefaultMutatorStrategy(odds2);
                return true;
            case SwapPermutationStrategy.IMPLEMENTATION:
                mutatorStrategy= new SwapPermutationStrategy(odds2);
                return true;
            default:
                return false;
        }
    }

    private boolean noiseMaps() {
        //Create NoiseStrategy for maps
        chromosomeGeneration = noiseStrategy.createNoise(dungeonWidth, dungeonHeight, populationSize, 0.45);
        return true;
    }

    private void displayHelp() {
       System.out.println(HELP_PAGE);
       System.out.println(README);
       System.out.println(AVAILABLE_OPTIONS);
    }

    //All arguments that the program takes, usually divided into short one letter flag and
    // verbose alternative
    private final ArrayList<String> CREATE_ARGUMENT = new ArrayList(Arrays.asList("-c", "--create"));
    private final ArrayList<String> LOAD_ARGUMENT = new ArrayList(Arrays.asList("-l", "--load"));
    private final ArrayList<String> HELP_ARGUMENT = new ArrayList(Arrays.asList("-h", "--help"));
    private final ArrayList<String> VERBOSE_ARGUMENT = new ArrayList(Arrays.asList("-v", "--verbose"));
    private final ArrayList<String> FITNESS_ARGUMENT = new ArrayList(Arrays.asList("-f", "--fitness"));
    private final ArrayList<String> MUTATOR_ARGUMENT = new ArrayList(Arrays.asList("-m", "--mutator"));
    private final ArrayList<String> CHROMOSOME_EVALUATION_ARGUMENT = new ArrayList(Arrays.asList("-e", "--evaluation"));
    private final ArrayList<String> CORRECTION_ARGUMENT = new ArrayList(Arrays.asList("-r", "--correction"));
    private final ArrayList<String> NOISE_ARGUMENT = new ArrayList(Arrays.asList("-n", "--noise"));
    private final ArrayList<String> SELECTION_ARGUMENT = new ArrayList(Arrays.asList("-s", "--selection"));
    private final ArrayList<String> CROSSOVER_ARGUMENT = new ArrayList(Arrays.asList("-g", "--crossover"));
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
            "\n" + CORRECTION_ARGUMENT + "\t[NAME]\t\t\tAdd [CORRECTION] Function" +
            "\n" + CROSSOVER_ARGUMENT + "\t[NAME]\t\t\tAdd [OFFSPRING] Function" +
            "\n" + VERBOSE_ARGUMENT + "\t\t\t\t\t\tVerbose output" +
            "\n\n\n Genetic Algorithm to work at minimum needs: [FITNESS]... [SELECTION] [OFFSPRING] [MUTATOR] [CHROMOSOME EVALUATION]" +
            "\n If you are creating maps, you need to add [NOISE]" +
            "\n If you are loading maps, [SOURCE] needs to specify a directory with them" +
            "\n Optional Genetic Options: [CORRECTION]";


    private final String README = "\nExamples:" +
            "\njava GMaps --create --noiseStrategy noiseStrategy --fitness find_all_rooms --selectionStrategy tournament --crossoverStrategy default --mutatorStrategy" +
            " default --cellular rule20 --permutationStrategy swap -r find_room 100 3000 30 25" +
            "\njava GMaps --load /home/user/folder/ 1 10 150 100";

    private final String AVAILABLE_OPTIONS =
            "\nAvailable [NAME]:\n" +
                    "\nFitness:" +
                    "\n\t" + FindAllRoomsFitnessStrategy.IMPLEMENTATION  +
                    "\n\t" + IsTraversableStrategy.IMPLEMENTATION  +

                    "\nCorrectionStrategy:" +
                    "\n\t" + FindHolesStrategy.IMPLEMENTATION +
                    "\n\t" + FindRoomStrategy.IMPLEMENTATION +

                    "\nMutations:" +
                    "\n\t" + DefaultMutatorStrategy.IMPLEMENTATION +

                    "\nNoise:" +
                    "\n\t" + FillNoiseStrategy.IMPLEMENTATION +
                    "\n\t" + RandomNoiseStrategy.IMPLEMENTATION +
                    "\n\t" + CaveCellularAutomateNoise.IMPLEMENTATION +

                    "\nPermutation:" +
                    "\n\t" + SwapPermutationStrategy.IMPLEMENTATION +
                    "\n\t" + InversionPermutationStrategy.IMPLEMENTATION  +
                    "\n\t" + ScramblePermutationStrategy.IMPLEMENTATION  +

                    "\nSelection:" +
                    "\n\t" + EliteSelectionStrategy.IMPLEMENTATION  +
                    "\n\t" + TournamentSelectionStrategy.IMPLEMENTATION  +
                    "\n\t" + RouletteSelectionStrategy.IMPLEMENTATION  +
                    "\n\t" + RankSelectionStrategy.IMPLEMENTATION  +
                    "\n\t" + StochasticTwoSelectionStrategy.IMPLEMENTATION  +

                    "\nOffspring:" +
                    "\n\t" + DefaultCrossoverStrategy.IMPLEMENTATION;

    //A whole generation of maps
    private ArrayList<Chromosome> chromosomeGeneration = new ArrayList<>();

    //Our FitnessStrategy implementations, we need at least one way
    // to evaluate a map
    private ArrayList<FitnessStrategy> fitnessStrategyList = new ArrayList<>();
    private SelectionStrategy selectionStrategy;
    private MutatorStrategy mutatorStrategy;
    private NoiseStrategy noiseStrategy;
    private CrossoverStrategy crossoverStrategy;
    private ArrayList<CorrectionStrategy> correctionStrategyList = new ArrayList<>();

    //if specified, saves maps to the output directory
    private String outputDirectory = null;

    //Chromosome collects all strategy elements
    // and evaluates our whole project
    private AbstractChromosomeEvaluation chromosomeEvaluation;

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
}
