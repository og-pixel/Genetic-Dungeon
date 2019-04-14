import Algorithms.*;
import Algorithms.CA.*;
import Dungeon.Dungeon;
import Genetic_Algorithm.ChromosomeEvaluation.AbstractChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.BasicChromosomeEvaluation;
import Genetic_Algorithm.Data.EvolutionDetails;
import Genetic_Algorithm.Fitness.FitnessEnum;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.Mutation.MutationsEnum;
import Genetic_Algorithm.Population.NoiseEnum;
import Genetic_Algorithm.Population.NoiseImp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Basic Interpreter is used for demonstrations of the entire project
 * it does read arguments and does something with them
 */
public class Interpreter {

    private static final Logger LOGGER = Logger.getLogger(Interpreter.class.getName());

    private final String CREATE = "-c";
    private final String HELP = "-h";

    private final String HELPz = "Usage: GA-Main [options]";
    private final String OPTION = "Options: ";
    private final String OPTION1 = "-c\t\tCreate map evaluation";
    private final String OPTION2 = "todo";
    private final String OPTION3 = "todo";
    private final String OPTION4 = "todo";
    private final String OPTION5 = "todo";

    private final String README = "Examples:\n-c 10 20 15 10 During 20 generations, create 10 maps of size 15 by 10 each\n" +
            "-c 1 10 150 100 During 10 generations, create 1 map of size 150 by 100 each";


    //A whole generation of maps
    private ArrayList<Dungeon> mapGeneration;

    private ArrayList<FitnessImp> fitnessImpList;
    private NoiseImp noiseImp;
    private AbstractChromosomeEvaluation chromosomeEvaluationImp;
    private CellurarAutomataImp cellurarAutomataImp;

    //Results after running program
    private EvolutionDetails evolutionDetails;

    private int populationSize;
    private int numberOfGenerations;
    private int dungeonWidth;
    private int dungeonHeight;

    public Interpreter(String... args){
        LOGGER.setLevel(Level.WARNING);
        setFileHandler();

        if(args.length <= 0){
            displayHelp();
            System.exit(1);
        }

        populationSize = Integer.parseInt(args[1]);
        numberOfGenerations = Integer.parseInt(args[2]);
        dungeonWidth = Integer.parseInt(args[3]);
        dungeonHeight = Integer.parseInt(args[4]);

        fitnessImpList = new ArrayList<>();

        mapGeneration = new ArrayList<>();

        interpretArguments();
    }


    private void interpretArguments(){
        //Measure time
        float timeNow = System.nanoTime();

        //Add Fitness to utilize
        addFitnessStrategy("find_all_rooms");
        //Add noise to empty maps
        addNoiseStrategy("fill");
        //Add Cellurar Automata rule to modify map a bit
        addCellularAutomataStrategy("rule20");
        //Add Evaluation Strategy, for now there is only basic
        addChromosomeEvaluationStrategy("basic");

        noiseMaps();
        caMaps();
        evaluateMaps();


        evolutionDetails.saveResults();
        LOGGER.log(Level.INFO, "Finished after: " + ((System.nanoTime() - timeNow) / 1000000000) + " seconds");

        try {
            Algorithms.writeToFile("BEST", evolutionDetails.findBest());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean addChromosomeEvaluationStrategy(String option){
        String choice = option.toLowerCase().trim();
        switch(choice){
            case "basic":
                chromosomeEvaluationImp = new BasicChromosomeEvaluation(0.1, populationSize);
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
                cellurarAutomataImp = new Rule20CellurarAutomata();
                return true;
            default:
                return false;
        }
    }

    private boolean noiseMaps(){
        //Create Noise for maps
        mapGeneration = noiseImp.createNoise(dungeonWidth, dungeonHeight, populationSize, 0.6);
        return true;
    }

    private boolean caMaps(){
        //Run Cellurar Automata
        for (int i = 0; i < mapGeneration.size(); i++) {
            Matrix k = cellurarAutomataImp.generateMap(mapGeneration.get(i).getDungeonMatrix());
            Dungeon kk = new Dungeon(k);
            mapGeneration.set(i, kk);
        }
        return true;
    }

    private boolean evaluateMaps(){

        evolutionDetails = chromosomeEvaluationImp.crossoverPopulation(mapGeneration, fitnessImpList, numberOfGenerations, MutationsEnum.DEFAULT);//TODO i moved mutation but it actually has to use thi variable now

        return true;
    }


    //TODO better description
    //Create a file handler
    private boolean setFileHandler(){
        File serverDirectory = new File("Logs/");

        //todo this makes a folder
        serverDirectory.mkdir();

        // This block configure the logger with handler and formatter

        FileHandler fh;
        int numberOfFiles = new File("Logs/").listFiles().length;
        try {
            fh = new FileHandler("Logs/" + "log_" + numberOfFiles + ".log");
        } catch (IOException e) {
            LOGGER.warning("Couldn't create log file!\n" + e.getMessage());
            return false;
        }
        LOGGER.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        LOGGER.info("Created a log file ");

        return true;
    }

    private void displayHelp(){
        System.out.println(HELPz);
        System.out.println("\n" + OPTION);
        System.out.println(OPTION1);
        System.out.println("\n" + README);
    }
}
