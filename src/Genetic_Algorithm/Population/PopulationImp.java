package Genetic_Algorithm.Population;

import Dungeon.Dungeon;

import java.util.ArrayList;

public interface PopulationImp {
    ArrayList<Dungeon> createPopulation(int width, int height, int numberOfMaps, double odds);
}
