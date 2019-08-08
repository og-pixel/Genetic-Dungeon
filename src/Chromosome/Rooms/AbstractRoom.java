package Chromosome.Rooms;

import DataStructure.Matrix;

import java.util.Random;

import static Chromosome.TileList.CORRIDOR;

public abstract class AbstractRoom implements Room {
    String roomName;
    private int roomWidth;
    private int roomHeight;
    Matrix room;
    Random random;

    public AbstractRoom(int roomWidth, int roomHeight){
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        room = new Matrix(roomWidth, roomHeight);
        random = new Random();
    }

    public Matrix getRoomInfo() {
        return room;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        for (int y = 0; y < roomHeight; y++) {
            string.append(" {");
            for (int x = 0; x < roomWidth; x++) {
                string.append(room.getElement(x, y));
                if(x != roomWidth - 1)string.append(", ");
            }
            string.append("}\n");
        }
        return string.toString();
    }
}