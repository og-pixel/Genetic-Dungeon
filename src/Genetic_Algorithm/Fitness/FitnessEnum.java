package Genetic_Algorithm.Fitness;

import Algorithms.Algorithms;
import Algorithms.Matrix;
import Map.*;

import java.util.ArrayList;
public enum FitnessEnum implements FitnessImp, TileList {
    IS_TRAVERSABLE(1) {
        @Override
        public void evaluateMap(Map map) {
        }

        @Override
        public void evaluateMapCheap(Map map) {
        }
    },
    FIND_ALL_ROOMS(0.7) {
        //TODO I need to work on how visit map works
        @Override
        public void evaluateMap(Map map) {
            int dungeonWidth = map.getMapWidth();
            int dungeonHeight = map.getMapHeight();

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
                    if (!(map.getMapMatrix().getElement(x, y) == CORRIDOR)) {
                        alreadyVisited = true;
                    }

                    if (!alreadyVisited) {
                        visitMap = Algorithms.floodFill(map, x, y);
                        listOfRooms.add(visitMap);

                        numberOfRooms++;
                        roomSize = roomSize + countVisited(visitMap);
                    }
                }
            }


            wallCount = (dungeonWidth * dungeonHeight) - roomSize;
            double roomAverage = (double) roomSize / numberOfRooms;
            int mapSize = dungeonWidth + dungeonHeight;


            double distance = roomSize - wallCount;
            if(numberOfRooms < 1)numberOfRooms = 1;

            double score = (distance / numberOfRooms); //More rooms * thier room size

            //Cleanup, save information to the map
            map.setNumberOfRooms(numberOfRooms);

            //TODO for now I had to make so score cannot be negative
            //TODO Return between 0 and 100 (0.0 to 1.0)
            //TODO for now I will only try to encourage creating multiple small rooms
            if(score > 0) map.setFitnessScore(score);
            else map.setFitnessScore(score);

            //TODO here i will save map stats //            map.setNumberOfRooms(numberOfRooms);
        }

        @Override
        public void evaluateMapCheap(Map map) {

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

    private final double strength;

    FitnessEnum(double strength) {
        this.strength = strength;
    }

    FitnessEnum(){
        this.strength = -1;//TODO if its irrelevant, I'll just make it negative, but it might be necessary for all of them anyway
    }
}
