package Genetic_Algorithm.Selection;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import Map.GameMap;

import java.util.ArrayList;
import java.util.Comparator;

public class EliteSelectionStrategy implements SelectionStrategy {
    public static final String IMPLEMENTATION = "elite";

    @Override
    public ArrayList<GameMap> selectFitIndividuals(ArrayList<GameMap> gameMap, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);

        //First elements are the most fit
        gameMap.sort(Comparator.comparing(GameMap::getFitnessScore).reversed());
        gameMap.subList((int)(gameMap.size() * selectionFraction), gameMap.size()).clear();

        gameMap = Algorithms.deepClone(gameMap);
        return gameMap;
    }
}
