package Genetic_Algorithm.Fitness;

import Algorithms.Algorithms;
import Algorithms.Matrix;
import Dungeon.*;
import Dungeon.Tile.Corridor;

import java.util.ArrayList;
//TODO here visit map 1 means visited and 0 means not visited, need to display it better
public enum FitnessEnum implements FitnessImp {
    IS_TRAVERSABLE(1) {


        @Override
        public int evaluateDungeon(Dungeon dungeon) {
            //TODO duh...


            return 0;
        }


    },
    FIND_ALL_ROOMS(0.7) {
        @Override
        public int evaluateDungeon(Dungeon dungeon) {
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

                    if (!(dungeon.getDungeonMatrix().getElement(x, y) == DungeonTiles.CORRIDOR.getX())) {
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

            wallCount = (dungeonWidth * dungeonHeight) - roomSize;
            dungeon.setScore((numberOfRooms * 20) + wallCount); //todo this way i set score so far
            double roomAverage = (double) roomSize / numberOfRooms;

            //TODO Return between 0 and 100 (0.0 to 1.0)

            //TODO for now I will only try to encourage creating multiple small rooms
            int mapSize = dungeonWidth + dungeonHeight;
            int sum = numberOfRooms * mapSize;
            float ccc = (float) (roomAverage * numberOfRooms); //More rooms * thier room size


            return (int) ccc;
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
