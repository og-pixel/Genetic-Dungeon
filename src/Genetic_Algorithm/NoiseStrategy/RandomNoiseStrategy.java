package Genetic_Algorithm.NoiseStrategy;

import Exceptions.VariableBoundsException;
import Chromosome.*;

import java.util.ArrayList;
import java.util.Random;
import static Chromosome.TileList.*;

public class RandomNoiseStrategy implements NoiseStrategy {

    public static final String IMPLEMENTATION = "random_noise";

    @Override
    public ArrayList<Chromosome> createNoise(int width, int height, int numberOfMaps, double odds) {
        if (odds <= 0 || odds > 1) throw new VariableBoundsException(0, 1.0);
        Random random = new Random();

        ArrayList<Chromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < numberOfMaps; i++) {
            chromosomeList.add(new Chromosome(width, height));

            for (Chromosome chromosome : chromosomeList) {

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (random.nextDouble() > odds) chromosome.getMapMatrix().put(x, y, CORRIDOR);
                        else chromosome.getMapMatrix().put(x, y, WALL);
                    }
                }
            }
            chromosomeList.get(i).createStartPosition();
            chromosomeList.get(i).createEndPosition();
        }
        return chromosomeList;
    }
}
