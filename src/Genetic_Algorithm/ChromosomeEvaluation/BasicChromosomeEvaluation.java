package Genetic_Algorithm.ChromosomeEvaluation;

import Algorithms.Algorithms;
import Dungeon.Dungeon;
import Exceptions.VariableBoundsException;
import Genetic_Algorithm.Fitness.FitnessEnum;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.Mutation.MutationsEnum;
import com.sun.javafx.logging.PlatformLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.logging.*;

public class BasicChromosomeEvaluation extends AbstractChromosomeEvaluation {

    private static final Logger LOGGER = Logger.getLogger(BasicChromosomeEvaluation.class.getName());
    private double TOP_POP; //todo final?
    private double POP_SIZE;


    public BasicChromosomeEvaluation(double topPopulation, double populationSize){

        if(topPopulation < 0.1 || topPopulation > 1)throw new VariableBoundsException(0.1, 1);

        if(populationSize > 1000) System.err.println("Population size is beyond 1000, program might take a long time");
        POP_SIZE = populationSize;

        if(topPopulation != 0.1) System.err.println("Default crossover behaviour recommends 0.1 (10%) of the best " +
                "population to mate");
        TOP_POP = topPopulation * populationSize;

        //If there are less maps than to make even 1, then we have to force it
        if(TOP_POP < 1)TOP_POP = 1;//todo consider 2
    }


    @Override
    public ArrayList<Dungeon> crossoverPopulation(ArrayList<Dungeon> mapList, ArrayList<FitnessImp> fitnessImpList,
                                                  int numberOfGenerations, MutationsEnum mutation) {

        ArrayList<Dungeon> newPopulation = new ArrayList<>();
        double iteration = numberOfGenerations * 0.01;

        for (int generation = 0; generation < numberOfGenerations; generation++) {

            for (FitnessImp fitnessImp : fitnessImpList) {
                for (Dungeon dungeon : mapList) {
                    fitnessImp.evaluateDungeon(dungeon);
                }
            }
            //First elements are the most fit
            mapList.sort(Comparator.comparing(Dungeon::getScore).reversed());

            //TODO This part takes top 10% of pop and removes the rest
            mapList.subList((int)(TOP_POP), mapList.size()).clear();

            if(generation % iteration == 0) {
                LOGGER.log(Level.INFO, generation + "th generation\nTop Speciment Score: " + mapList.get(0).getScore() + "\nTop Speciment Number of Rooms: " + mapList.get(0).getNumberOfRooms() + "\n");
                try {
                    Algorithms.writeToFile("Debug ", mapList.get(0));
                } catch (IOException e) {
                    System.out.println("eeoeeasfasfERROR");
                }
            }

            Random random = new Random();

            while (newPopulation.size() < POP_SIZE) {
                //TODO for now parents can be the same etc
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
        return newPopulation;
    }


    //TODO since its a matrix and not a string, I can either stringfly it
    // or have funky crossover points based on width or height
    // For now I will simply get mid width/height
    @Override
    public int getCrossoverPoint(Dungeon dungeon) {
        return dungeon.getDungeonMatrix().getHeight()/2;
    }
}