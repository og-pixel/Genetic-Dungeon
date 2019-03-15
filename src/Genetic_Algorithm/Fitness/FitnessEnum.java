package Genetic_Algorithm.Fitness;

import Algorithms.Algorithms;
import Algorithms.Matrix;
import Dungeon.*;
import Dungeon.Tile.Corridor;

import java.util.ArrayList;
//TODO here visit map 1 means visited and 0 means not visited, need to display it better
public enum FitnessEnum implements FitnessImp, TileList {
    IS_TRAVERSABLE(1) {


        @Override
        public double evaluateDungeon(Dungeon dungeon) {
            //TODO duh...


            return -1;
        }


    },
    FIND_ALL_ROOMS(0.7) {
        @Override
        public double evaluateDungeon(Dungeon dungeon) {
            int dungeonWidth = dungeon.getDungeonWidth();
            int dungeonHeight = dungeon.getDungeonHeight();

            int roomSize = 0;
            int numberOfRooms = 0;
            int wallCount = 0;

            ArrayList<Matrix> listOfRooms = new ArrayList<>();
            Matrix visitMap = new Matrix(dungeonWidth, dungeonHeight);

            visitMap.fillMatrix(0);

            for (int y = 0; y < dungeonHeight; y++) {
                for (int x = 0; x < dungeonWidth; x++) {

                    boolean alreadyVisited = false;
                    if (!listOfRooms.isEmpty()) {
                        for (Matrix listOfRoom : listOfRooms) {
                            if (listOfRoom.getElement(x, y) == 1) {
                                alreadyVisited = true;
                                break;
                            }
                        }
                    }

                    if (!(dungeon.getDungeonMatrix().getElement(x, y) == CORRIDOR)) {
                        alreadyVisited = true;
                    }

                    if (!alreadyVisited) {
                        visitMap = Algorithms.floodFill(dungeon, x, y);
                        listOfRooms.add(visitMap);

                        numberOfRooms++;
                        roomSize = roomSize + countVisited(visitMap);
                    }
                }
            }

//            dungeon.setScore((numberOfRooms * 20) + wallCount); //todo this way i set score so far
            //TODO Return between 0 and 100 (0.0 to 1.0)
            //TODO for now I will only try to encourage creating multiple small rooms

            wallCount = (dungeonWidth * dungeonHeight) - roomSize;
            double roomAverage = (double) roomSize / numberOfRooms;
            int mapSize = dungeonWidth + dungeonHeight;


            double distance = roomSize - wallCount;
            if(numberOfRooms < 1)numberOfRooms = 1;
            //TODo i want it strike balance 50/50 by this calculation, so the biggerst score would be if these values were closest to each other
            double score = (distance / numberOfRooms); //More rooms * thier room size


            dungeon.setNumberOfRooms(numberOfRooms);

            dungeon.setScore(score);



            ///CLEANUP
            //TODO here i will save dungeon stats //            dungeon.setNumberOfRooms(numberOfRooms);
            return score;
        }

        private int countVisited(Matrix visitMap) {
            int counter = 0;
            for (int y = 0; y < visitMap.getHeight(); y++) {
                for (int x = 0; x < visitMap.getWidth(); x++) {
                    if (visitMap.getElement(x, y) == 1) counter++;
                }
            }
            return counter;
        }
    };


    private int roomCount(Dungeon dungeon){

        int dungeonWidth = dungeon.getDungeonWidth();
        int dungeonHeight = dungeon.getDungeonHeight();

//        int[][] shape = new int[6][5];{{WALL, WALL, WALL, WALL, WALL;{
//        WALL, WALL, WALL, WALL, WALL}
//        }








        return -1;
    }



    private final double strength;

    FitnessEnum(double strength) {
        this.strength = strength;
    }

    FitnessEnum(){
        this.strength = -1;//TODO if its irrelevant, I'll just make it negative, but it might be necessary for all of them anyway
    }


}
