package Algorithms;

import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import com.sun.javafx.scene.traversal.Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//todo this class name should not be a verb but I feel like its better than "RoomSize"
public class FindAllRooms implements Fitness{

    /**
     * Takes a dungeon map and tries to use a fill algorithm to find all rooms and their respective sizes
     * Score is counted based on todo "Up to 100?"
     * @param dungeon dungeon map
     */
    //todo I think it no longer iterates over rooms that were discovered
    @Override
    public void evaluateDungeon(Dungeon dungeon) {
        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();

        int roomSize = 0;
        int numberOfRooms = 0;
        int wallCount = 0;
        int counter = 0;

        ArrayList<ArrayList<ArrayList<Boolean>>> listOfRooms = new ArrayList<>();
//        listOfRooms.add(new ArrayList<>());

        ArrayList<ArrayList<Boolean>> visitMap = Matrix.createMatrix(dungeonWidth, dungeonHeight, false);


        for(int y = 0; y < dungeonHeight; y++){
            for(int x = 0; x < dungeonWidth; x++) {
            if (dungeon.getDungeonMatrix().get(y).get(x).getTile() instanceof Corridor) {

//                if(listOfRooms.isEmpty()) {
//                    listOfRooms.add(visitMap);
//                    for (int a = 0; a < listOfRooms.size(); a++) {
//
//                        boolean tile = false;
//                        boolean secondTile = false;
//
//                        tile = visitMap.get(y).get(x);
//                        secondTile = listOfRooms.get(a).get(y).get(x);
//
//
//                        if (tile && secondTile) {
////                            numberOfRooms--;
//                            listOfRooms.remove(listOfRooms.size() - 1);
//                            break;
//                        }
//                    }
//                }

                try {
                    if (!visitMap.get(y).get(x)) {
                        visitMap = Algorithms.floodFill(dungeon, x, y);
                        if (visitMap != null) { //todo I think its always not null now so i can remove it
                            numberOfRooms++;
                            printMap(visitMap);
                            roomSize = roomSize + countVisited(visitMap);
                            listOfRooms.add(visitMap);//todo here
                        }
                    }
                } catch (Exception e) {
                    System.err.println("erros"); //todo i might not need that too
                    visitMap = Algorithms.floodFill(dungeon, x, y);
                }




            }
        }
    }


        wallCount = (dungeonWidth * dungeonHeight) - roomSize;
        //Currently score is given by calculating ratio of wall to corridor (so 50
//                            dungeon.setScore((numberOfRooms * 20) + wallCount); //todo this way i set score so far
        System.out.println("Room Count: " + numberOfRooms);
        System.out.println("Walls: " + wallCount);
        System.out.println("Rooms Take: " + roomSize);
//                            System.out.println("Total Score: " + (numberOfRooms * 20) + wallCount);
        System.out.println("*******************************************");
        System.out.println("Score: " + dungeon.getScore());
}

    private void function(){


    }


    /**
     * Check if a room was "discovered" already
     * @return
     */
    private boolean checkIfAlreadyFlooded(int x, int y){


        return false;
    }

    private int countVisited(ArrayList<ArrayList<Boolean>> visitMap){
        int counter = 0;
        for (ArrayList<Boolean> booleans : visitMap) {
            for (Boolean aBoolean : booleans) {
                if (aBoolean) counter++;
            }
        }
        return counter;
    }
    public void printMap(ArrayList<ArrayList<Boolean>> visitMap){
        for (ArrayList<Boolean> booleans : visitMap) {
            System.out.println();
            for (Boolean aBoolean : booleans) {
                if (aBoolean) System.out.print("░");
                else System.out.print("x");
            }
        }
        System.out.println();
        System.out.println("****************************");
    }
//    public void printMap(ArrayList<ArrayList<Boolean>> visitMap){
//        for(int y = 0; y < visitMap.size(); y++){
//            System.out.println();
//            for(int x = 0; x < visitMap.get(y).size(); x++){
//                if(visitMap.get(y).get(x)) System.out.print(" ");
//                    else System.out.print("░");
//            }
//        }
//        System.out.println();
//        System.out.println("****************************");
//    }
}