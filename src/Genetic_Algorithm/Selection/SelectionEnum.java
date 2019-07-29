    package Genetic_Algorithm.Selection;

import Algorithms.Algorithms;
import Exceptions.VariableBoundsException;
import Map.GameMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public enum SelectionEnum implements ISelection {
    //TODO I was thinking a little a bout it and selection fraction cannot be  in the constructor as Id want
    // to be more flexible if needed, since its enum
    ELITE("elite"){
        @Override
        public ArrayList<GameMap> selectFitIndividuals(ArrayList<GameMap> list, double selectionFraction) {
            if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);

            //First elements are the most fit
            list.sort(Comparator.comparing(GameMap::getFitnessScore).reversed());
            list.subList((int)(list.size() * selectionFraction), list.size()).clear();

            list = Algorithms.deepClone(list);
            return list;
        }
    },
    ROULETTE("roulette"){
        @Override
        public ArrayList<GameMap> selectFitIndividuals(ArrayList<GameMap> list, double selectionFraction) {
            if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);
            //TODO i dont like limit variable
            double limit = list.size() * selectionFraction;
            Random random = new Random();

            int rouletteSum = 0;
            for (GameMap gameMap : list) rouletteSum += gameMap.getFitnessScore();


            list.sort(Comparator.comparing(GameMap::getFitnessScore).reversed());
            ArrayList<GameMap> selectedList = new ArrayList<>();

            int x = 0;
            int iterator = -1;
            while(selectedList.size() < limit){
                iterator++;
                x += random.nextInt(rouletteSum);
                if(x > rouletteSum){
                    GameMap selectedGameMap = list.get(iterator);
                    selectedGameMap = Algorithms.deepClone(selectedGameMap);

                    selectedList.add(selectedGameMap);
                    iterator = -1;
                    x = 0;
                }
            }

            return selectedList;
        }
    },
    //TODO its stochastic universal sampling
    StochasticTwo("stochastic_two"){
        @Override
        public ArrayList<GameMap> selectFitIndividuals(ArrayList<GameMap> list, double selectionFraction) {
            if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);

            return null;
        }
    },
    TOURNAMENT("tournament"){
        @Override
        public ArrayList<GameMap> selectFitIndividuals(ArrayList<GameMap> list, double selectionFraction) {
            if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);
            //TODO i dont like limit variable
            double limit = list.size() * selectionFraction;
            Random random = new Random();

            list.sort(Comparator.comparing(GameMap::getFitnessScore).reversed());
            ArrayList<GameMap> selectedList = new ArrayList<>();


            while(selectedList.size() < limit) {
                ArrayList<GameMap> tournamentList = new ArrayList<>();
                for (int i = 0; i < limit; i++) {
                    GameMap selectedGameMap = list.get(random.nextInt(list.size()));
                    tournamentList.add(selectedGameMap);
                }

                //TODO get top guy, i am not sure if this method is fast, it sorts entire arry, i just need to pull one object
                tournamentList.sort((Comparator.comparing(GameMap::getFitnessScore).reversed()));
                selectedList.add(Algorithms.deepClone(tournamentList.get(0)));
            }


            return selectedList;
        }
    },
    RANK("rank"){
        @Override
        public ArrayList<GameMap> selectFitIndividuals(ArrayList<GameMap> list, double selectionFraction) {
            if(selectionFraction < 0 || selectionFraction > 1) throw new VariableBoundsException(0, 1);

            return null;
        }
    };
    SelectionEnum(String implementationName){
        this.implementationName = implementationName;
    }

//    private double selectionFraction; //As how much of the generation is meant to survive
    private String implementationName;
    public String getImplementationName() {
        return implementationName;
    }
}
