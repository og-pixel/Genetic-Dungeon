package Chromosome.Rooms;

import DataStructure.Matrix;
import DataStructure.MutabilityMatrix;

import static Chromosome.TileList.CORRIDOR;
import static Chromosome.TileList.WALL;

public class DefaultRoom extends AbstractRoom {
    public DefaultRoom(int roomWidth, int roomHeight) {
        super(roomWidth, roomHeight);
        this.roomName = "Room";

//        for (int y = 0; y < roomHeight; y++) {
//            for (int x = 0; x < roomWidth; x++) {
//
//
//            }
//        }

//        long[] wall = new long[roomWidth];
//        for (int i = 0; i < roomWidth; i++) {
//            wall[i] = WALL;
//        }

        room.fillMatrix(CORRIDOR);

        for (int x = 0; x < roomWidth; x++) {
            room.put(x, 0, WALL);
            room.put(x, room.getHeight() - 1, WALL);
        }

        for (int y = 0; y < roomHeight; y++) {
            room.put(0, y, WALL);
            room.put(room.getWidth() - 1, y, WALL);
        }

        System.out.println(room);
        System.out.println();
    }
}
