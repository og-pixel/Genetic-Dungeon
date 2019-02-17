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

        ArrayList<Matrix<Boolean>> listOfRooms = new ArrayList<>();

        Matrix<Boolean> visitMap = new Matrix<Boolean>(dungeonWidth, dungeonHeight);
        visitMap.fillMatrix(false);

        for(int y = 0; y < dungeonHeight; y++){
            for(int x = 0; x < dungeonWidth; x++) {
//                if (dungeon.getDungeonMatrix().getElement(x, y) instanceof Corridor) {
//                    for(int p = 0; p < listOfRooms.size(); p++){
//                        if(listOfRooms.get(p).getElement(x, y) && visitMap.getElement(x, y)){
//                            break;
//                        }
//                    }
//
//                    try {
//                        if (!visitMap.getElement(x,y)) {
//                            visitMap = Algorithms.floodFill(dungeon, x, y);
//                            if (visitMap != null) { //todo I think its always not null now so i can remove it
//                                numberOfRooms++;
//                                printMap(visitMap);
//                                roomSize = roomSize + countVisited(visitMap);
//                                listOfRooms.add(visitMap);//todo here
//                            }
//                        }
//                    } catch (Exception e) {
//                        System.err.println("erros"); //todo i might not need that too
//                        visitMap = Algorithms.floodFill(dungeon, x, y);
//                    }
//                }

               boolean alreadyVisited = false;
               if(!listOfRooms.isEmpty()) {
                   for(int p = 0; p < listOfRooms.size(); p++) {
                       if (listOfRooms.get(p).getElement(x, y)) {
                           alreadyVisited = true;
                           break;
                       }
                   }
               }

               if(!(dungeon.getDungeonMatrix().getElement(x,y).getTile() instanceof Corridor)){
                  alreadyVisited = true;
               }


               if(!alreadyVisited){
                   visitMap = Algorithms.floodFill(dungeon, x, y);
                   listOfRooms.add(visitMap);

                   numberOfRooms++;
//                   printMap(visitMap);
                   roomSize = roomSize + countVisited(visitMap);
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

    private int countVisited(Matrix<Boolean> visitMap){
        int counter = 0;
        for(int y = 0; y < visitMap.getHeight(); y++){
            for(int x = 0; x < visitMap.getWidth(); x++){
                if(visitMap.getElement(x,y)) counter++;
            }
        }
        return counter;
    }

    public void printMap(Matrix<Boolean> visitMap){
        for (int y = 0; y < visitMap.getHeight(); y++) {
            System.out.println();
            for (int x = 0; x < visitMap.getWidth(); x++) {
                if (visitMap.getElement(x,y)) System.out.print("░");
                else System.out.print("x");
            }
        }
        System.out.println();
        System.out.println("****************************");
    }
}
