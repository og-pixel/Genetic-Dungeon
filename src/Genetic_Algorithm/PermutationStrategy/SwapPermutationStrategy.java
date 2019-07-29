package Genetic_Algorithm.PermutationStrategy;

import GameMap.GameMap;

import java.util.ArrayList;
import java.util.Random;

public class SwapPermutationStrategy implements PermutationStrategy {
    public static final String IMPLEMENTATION = "swap";

    @Override
    public void permutateDungeon(GameMap gameMap) {
        Random random = new Random();
        int dungeonHeight = gameMap.getMapHeight();
        int dungeonWidth = gameMap.getMapWidth();
        int x1 ,y1;
        int x2, y2;
        //While it is a loop, it is meant to be run once 99% ot the time
        do {
            x1 = random.nextInt(dungeonWidth);
            y1 = random.nextInt(dungeonHeight);

            x2 = random.nextInt(dungeonWidth);
            y2 = random.nextInt(dungeonHeight);
        }while (x1 != x2 || y1 != y2);

        if(!gameMap.getMapMatrix().swapElements(x1, y1, x2, y2)) System.out.println("Error");
    }

    @Override
    public void permutateDungeons(ArrayList<GameMap> gameMapList) {
        for (GameMap gameMap : gameMapList) {
            permutateDungeon(gameMap);
        }
    }
}
