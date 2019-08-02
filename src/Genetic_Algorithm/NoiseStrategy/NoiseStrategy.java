package Genetic_Algorithm.NoiseStrategy;

import Chromosome.*;

import java.util.ArrayList;

public interface NoiseStrategy {
    /**
     * Creates a base noise used in random map generation
     * @param width
     * @param height
     * @param numberOfMaps
     * @return
     */
    ArrayList<Chromosome> createNoise(int width, int height, int numberOfMaps, double odds);
//    String IMPLEMENTATION = "unknown strategy";
}
