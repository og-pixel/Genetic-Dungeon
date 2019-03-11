package Genetic_Algorithm;

import Algorithms.Algorithms;
import Dungeon.Dungeon;
import Exceptions.VariableBoundsException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class BasicChromosomeEvaluation extends AbstractChromosomeEvaluation {

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

        for (int generation = 0; generation < numberOfGenerations; generation++) {

            for (FitnessImp fitnessImp : fitnessImpList) {
                for (Dungeon dungeon : mapList) {
                    dungeon.setScore(fitnessImp.evaluateDungeon(dungeon));
                }
            }
            //First elements are the most fit
            mapList.sort(Comparator.comparing(Dungeon::getScore).reversed());

            //TODO This part takes top 10% of pop and removes the rest
            mapList.subList((int)(TOP_POP), mapList.size()).clear();


            Random random = new Random();

            while (newPopulation.size() < POP_SIZE) {
                //TODO for now parents can be the same etc
                int randomPick = random.nextInt((int) TOP_POP);
                Dungeon parent1 = mapList.get(randomPick);

                randomPick = random.nextInt((int) TOP_POP);
                Dungeon parent2 = mapList.get(randomPick);

                //TODO without DeepClone method (serialize and deserlize object)
                // it would still hold references from parents, we need NEW objects
                Dungeon child1 = Algorithms.deepClone(parent1);
                Dungeon child2 = Algorithms.deepClone(parent2);

                for (int i = 0; i < parent1.getDungeonHeight()/2; i++) {
                    child1.getDungeonMatrix().replaceRow(i, parent2.getDungeonMatrix().getRow(i));
                }

                for (int i = parent2.getDungeonHeight()/2; i < parent2.getDungeonHeight(); i++) {
                    child2.getDungeonMatrix().replaceRow(i, parent1.getDungeonMatrix().getRow(i));
                }


                newPopulation.add(child1);
                newPopulation.add(child2);

                //TODO I have temporary removed it, I think I dont need it and its overly confusing
//                newPopulation.get(newPopulation.size() - 1).createStartPosition();
//                newPopulation.get(newPopulation.size() - 1).createEndPosition(); //todo i really dislike how i write the whole thing but should be enough for now
            }
//            mapList = newPopulation; //new pop!
        }

        mutation.mutateDungeons(newPopulation);//TODO it might not mutate kutas
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