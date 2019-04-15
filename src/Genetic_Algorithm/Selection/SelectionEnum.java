    package Genetic_Algorithm.Selection;

import Algorithms.Algorithms;
import Dungeon.Dungeon;
import com.sun.javafx.scene.traversal.Algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

//TODO https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_parent_selection.htm
public enum SelectionEnum implements SelectionImp {
    ELITE{
        @Override
        public ArrayList<Dungeon> selectFitIndividuals(ArrayList<Dungeon> list) {

            //////TODO THIS PART IS SELECTION
            //First elements are the most fit
            list.sort(Comparator.comparing(Dungeon::getScore).reversed());
            //TODO This part takes top 10% of pop and removes the rest
            list.subList((int)(list.size() * 0.1), list.size()).clear();

            list = Algorithms.deepClone(list);
            return list;
        }
    },
    ROULETTE{
        @Override
        public ArrayList<Dungeon> selectFitIndividuals(ArrayList<Dungeon> list) {
            //TODO i dont like limit variable
            double limit = list.size() * 0.1;
            Random random = new Random();

            int rouletteSum = 0;
            for (Dungeon dungeon : list) rouletteSum += dungeon.getScore();


            list.sort(Comparator.comparing(Dungeon::getScore).reversed());
            ArrayList<Dungeon> selectedList = new ArrayList<>();

            int x = 0;
            int iterator = -1;
            while(selectedList.size() < limit){
                iterator++;
                x += random.nextInt(rouletteSum);
                if(x > rouletteSum){
                    Dungeon selectedMap = list.get(iterator);
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
        public ArrayList<Dungeon> selectFitIndividuals(ArrayList<Dungeon> list) {

            return null;
        }
    },
    Tournament{
        @Override
        public ArrayList<Dungeon> selectFitIndividuals(ArrayList<Dungeon> list) {
            //TODO i dont like limit variable
            double limit = list.size() * 0.1;
            Random random = new Random();

            list.sort(Comparator.comparing(Dungeon::getScore).reversed());
            ArrayList<Dungeon> selectedList = new ArrayList<>();


            while(selectedList.size() < limit) {
                ArrayList<Dungeon> tournamentList = new ArrayList<>();
                for (int i = 0; i < limit; i++) {
                    Dungeon selectedMap = list.get(random.nextInt(list.size()));
                    tournamentList.add(selectedMap);
                }

                //TODO get top guy, i am not sure if this method is fast, it sorts entire arry, i just need to pull one object
                tournamentList.sort((Comparator.comparing(Dungeon::getScore).reversed()));
                selectedList.add(Algorithms.deepClone(tournamentList.get(0)));
            }


            return selectedList;
        }
    },
    Rank{
        @Override
        public ArrayList<Dungeon> selectFitIndividuals(ArrayList<Dungeon> list) {

            return null;
        }
    },
    //TODO age doesnt work
    Age{
        @Override
        public ArrayList<Dungeon> selectFitIndividuals(ArrayList<Dungeon> list) {
            Random random = new Random(); //lol

            ArrayList<Dungeon> rest = new ArrayList<>();

            for (Dungeon dungeon : list) {
                if (dungeon.getAge() < 3) { //lol random deadth for everyone
                    dungeon.setAge(dungeon.getAge() + 1);
                    rest.add(dungeon);
                }
            }
            rest.sort((Comparator.comparing(Dungeon::getScore).reversed()));


            return rest;
        }
    }
}
