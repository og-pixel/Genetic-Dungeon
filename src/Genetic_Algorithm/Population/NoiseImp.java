package Genetic_Algorithm.Population;

import Map.Map;

import java.util.ArrayList;

public interface NoiseImp {
    /**
     * Its Role is to to create a noise
     * @param width
     * @param height
     * @param numberOfMaps
     * @param odds
     * @return
     */
    ArrayList<Map> createNoise(int width, int height, int numberOfMaps, double odds);
}
