package Dungeon_GA;

import java.util.ArrayList;
import java.util.Random;

public class Dungeon {

    private ArrayList<ArrayList<String>> mapMatrix;
    private static Random random = new Random();

    private int dungeonWidth;
    private int dungeonHeight;

    public Dungeon(int width, int height){
        mapMatrix = new ArrayList<>();
        for(int yAxis = 0; yAxis < height; yAxis++){
            mapMatrix.add(new ArrayList<>());
            for(int xAxis = 0; xAxis < width; xAxis++){
                if(random.nextFloat() < 0.65){
                    mapMatrix.get(yAxis).add(" ");
                }else {
                    mapMatrix.get(yAxis).add("X");
                }
            }
        }

        dungeonHeight = mapMatrix.size();
        dungeonWidth = mapMatrix.get(0).size();


        System.out.println("Dungeon_GA.Dungeon_GA width is: " + dungeonWidth);
        System.out.println("Dungeon_GA.Dungeon_GA height is: " + dungeonHeight);
    }


    public void test(){
        for(int x = 0; x < mapMatrix.size(); x++){
            for(int y = 0; y < mapMatrix.get(x).size(); y++){
                System.out.print(mapMatrix.get(x).get(y));
            }
            System.out.println();
        }
    }



    public int getDungeonWidth() {
        return dungeonWidth;
    }

    public int getDungeonHeight() {
        return dungeonHeight;
    }
}
