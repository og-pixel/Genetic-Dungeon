package Genetic_Algorithm.ChromosomeEvaluation;

import Algorithms.Algorithms;
import Dungeon.Dungeon;
import Exceptions.VariableBoundsException;
import Genetic_Algorithm.Data.EvolutionDetails;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.ManualCorrections.CorrectionEnum;
import Genetic_Algorithm.Mutation.MutationsEnum;
import Genetic_Algorithm.Premutation.PremutationEnum;
import Genetic_Algorithm.Selection.SelectionEnum;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.*;

public class BasicChromosomeEvaluation extends AbstractChromosomeEvaluation {

    private static final Logger LOGGER = Logger.getLogger(BasicChromosomeEvaluation.class.getName());
    private double TOP_POP; //todo final?
    private double POP_SIZE;


    public BasicChromosomeEvaluation(double topPopulation, double populationSize){
        //TODO added
        LOGGER.setLevel(Level.WARNING);

        if(topPopulation < 0.1 || topPopulation > 1)throw new VariableBoundsException(0.1, 1);

        if(populationSize > 1000) System.err.println("Population size is beyond 1000, program might take a long time and" +
                "results probably won't be better");

        if(topPopulation != 0.1) System.err.println("Default crossover behaviour recommends 0.1 (10%) of the best " +
                "population to mate");

        POP_SIZE = populationSize;
        TOP_POP = topPopulation * populationSize;

        //If there are less maps than to make even 1, then we have to force it
        if(TOP_POP < 1)TOP_POP = 1;//todo consider 2
    }

    @Override
    public EvolutionDetails crossoverPopulation(ArrayList<Dungeon> mapList, ArrayList<FitnessImp> fitnessImpList,
                                                int numberOfGenerations,
                                                MutationsEnum mutation, SelectionEnum selection, PremutationEnum premutation,
                                                CorrectionEnum correction) {
        Random random = new Random();
        ArrayList<Dungeon> newPopulation = new ArrayList<>();
        double iteration = numberOfGenerations * 0.01;//every 1%

        //todo delete
        setFileHandler();

        //todo this is new data structure
        EvolutionDetails evolutionDetails = new EvolutionDetails();

        int percentageDone = 0;

        for (int generation = 0; generation < numberOfGenerations; generation++) {


            //Evaluate all dungeon based on all fitness implementations on the list
            //TODO so far its just one, the one that finds all rooms
            for (FitnessImp fitnessImp : fitnessImpList) {
                for (Dungeon dungeon : mapList) {
                    fitnessImp.evaluateDungeon(dungeon);
                }
            }

            //Evaluate all dungeon based on all fitness implementations on the list
            //TODO so far its just one, the one that finds all rooms
            for (Dungeon dungeon : mapList) {
                correction.correct(dungeon);
            }

            //NEW AND IMPROVED
            mapList = selection.useSelection(mapList);



            if(generation % iteration == 0) {
                LOGGER.log(Level.INFO, generation + "th generation\nTop Speciment Score: " + mapList.get(0).getScore() + "\nTop Speciment Number of Rooms: " + mapList.get(0).getNumberOfRooms() + "\n");
                percentageDone++;
                System.out.println(percentageDone + "%");
            }
            evolutionDetails.addRow(Algorithms.deepClone(mapList));



            while (newPopulation.size() < POP_SIZE) {
                //THIS IS premutation part
//                premutation.premutateDungeons(mapList);

                //THIS is mutation part
                int randomPick = random.nextInt((int) TOP_POP);
                Dungeon parent1 = mapList.get(randomPick);

                randomPick = random.nextInt((int) TOP_POP);
                Dungeon parent2 = mapList.get(randomPick);

                Dungeon child1 = Algorithms.deepClone(parent1);
                Dungeon child2 = Algorithms.deepClone(parent2);

                int crossPointX = random.nextInt(parent1.getDungeonWidth());
                int crossPointY = random.nextInt(parent1.getDungeonHeight());


                for (int y = 0; y < crossPointY - 1; y++) {
                    child1.getDungeonMatrix().replaceRow(y, child2.getDungeonMatrix().getRow(y));
                }
                for (int x = 0; x < crossPointX; x++) {
                    child1.getDungeonMatrix().put(x, crossPointY, child2.getDungeonMatrix().getElement(x, crossPointY));
                }




                for (int x = crossPointX; x < parent1.getDungeonMatrix().getWidth(); x++) {
                    child2.getDungeonMatrix().put(x, crossPointY, child1.getDungeonMatrix().getElement(x, crossPointY));
                }

                for (int y = crossPointY + 1; y < parent1.getDungeonHeight(); y++) {
                    child2.getDungeonMatrix().replaceRow(y, child1.getDungeonMatrix().getRow(y));
                }

//                for (int i = 0; i < parent1.getDungeonHeight()/2; i++) {
//                    child1.getDungeonMatrix().replaceRow(i, parent2.getDungeonMatrix().getRow(i));
//                }
//
//                for (int i = parent2.getDungeonHeight()/2; i < parent2.getDungeonHeight(); i++) {
//                    child2.getDungeonMatrix().replaceRow(i, parent1.getDungeonMatrix().getRow(i));
//                }

                newPopulation.add(child1);
                newPopulation.add(child2);
            }

            mutation.mutateDungeons(newPopulation);//TODO it might not mutate sort makes no sense as i didnt evaluate it again

            mapList = newPopulation;//TODO it might make it work or not
//            newPopulation.sort(Comparator.comparing(Dungeon::getScore).reversed());
        }

        return evolutionDetails;
    }


    //TODO since its a matrix and not a string, I can either stringfly it
    // or have funky crossover points based on width or height
    // For now I will simply get mid width/height
    @Override
    public int getCrossoverPoint(Dungeon dungeon) {
        return dungeon.getDungeonMatrix().getHeight()/2;
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
}