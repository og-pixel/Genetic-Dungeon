package Genetic_Algorithm.NoiseStrategy;

import Chromosome.*;

import java.util.ArrayList;
import static Chromosome.TileList.*;

public class FillNoiseStrategy implements NoiseStrategy {

    public static final String IMPLEMENTATION = "fill";

    @Override
    public ArrayList<Chromosome> createNoise(int width, int height, int numberOfMaps, double odds) {
         ArrayList<Chromosome> chromosomeList = new ArrayList<>();
            for (int i = 0; i < numberOfMaps; i++) {
                chromosomeList.add(new Chromosome(width, height));

                chromosomeList.get(i).getMapMatrix().fillMatrix(WALL);

                chromosomeList.get(i).createStartPosition();
                chromosomeList.get(i).createEndPosition();
            }
            return chromosomeList;
    }
}
