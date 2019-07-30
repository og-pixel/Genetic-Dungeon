package Genetic_Algorithm.OffspringStrategy;

import Chromosome.Chromosome;
import Algorithms.Algorithms;
import java.util.ArrayList;
import java.util.Random;

public class DefaultOffspringStrategy implements OffspringStrategy {

    public static final String IMPLEMENTATION = "default";

    @Override
    public ArrayList<Chromosome> createNewGeneration(ArrayList<Chromosome> list, double populationSize, double selectionFraction) {

        Random random = new Random();
        ArrayList<Chromosome> newPopulation = new ArrayList<>();

        while (newPopulation.size() < populationSize) {
            //THIS is mutation part
            int randomPick = random.nextInt(list.size());
            Chromosome parent1 = list.get(randomPick);

            randomPick = random.nextInt(list.size());
            Chromosome parent2 = list.get(randomPick);

            Chromosome child1 = Algorithms.deepClone(parent1);
            Chromosome child2 = Algorithms.deepClone(parent2);

            int crossPointX = random.nextInt(parent1.getMapWidth());
            int crossPointY = random.nextInt(parent1.getMapHeight());


            for (int y = 0; y < crossPointY - 1; y++) {
                child1.getMapMatrix().replaceRow(y, child2.getMapMatrix().getRow(y));
            }

            for (int x = 0; x < crossPointX; x++) {
                child1.getMapMatrix().put(x, crossPointY, child2.getMapMatrix().getElement(x, crossPointY));
            }

            for (int x = crossPointX; x < parent1.getMapMatrix().getWidth(); x++) {
                child2.getMapMatrix().put(x, crossPointY, child1.getMapMatrix().getElement(x, crossPointY));
            }

            for (int y = crossPointY + 1; y < parent1.getMapHeight(); y++) {
                child2.getMapMatrix().replaceRow(y, child1.getMapMatrix().getRow(y));
            }

            newPopulation.add(child1);
            newPopulation.add(child2);
        }
        return newPopulation;
    }
}
