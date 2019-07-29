package Map.Rooms;

public class DefaultRoom extends AbstractRoom {
    public DefaultRoom(int roomWidth, int roomHeight) {
        super(roomWidth, roomHeight);
        this.roomName = "Room";

        for (int y = 0; y < roomHeight; y++) {
            for (int x = 0; x < roomWidth; x++) {
            }
        }


    }


    public String getRoomInfo() {

        return null;
    }
}
