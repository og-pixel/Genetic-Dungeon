import Algorithms.Fitness;
import Algorithms.Mutator;
import Dungeon.Dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class CrossoverBehaviour implements CrossoverImp {
    @Override
    public void crossoverPopulation(ArrayList<Dungeon> mapList, ArrayList<Fitness> fitnessList) {
        Mutator mutator = new Mutator(0.1);

        float score;
        ArrayList<Dungeon> newPopulation = new ArrayList<>();
        int GENERATIONS = 3;

        for (int generation = 0; generation < GENERATIONS; generation++) {


            for (int i = 0; i < fitnessList.size(); i++) {
                for (int x = 0; x < mapList.size(); x++) {
                    mapList.get(x).setScore(
                            fitnessList.get(i).evaluateDungeon(mapList.get(x)));
                    mutator.mutateDungeon(mapList.get(x));
                }
            }

            Collections.sort(mapList, Comparator.comparing(Dungeon::getScore));

            double TOP_POP = 0.1; //10% of pop will mate todo final etc
            double POP_SIZE = 10;

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
    }
}

