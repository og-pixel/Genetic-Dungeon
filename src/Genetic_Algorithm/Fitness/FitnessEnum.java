package Genetic_Algorithm.Fitness;

import Algorithms.Algorithms;
import DataStructure.Matrix;
import Map.*;

import java.util.ArrayList;
import static Map.TileList.*;

public enum FitnessEnum implements FitnessImp {
    IS_TRAVERSABLE("is_traversable", 1) {
        @Override
        public void evaluateMap(GameMap gameMap) {
        }

        @Override
        public void evaluateMapCheap(GameMap gameMap) {
        }
    },
    FIND_ALL_ROOMS("find_all_rooms", 0.7) {
        //TODO I need to work on how visit gameMap works
        @Override
        public void evaluateMap(GameMap gameMap) {
            int dungeonWidth = gameMap.getMapWidth();
            int dungeonHeight = gameMap.getMapHeight();

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
                    if (!(gameMap.getMapMatrix().getElement(x, y) == CORRIDOR)) {
                        alreadyVisited = true;
                    }

                    if (!alreadyVisited) {
                        visitMap = Algorithms.floodFill(gameMap, x, y);
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

            //Cleanup, save information to the gameMap
            gameMap.setNumberOfRooms(numberOfRooms);

            //TODO for now I had to make so score cannot be negative
            //TODO Return between 0 and 100 (0.0 to 1.0)
            //TODO for now I will only try to encourage creating multiple small rooms
            if(score > 0) gameMap.setFitnessScore(score);
            else gameMap.setFitnessScore(score);
        }

        @Override
        public void evaluateMapCheap(GameMap gameMap) {

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

    FitnessEnum(String implementationName, double strength) {
        this.implementationName = implementationName;
        this.strength = strength;
    }
    private final String implementationName;
    private final double strength;
    public String getImplementationName(){
        return implementationName;
    }
}
