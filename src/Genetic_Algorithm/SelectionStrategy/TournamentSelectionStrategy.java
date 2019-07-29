package Genetic_Algorithm.SelectionStrategy;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import GameMap.GameMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class TournamentSelectionStrategy implements SelectionStrategy {
    public static final String IMPLEMENTATION = "tournament";

    @Override
    public ArrayList<GameMap> selectFitIndividuals(ArrayList<GameMap> gameMap, double selectionFraction) {
        if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);
        //TODO i dont like limit variable
        double limit = gameMap.size() * selectionFraction;
        Random random = new Random();

        gameMap.sort(Comparator.comparing(GameMap::getFitnessScore).reversed());
        ArrayList<GameMap> selectedList = new ArrayList<>();


        while(selectedList.size() < limit) {
            ArrayList<GameMap> tournamentList = new ArrayList<>();
            for (int i = 0; i < limit; i++) {
                GameMap selectedGameMap = gameMap.get(random.nextInt(gameMap.size()));
                tournamentList.add(selectedGameMap);
            }

            //TODO get top guy, i am not sure if this method is fast, it sorts entire arry, i just need to pull one object
            tournamentList.sort((Comparator.comparing(GameMap::getFitnessScore).reversed()));
            selectedList.add(Algorithms.deepClone(tournamentList.get(0)));
        }

        //TODO remove if wrong
        gameMap = selectedList;

        return selectedList;
    }
}
