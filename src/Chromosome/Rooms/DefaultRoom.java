package Chromosome.Rooms;


import static Chromosome.TileList.CORRIDOR;
import static Chromosome.TileList.WALL;

public class DefaultRoom extends AbstractRoom {
    public DefaultRoom(int roomWidth, int roomHeight) {
        super(roomWidth, roomHeight);
        this.roomName = "Room";
        room.fillMatrix(CORRIDOR);

        for (int x = 0; x < roomWidth; x++) {
            room.put(x, 0, WALL);
            room.put(x, room.getHeight() - 1, WALL);
        }

        for (int y = 0; y < roomHeight; y++) {
            room.put(0, y, WALL);
            room.put(room.getWidth() - 1, y, WALL);
        }


        //TODO putting doors is too confusing
        int low;
        int noDoors = 0;
        int doorPosition;
        int high = 1;
        // 0 = top
        // 1 = right
        // 2 = bottom
        // 3 = left
        while(noDoors < 2) {
            doorPosition = random.nextInt(4);


            if(doorPosition == 0){
                low = 1;
                high = roomWidth - 1;
                room.put(random.nextInt(high - low) + low, 0, CORRIDOR);
                noDoors++;
            }
            else if(doorPosition == 1){
                low = 1;
                high = roomHeight - 1;
                room.put(roomWidth - 1, random.nextInt(high - low) + low, CORRIDOR);
                noDoors++;
            }
            else if(doorPosition == 2){
                low = 1;
                high = roomWidth - 1;
                room.put(random.nextInt(high - low) + low, roomHeight - 1, CORRIDOR);
                noDoors++;
            }
            else if(doorPosition == 3){
                low = 1;
                high = roomHeight - 1;
                room.put(0, random.nextInt(high - low) + low, CORRIDOR);
                noDoors++;
            }
        }
    }

}
