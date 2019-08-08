package Genetic_Algorithm.NoiseStrategy;

import Chromosome.*;
import java.util.ArrayList;

public interface NoiseStrategy {
    ArrayList<Chromosome> createNoise(int width, int height, int numberOfMaps, double odds);
}
