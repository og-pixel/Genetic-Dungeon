package Dungeon;

public class Dungeon {
    DungeonGenerator dungeonGenerator;

    public int heroLocationX, heroLocationY; //todo to remove, its for testing :)

    /* Variables */
    public int dungeonWidth, dungeonHeight;    //Size of the dungeonMatrix
    private int limitCorner;            //Strange value to limit dead ends

    /* Constants */
    private int DICE_ROLL_ODDS = 65; //todo it was final
    private int MINIMUM_CORRIDOR_LENGTH = 10; //todo it was final

    //todo this one will take more parameters to create a dungeonGenerator right away
    /**
     * New Constructor
     * @param dungeonWidth
     * @param dungeonHeight
     */
    public Dungeon(int dungeonWidth, int dungeonHeight, int noOfRooms, int maxRoomSize, int diceRoll, int minimumCorridorLength) {
        if (diceRoll < 1 || diceRoll > 100) throw new VariableBoundsIncorrect(1,100);

        DICE_ROLL_ODDS = diceRoll;
        MINIMUM_CORRIDOR_LENGTH = minimumCorridorLength;

        float currentTime = System.nanoTime();
        this.dungeonWidth = dungeonWidth;
        this.dungeonHeight = dungeonHeight;
        dungeonMatrix = new ArrayList<>();
        for (int yAxis = 0; yAxis < dungeonHeight; yAxis++) {
            dungeonMatrix.add(new ArrayList<>()); //Create new dungeonWidth in the matrix
            for (int xAxis = 0; xAxis < dungeonWidth; xAxis++) {
                dungeonMatrix.get(yAxis).add(new EmptyTile(xAxis, yAxis)); //Populate Dungeon.Tile Column
            }
        }
        generateRooms(noOfRooms, maxRoomSize);
        createMaze();
        removeSmallRegions();
        removeTeeth();
        generateDoors(1);
        fillEmptyTiles();
//        removeCorner(15);


        for(int y = 0; y < dungeonHeight; y++){
            for(int x = 0; x < dungeonWidth; x++){
                if(randomNumber.nextInt(100) > 98 && dungeonMatrix.get(y).get(x) instanceof Corridor) dungeonMatrix.get(y).get(x).setBaseElement(new Rocks());
                if(randomNumber.nextInt(100) > 98 && dungeonMatrix.get(y).get(x) instanceof Room) dungeonMatrix.get(y).get(x).setBasicUnit(new Goblin(10,44, 10));
            }
        }

        new TurnCycle(this);

//        System.out.println ("Dungeon_GA Creation took " + (System.nanoTime() - currentTime) / 1000000000 + " seconds");
    }

}
