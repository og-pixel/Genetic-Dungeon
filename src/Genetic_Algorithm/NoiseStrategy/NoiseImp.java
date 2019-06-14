package Genetic_Algorithm.NoiseStrategy;

import Map.Map;

import java.util.ArrayList;

public interface NoiseImp {
    /**
     * Creates a base noise used in random map generation
     * @param width
     * @param height
     * @param numberOfMaps
     * @return
     */
    ArrayList<Map> createNoise(int width, int height, int numberOfMaps, double odds);
}
