package Map.Rooms;

import DataStructure.Matrix;

public abstract class AbstractRoom implements IRoom {
    String roomName;
    int roomWidth;
    int roomHeight;
    Matrix room;

    public AbstractRoom(int roomWidth, int roomHeight){
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        room = new Matrix(roomWidth, roomHeight);
        room.fillMatrix(0);

    }

    public String getRoomInfo() {

        return null;
    }

}