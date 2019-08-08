package Genetic_Algorithm.CorrectionStrategy;

import Chromosome.Rooms.*;
import Chromosome.*;
import DataStructure.Matrix;
import DataStructure.MutabilityMatrix;

import java.util.ArrayList;
import java.util.Random;

import static Chromosome.TileList.WALL;

public class AddPermanentRoomsStrategy implements CorrectionStrategy {

    public static final String IMPLEMENTATION = "add_permanent_rooms";

    private int count;
    private ArrayList<Room> roomList;
    private Random random;


    //TODO low and high might be something to tweak yourself
    public AddPermanentRoomsStrategy(int count){
        this.count = count;
        roomList = new ArrayList<>();
        random = new Random();

        int low = 4;
        int high = 10;
        int result;
        int result2;

        result = random.nextInt(high - low) + low;
        result2 = random.nextInt(high - low) + low;
        roomList.add(new AltarRoom(result, result2));

        for (int i = 0; i < count; i++) {
            result = random.nextInt(high - low) + low;
            result2 = random.nextInt(high - low) + low;

            roomList.add(new DefaultRoom(result, result2));
        }
    }

    @Override
    public void correctMap(Chromosome chromosome) {
        MutabilityMatrix matrix = (MutabilityMatrix) chromosome.getMapMatrix();

        for (Room room : roomList) {
            Matrix roomMatrix = room.getRoomInfo();

            int pickX = random.nextInt(matrix.getWidth());
            int pickY = random.nextInt(matrix.getHeight());


            if (matrix.fillMatrixArea(pickX, pickY, roomMatrix)) {
                matrix.makeAreaImmutable(pickX, pickY, roomMatrix);
            }
        }
    }
}
