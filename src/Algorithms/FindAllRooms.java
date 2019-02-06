package Algorithms;

import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;

import java.util.ArrayList;
import java.util.HashMap;

//todo this class name should not be a verb but I feel like its better than "RoomSize"
public class FindAllRooms implements Fitness{

    /**
     * Takes a dungeon map and tries to use a fill algorithm to find all rooms and their respective sizes
     * Score is counted based on todo "Up to 100?"
     * @param dungeon dungeon map
     */
    @Override
    public void evaluateDungeon(Dungeon dungeon) {
        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();

        int roomSize = 0;
        int roomCount = 0;
        int wallCount = 0;

        HashMap<Integer, ArrayList<ArrayList<Boolean>>> xz = new HashMap<>();
        ArrayList<ArrayList<ArrayList<Boolean>>> xzc = new ArrayList<>();

        ArrayList<ArrayList<Boolean>> visitMap;
        ArrayList<ArrayList<Boolean>> wholeVisitMap = Matrix.createMatrix(dungeonWidth, dungeonHeight, false);

        for(int y = 0; y < dungeonHeight; y++){
            for(int x = 0; x < dungeonWidth; x++) {


//                if (!wholeVisitMap.get(y).get(x)) {
                    if (dungeon.getDungeonMatrix().get(y).get(x).getTile() instanceof Corridor) {
                        visitMap = Algorithms.floodFill(dungeon, x, y);

//                        //todo this is a very stupid workaround atm
//                        for(int yPos = 0; yPos < dungeonHeight; yPos++){
//                            for(int xPos = 0; xPos < dungeonWidth; xPos++){
//                                //todo it only triggets if they are different (it means change)
//                                if(wholeVisitMap.get(yPos).get(xPos) != visitMap.get(yPos).get(xPos)){
//                                    wholeVisitMap.get(yPos).set(xPos, true);
//                                }
//                            }
//                        }

//                        wholeVisitMap = visitMap;




                        roomSize = roomSize + countVisited(visitMap);
                        wallCount = (dungeonWidth * dungeonHeight) - roomSize;
                        //Currently score is given by calculating ratio of wall to corridor (so 50
                        dungeon.setScore(roomCount * 10 + roomSize - wallCount); //todo this way i set score so far
                    }
                }
            }
        }
//    }


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
                if (aBoolean) System.out.print(" ");
                else System.out.print("░");
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