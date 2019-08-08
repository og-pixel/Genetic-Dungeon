package Chromosome.Rooms;

import static Chromosome.TileList.*;

public class AltarRoom extends AbstractRoom {
    public AltarRoom(int roomWidth, int roomHeight) {
        super(roomWidth, roomHeight);
        this.roomName = "Altar Room";

        room.fillMatrix(CORRIDOR);


        //TODO whole thing is a little bit too confusing
        for (int x = 0; x < roomWidth; x++) {
            room.put(x, 0, WALL);
            room.put(x, room.getHeight() - 1, WALL);
        }

        for (int y = 0; y < roomHeight; y++) {
            room.put(0, y, WALL);
            room.put(room.getWidth() - 1, y, WALL);
        }

        /////
        int highX = roomWidth - 1;
        int highY = roomHeight - 1;
        int low = 1;

        int resultX;
        int resultY;

        resultX = random.nextInt(highX - low) + low;
        resultY = random.nextInt(highY - low) + low;

        room.put(resultX, resultY, SPECIAL);
        /////

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
