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

        DefaultMutator defaultMutator = new DefaultMutator(0.1);
        ArrayList<Dungeon> newPopulation = new ArrayList<>();


        for (int generation = 0; generation < numberOfGenerations; generation++) {

            for (int i = 0; i < fitnessImpList.size(); i++) {
                for (int x = 0; x < mapList.size(); x++) {
                    mapList.get(x).setScore(
                            fitnessImpList.get(i).evaluateDungeon(mapList.get(x)));
//                    defaultMutator.mutateDungeon(mapList.get(x)); todo i dont think i have to mutate here, should be a separate thing
                }
            }

            Collections.sort(mapList, Comparator.comparing(Dungeon::getScore));


            Random random = new Random();
            while (newPopulation.size() < POP_SIZE) {
                int randomPick = (int) (mapList.size() * TOP_POP);
                if (randomPick <= 0) randomPick = 1;

                newPopulation.add(mapList.get(random.nextInt(randomPick)));//todo simplify as it looks like shit
                newPopulation.get(newPopulation.size() - 1).createStartPosition();
                newPopulation.get(newPopulation.size() - 1).createEndPosition(); //todo i really dislike how i write the whole thing but should be enough for now
            }

            mapList = newPopulation; //new pop!
        }
        return newPopulation;
    }
}

