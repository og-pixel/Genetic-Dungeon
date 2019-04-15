package Genetic_Algorithm.Offspring;

import Algorithms.Algorithms;
import Dungeon.Dungeon;

import java.util.ArrayList;
import java.util.Random;

public enum OffspringEnum implements OffspringImp {
    DEFAULT{
        @Override
        public ArrayList<Dungeon> createNewGeneration(ArrayList<Dungeon> list, double popSize, double topPop) {
            Random random = new Random();
            ArrayList<Dungeon> newPopulation = new ArrayList<>();

            //TODO for now pop size is as big as the list that enters
            double POP_SIZE = popSize;
            double TOP_POP = topPop;

            while (newPopulation.size() < POP_SIZE) {
                //THIS IS premutation part
//                premutation.premutateDungeons(mapList);

                //THIS is mutation part
                int randomPick = random.nextInt((int) TOP_POP);
                Dungeon parent1 = list.get(randomPick);

                randomPick = random.nextInt((int) TOP_POP);
                Dungeon parent2 = list.get(randomPick);

                Dungeon child1 = Algorithms.deepClone(parent1);
                Dungeon child2 = Algorithms.deepClone(parent2);

                int crossPointX = random.nextInt(parent1.getDungeonWidth());
                int crossPointY = random.nextInt(parent1.getDungeonHeight());


                for (int y = 0; y < crossPointY - 1; y++) {
                    child1.getDungeonMatrix().replaceRow(y, child2.getDungeonMatrix().getRow(y));
                }
                for (int x = 0; x < crossPointX; x++) {
                    child1.getDungeonMatrix().put(x, crossPointY, child2.getDungeonMatrix().getElement(x, crossPointY));
                }




                for (int x = crossPointX; x < parent1.getDungeonMatrix().getWidth(); x++) {
                    child2.getDungeonMatrix().put(x, crossPointY, child1.getDungeonMatrix().getElement(x, crossPointY));
                }

                for (int y = crossPointY + 1; y < parent1.getDungeonHeight(); y++) {
                    child2.getDungeonMatrix().replaceRow(y, child1.getDungeonMatrix().getRow(y));
                }

//                for (int i = 0; i < parent1.getDungeonHeight()/2; i++) {
//                    child1.getDungeonMatrix().replaceRow(i, parent2.getDungeonMatrix().getRow(i));
//                }
//
//                for (int i = parent2.getDungeonHeight()/2; i < parent2.getDungeonHeight(); i++) {
//                    child2.getDungeonMatrix().replaceRow(i, parent1.getDungeonMatrix().getRow(i));
//                }

                newPopulation.add(child1);
                newPopulation.add(child2);
            }



            return newPopulation;
        }
    },
    DASD{
        @Override
        public ArrayList<Dungeon> createNewGeneration(ArrayList<Dungeon> list, double popSize, double topPop) {
            return null;
        }
    };

}
