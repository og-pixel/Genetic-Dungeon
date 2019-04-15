package Genetic_Algorithm.Premutation;

import Dungeon.Dungeon;

import java.util.ArrayList;
import java.util.Random;

public enum PremutationEnum implements PremutationImp{
    SWAP{
        @Override
        public void premutateDungeon(Dungeon dungeon){
            Random random = new Random();
            int dungeonHeight = dungeon.getDungeonHeight();
            int dungeonWidth = dungeon.getDungeonWidth();
            int x1;
            int y1;
            int x2;
            int y2;
            //While it is a loop, it is meant to be run once 99% ot the time
            do {
                x1 = random.nextInt(dungeonWidth);
                y1 = random.nextInt(dungeonHeight);

                x2 = random.nextInt(dungeonWidth);
                y2 = random.nextInt(dungeonHeight);
            }while (x1 != x2 || y1 != y2);

            if(!dungeon.getDungeonMatrix().swapElements(x1, y1, x2, y2)) System.out.println("Error");;
        }
    },
    SCRAMBLE{
        @Override
        public void premutateDungeon(Dungeon dungeon) {

        }
    },
    INVERSION{
        @Override
        public void premutateDungeon(Dungeon dungeon) {

        }
    };


    //Just run the same method for the whole arry
    @Override
    public void premutateDungeons(ArrayList<Dungeon> dungeonList) {
        for (Dungeon dungeon : dungeonList) {
            premutateDungeon(dungeon);
        }
    }
}
