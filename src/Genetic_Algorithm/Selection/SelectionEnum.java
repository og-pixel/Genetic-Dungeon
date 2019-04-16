    package Genetic_Algorithm.Selection;

import Algorithms.Algorithms;
import Map.Map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public enum SelectionEnum implements SelectionImp {
    ELITE{
        @Override
        public ArrayList<Map> selectFitIndividuals(ArrayList<Map> list) {

            //////TODO THIS PART IS SELECTION
            //First elements are the most fit
            list.sort(Comparator.comparing(Map::getFitnessScore).reversed());
            //TODO This part takes top 10% of pop and removes the rest
            list.subList((int)(list.size() * 0.1), list.size()).clear();

            list = Algorithms.deepClone(list);
            return list;
        }
    },
    ROULETTE{
        @Override
        public ArrayList<Map> selectFitIndividuals(ArrayList<Map> list) {
            //TODO i dont like limit variable
            double limit = list.size() * 0.1;
            Random random = new Random();

            int rouletteSum = 0;
            for (Map map : list) rouletteSum += map.getFitnessScore();


            list.sort(Comparator.comparing(Map::getFitnessScore).reversed());
            ArrayList<Map> selectedList = new ArrayList<>();

            int x = 0;
            int iterator = -1;
            while(selectedList.size() < limit){
                iterator++;
                x += random.nextInt(rouletteSum);
                if(x > rouletteSum){
                    Map selectedMap = list.get(iterator);
                    selectedMap = Algorithms.deepClone(selectedMap);

                    selectedList.add(selectedMap);
                    iterator = -1;
                    x = 0;
                }
            }

            return selectedList;
        }
    },
    //TODO its stochastic universal sampling
    StochasticTwo{
        @Override
        public ArrayList<Map> selectFitIndividuals(ArrayList<Map> list) {

            return null;
        }
    },
    TOURNAMENT{
        @Override
        public ArrayList<Map> selectFitIndividuals(ArrayList<Map> list) {
            //TODO i dont like limit variable
            double limit = list.size() * 0.1;
            Random random = new Random();

            list.sort(Comparator.comparing(Map::getFitnessScore).reversed());
            ArrayList<Map> selectedList = new ArrayList<>();


            while(selectedList.size() < limit) {
                ArrayList<Map> tournamentList = new ArrayList<>();
                for (int i = 0; i < limit; i++) {
                    Map selectedMap = list.get(random.nextInt(list.size()));
                    tournamentList.add(selectedMap);
                }

                //TODO get top guy, i am not sure if this method is fast, it sorts entire arry, i just need to pull one object
                tournamentList.sort((Comparator.comparing(Map::getFitnessScore).reversed()));
                selectedList.add(Algorithms.deepClone(tournamentList.get(0)));
            }


            return selectedList;
        }
    },
    RANK{
        @Override
        public ArrayList<Map> selectFitIndividuals(ArrayList<Map> list) {

            return null;
        }
    },
}
