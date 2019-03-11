package Genetic_Algorithm;

import Algorithms.Algorithms;
import Dungeon.Dungeon;
import java.io.IOException;
import java.util.ArrayList;

public class PrintBasicChromosomeEvaluation extends DecoratorChromosomeEvaluation {

    private AbstractChromosomeEvaluation abstractChromosomeEvaluation;

    public PrintBasicChromosomeEvaluation(AbstractChromosomeEvaluation abstractChromosomeEvaluation) {
        this.abstractChromosomeEvaluation = abstractChromosomeEvaluation;
    }

    @Override
    public ArrayList<Dungeon> crossoverPopulation(ArrayList<Dungeon> mapList, ArrayList<FitnessImp> fitnessImpList, int numberOfGenerations, MutationsEnum mutation) {
        ArrayList<Dungeon> k = abstractChromosomeEvaluation.crossoverPopulation(mapList, fitnessImpList, numberOfGenerations, mutation);//TODO its kinda like calling super

        for (Dungeon dungeon : k) {
            try {
                Algorithms.writeToFile("", dungeon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return k;
    }


    @Override
    public int getCrossoverPoint(Dungeon dungeon) {
        return 0;
    }
}
