package Genetic_Algorithm.SelectionStrategy;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import GameMap.GameMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class RouletteSelectionStrategy implements SelectionStrategy {
    public static final String IMPLEMENTATION = "roulette";

    @Override
    public ArrayList<GameMap> selectFitIndividuals(ArrayList<GameMap> gameMap, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);
        //TODO i dont like limit variable
        double limit = gameMap.size() * selectionFraction;
        Random random = new Random();

        int rouletteSum = 0;
        //TODO there are gameMap and gameMaps due to my refractoring, CHANGE FFS
        for (GameMap gameMaps : gameMap) rouletteSum += gameMaps.getFitnessScore();


        gameMap.sort(Comparator.comparing(GameMap::getFitnessScore).reversed());
        ArrayList<GameMap> selectedList = new ArrayList<>();

        int x = 0;
        int iterator = -1;
        while(selectedList.size() < limit){
            iterator++;
            x += random.nextInt(rouletteSum);
            if(x > rouletteSum){
                GameMap selectedGameMap = gameMap.get(iterator);
                selectedGameMap = Algorithms.deepClone(selectedGameMap);

                selectedList.add(selectedGameMap);
                iterator = -1;
                x = 0;
            }
        }

        return selectedList;
    }
}
