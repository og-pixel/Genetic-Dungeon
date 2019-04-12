import Algorithms.*;
import Algorithms.CA.*;
import Dungeon.Dungeon;
import Genetic_Algorithm.ChromosomeEvaluation.AbstractChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.BasicChromosomeEvaluation;
import Genetic_Algorithm.ChromosomeEvaluation.PrintBasicChromosomeEvaluation;
import Genetic_Algorithm.Fitness.FitnessEnum;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.Mutation.MutationsEnum;
import Genetic_Algorithm.Population.PopulationEnum;
import Genetic_Algorithm.Population.PopulationImp;

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

    private final String README = "Examples:\n-c 10 15 10 Create 10 maps of size 15 by 10\n" +
            "-c 1 100 100 Create 1 map of size 100 by 100";


    private ArrayList<Dungeon> mapList;

    private ArrayList<FitnessImp> fitnessImpList;
    private ArrayList<PopulationImp> populationImpList;
    private CellurarAutomataImp cellurarAutomataImp;

    private int populationSize;
    private int generations;
    private int dungeonWidth;
    private int dungeonHeight;

    public Interpreter(String... args){
        setFileHandler();

        if(args.length <= 0){
            displayHelp();
            System.exit(1);
        }

        populationSize = Integer.parseInt(args[1]);
        generations = Integer.parseInt(args[2]);
        dungeonWidth = Integer.parseInt(args[3]);
        dungeonHeight = Integer.parseInt(args[4]);

        fitnessImpList = new ArrayList<>();
        populationImpList = new ArrayList<>();

        mapList = null;

        interpretArguments();
    }


    private void interpretArguments(){
        //Measure time
        float timeNow = System.nanoTime();

        //List of maps
        mapList = new ArrayList<>();

        ArrayList<Dungeon> nextGeneration = new ArrayList<>();

        //Create Fitness
        addFitnessStrategy("find_all_rooms");

        //Fill maps with cellurar automata data (random 1's and 0's with loaded odds)
        addPopulationStrategy("noise");

        //Run over Cellurar Automata to modify map a bit
        addCellurarAutomataStrategy("rule20");


        //Create random population for maps
        for (int i = 0; i < populationImpList.size(); i++) {
            mapList = populationImpList.get(i).createPopulation(dungeonWidth, dungeonHeight, populationSize, 0.6);
        }

        ////////////////////////////
        //Run over generated maps with CA
        for (int i = 0; i < mapList.size(); i++) {
            Matrix k = cellurarAutomataImp.generateMap(mapList.get(i).getDungeonMatrix());
            Dungeon kk = new Dungeon(k);
            mapList.set(i, kk);
        }


        //ADDING this wraps my object and adds filewriting
        AbstractChromosomeEvaluation z = new PrintBasicChromosomeEvaluation(new BasicChromosomeEvaluation(0.1, populationSize));
        nextGeneration = z.crossoverPopulation(mapList, fitnessImpList, generations, MutationsEnum.LOWER);//TODO i moved mutation but it actually has to use thi variable now

        LOGGER.log(Level.INFO, "Finished after: " + ((System.nanoTime() - timeNow) / 1000000000) + " seconds");
    }

    private boolean addPopulationStrategy(String option){
        String choice  = option.toLowerCase().trim();

        switch(choice) {
            case "noise":
                populationImpList.add(PopulationEnum.NOISE);
                return true;
            case "fill":
                populationImpList.add(PopulationEnum.FILL);
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

    private boolean addCellurarAutomataStrategy(String option){
        String choice  = option.toLowerCase().trim();

        switch(choice) {
            case "rule20":
                cellurarAutomataImp = new Rule20CellurarAutomata();
                return true;
            default:
                return false;
        }
    }

    //TODO better description
    //Create a file handler
    private boolean setFileHandler(){
        File serverDirectory = new File("Logs/");

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
        System.out.println(README);
    }
}
