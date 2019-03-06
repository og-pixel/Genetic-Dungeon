package Genetic_Algorithm;

import Dungeon.Dungeon;
import Exceptions.VariableBoundsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class CrossoverBehaviour implements CrossoverImp {

    private double TOP_POP; //todo final?
    private double POP_SIZE;


    public CrossoverBehaviour(double topPopulation, double populationSize){

        if(topPopulation < 0.1 || topPopulation > 1)throw new VariableBoundsException(0.1,1);

        if(topPopulation != 0.1) System.err.println("Default crossover behaviour recommends 0.1 (10%) of the best " +
                                                    "population to mate");
        TOP_POP = topPopulation;

        if(populationSize > 1000) System.err.println("Population size is beyond 1000, program might take a long time");
        POP_SIZE = populationSize;
    }


    @Override
    public ArrayList<Dungeon> crossoverPopulation(ArrayList<Dungeon> mapList, ArrayList<FitnessImp> fitnessImpList,
                                                  int numberOfGenerations) {

        ArrayList<Dungeon> newPopulation = new ArrayList<>();

        for (int generation = 0; generation < numberOfGenerations; generation++) {

            for (int i = 0; i < fitnessImpList.size(); i++) {
                for (int x = 0; x < mapList.size(); x++) {
                    mapList.get(x).setScore(
                            fitnessImpList.get(i).evaluateDungeon(mapList.get(x)));
                }
            }


            //First elements are the most fit
            mapList.sort(Comparator.comparing(Dungeon::getScore).reversed());
            //CUT mapList to 10%

            //TODO this casting might cause trouble (losing floating point)
            //todo added +1
            mapList.subList((int)(TOP_POP*POP_SIZE) + 1, mapList.size()).clear();




            Random random = new Random();

            while (newPopulation.size() < POP_SIZE) {
                //TODO for now parents can be the same etc
                int randomPick = (int)(TOP_POP*POP_SIZE);
//                if (randomPick <= 0) randomPick = 1;
                Dungeon parent1 = mapList.get(randomPick);

                randomPick = (int)(TOP_POP*POP_SIZE);
//                if (randomPick <= 0) randomPick = 1;
                Dungeon parent2 = mapList.get(randomPick);

                Dungeon child1 = parent1;
                Dungeon child2 = parent2;

                for (int i = 0; i < parent1.getDungeonHeight()/2; i++) {
                    child1.getDungeonMatrix().replaceRow(i ,parent2.getDungeonMatrix().getRow(i));
                }

                for (int i = parent2.getDungeonHeight()/2; i < parent2.getDungeonHeight(); i++) {
                    child2.getDungeonMatrix().replaceRow(i ,parent1.getDungeonMatrix().getRow(i));
                }


                newPopulation.add(child1);
                newPopulation.add(child2);

                //TODO I have temporary removed it, I think I dont need it and its overly confusing
//                newPopulation.get(newPopulation.size() - 1).createStartPosition();
//                newPopulation.get(newPopulation.size() - 1).createEndPosition(); //todo i really dislike how i write the whole thing but should be enough for now
            }
//            mapList = newPopulation; //new pop!
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

    public void test(){

    }

}