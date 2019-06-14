package Genetic_Algorithm.Premutation;

import Map.Map;

import java.util.ArrayList;
import java.util.Random;

public enum PremutationEnum implements PremutationImp{
    //todo maybe add something like "do swap x times"
    SWAP("swap"){
        @Override
        public void premutateDungeon(Map map){
            Random random = new Random();
            int dungeonHeight = map.getMapHeight();
            int dungeonWidth = map.getMapWidth();
            int x1 ,y1;
            int x2, y2;
            //While it is a loop, it is meant to be run once 99% ot the time
            do {
                x1 = random.nextInt(dungeonWidth);
                y1 = random.nextInt(dungeonHeight);

                x2 = random.nextInt(dungeonWidth);
                y2 = random.nextInt(dungeonHeight);
            }while (x1 != x2 || y1 != y2);

            if(!map.getMapMatrix().swapElements(x1, y1, x2, y2)) System.out.println("Error");
        }
    },
    SCRAMBLE("scramble"){
        @Override
        public void premutateDungeon(Map map) {

        }
    },
    INVERSION("inversion"){
        @Override
        public void premutateDungeon(Map map) {

        }
    };


    //Just run the same method for the whole arry
    @Override
    public void premutateDungeons(ArrayList<Map> mapList) {
        for (Map map : mapList) {
            premutateDungeon(map);
        }
    };
    PremutationEnum(String implementationName){
        this.implementationName = implementationName;
    }
    private String implementationName;
    public String getImplementationName() {
        return implementationName;
    }
}
