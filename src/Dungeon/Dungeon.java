package Dungeon;

import Element.Rocks;
import Errors.VariableBoundsIncorrect;
import Menu.AssetDrawing;
import Tile.*;
import Unit.*;
import java.util.*;

public class Dungeon {


    private TurnCycle turnCycle;

    public void notifyTurnCycle(){
        turnCycle.update();
    }

    public void attachObserver(TurnCycle turnCycle){
        this.turnCycle = turnCycle;
    }
    public int getState(){
        BasicUnit player = dungeonMatrix.get(heroLocationY).get(heroLocationX).getBasicUnit();
        return player.getNoOfMoves();
    }
    public void setState(int state){
        notifyTurnCycle();
    }




    public int heroLocationX, heroLocationY; //todo to remove, its for testing :)

    /* Constants */
    private int DICE_ROLL_ODDS = 65; //todo it was final
    private int MINIMUM_CORRIDOR_LENGTH = 10; //todo it was final

    /* Variables */
    public int dungeonWidth, dungeonHeight;    //Size of the dungeonMatrix
    private int limitCorner;            //Strange value to limit dead ends

    /* Statistic variables, used to keep information of how many rooms dungeons has etc. */
    private int noOfRooms;                      //Number of Rooms created

    /* Dungeon_GA is created as a matrix made out of (abstract) Tile objects */
    public ArrayList<ArrayList<Tile>> dungeonMatrix;

    /* Random generator */
    private Random randomNumber = new Random();

    /*
     * Stack used for backtracking, whenever there is a possibility to go multiple directions, rest of them will be saved
     * onto the stack
     */
    public Stack<Integer> directionStack = new Stack<>();
    private Stack<Integer> xPosStack = new Stack<>();
    private Stack<Integer> yPosStack = new Stack<>();

    /*
     * Hash Map used to store how big corridor regions are
     * Each key corresponds to a region, and a value is how many tiles this region has
     */
    private HashMap<Integer,Integer> regionMap = new HashMap<>();

    /**
     * Main Constructor, takes what size of a dungeonMatrix we want
     * and creates a matrix of empty tiles
     *
     * @param dungeonWidth  dungeonWidth of the dungeonMatrix.
     * @param dungeonHeight dungeonHeight of the dungeonMatrix.
     */
    Dungeon(int dungeonWidth, int dungeonHeight) {
        this.dungeonWidth = dungeonWidth;
        this.dungeonHeight = dungeonHeight;
        dungeonMatrix = new ArrayList<>();
        for (int yAxis = 0; yAxis < dungeonHeight; yAxis++) {
            dungeonMatrix.add(new ArrayList<>());
            for (int xAxis = 0; xAxis < dungeonWidth; xAxis++) {
                dungeonMatrix.get(yAxis).add(new EmptyTile(xAxis, yAxis));
            }
        }
    }

    //todo this one will take more parameters to create a dungeon right away
    /**
     * New Constructor
     * @param dungeonWidth
     * @param dungeonHeight
     */
    public Dungeon (int dungeonWidth, int dungeonHeight, int noOfRooms, int maxRoomSize, int diceRoll, int minimumCorridorLength) {
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
                dungeonMatrix.get(yAxis).add(new EmptyTile(xAxis, yAxis)); //Populate Tile Column
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

    /**
     * Method where program goes through the entire matrix
     * and finds any acceptable tile that can be a tunnel.
     * Acceptable tile is:
     * - NOT adjacent to the room tile
     * - NOT adjacent to the corridor (digTunnel method does the pathfinding)
     *  @return time spent to execute
     */
    public float createMaze() {
        float currentTime = System.nanoTime();
        int region = 0;
        for(int i = 1; i < dungeonHeight - 1; i++){
            for(int j = 1; j < dungeonWidth - 1; j++){
                if(dungeonMatrix.get(i).get(j) instanceof EmptyTile
                        && dungeonMatrix.get(i).get(j - 1) instanceof EmptyTile
                        && dungeonMatrix.get(i - 1).get(j) instanceof EmptyTile
                        && dungeonMatrix.get(i).get(j + 1) instanceof EmptyTile
                        && dungeonMatrix.get(i + 1).get(j) instanceof EmptyTile
                        && dungeonMatrix.get(i + 1).get(j + 1) instanceof EmptyTile
                        && dungeonMatrix.get(i + 1).get(j - 1) instanceof EmptyTile
                        && dungeonMatrix.get(i - 1).get(j - 1) instanceof EmptyTile
                        && dungeonMatrix.get(i - 1).get(j + 1) instanceof EmptyTile){
                    region++;
                    digTunnel(j,i, 0b0010, region);
                }
            }
        }
        return (System.nanoTime() - currentTime) / 1000000000;
    }

    /**
     *  @param determinedDirection used to determinate which direction we were going so for example
        1000 = Up
        0100 = Right
        0010 = Down
        0001 = Left
        eg. 1001 = up AND left as available directions that we can explore

    */
    private void digTunnel(int currentXPos, int currentYPos, int determinedDirection, int region) {

        //Create corridor in currentXPos and currentYPos and set a region
        dungeonMatrix.get(currentYPos).set(currentXPos, new Corridor(currentXPos, currentYPos));
        ((Corridor) dungeonMatrix.get(currentYPos).get(currentXPos)).setRegion(region);


        //Increment the region, NullPointerException if its the first one
        try { regionMap.put(region, regionMap.get(region) + 1); }
        catch (NullPointerException e){ regionMap.put(region, 1); }


        // Variable used to determinate which directions we can explore
        int availableDirection = 0;

        /* Check if we can go UP */
        if (dungeonMatrix.get(currentYPos - 1).get(currentXPos) instanceof EmptyTile && currentYPos > 1) {
            if (dungeonMatrix.get(currentYPos - 1).get(currentXPos + 1) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos - 1).get(currentXPos - 1) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos - 2).get(currentXPos) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos - 2).get(currentXPos - 1) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos - 2).get(currentXPos + 1) instanceof EmptyTile) {
                availableDirection = availableDirection + 0b1000;
            }
        }
        /* Check if we can go RIGHT */
        if (dungeonMatrix.get(currentYPos).get(currentXPos + 1) instanceof EmptyTile && currentXPos < dungeonWidth - 2) {
            if (dungeonMatrix.get(currentYPos).get(currentXPos + 2) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos + 1).get(currentXPos + 1) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos - 1).get(currentXPos + 1) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos - 1).get(currentXPos + 2) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos + 1).get(currentXPos + 2) instanceof EmptyTile) {
                availableDirection = availableDirection + 0b0100;
            }
        }
        /* Check if we can go DOWN */
        if (dungeonMatrix.get(currentYPos + 1).get(currentXPos) instanceof EmptyTile && currentYPos < dungeonHeight - 2) {
            if (dungeonMatrix.get(currentYPos + 1).get(currentXPos + 1) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos + 2).get(currentXPos) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos + 1).get(currentXPos - 1) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos + 2).get(currentXPos - 1) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos + 2).get(currentXPos + 1) instanceof EmptyTile) {
                availableDirection = availableDirection + 0b0010;
            }
        }
        /* Check if we can go LEFT */
        if (dungeonMatrix.get(currentYPos).get(currentXPos - 1) instanceof EmptyTile && currentXPos > 1) {
            if (dungeonMatrix.get(currentYPos).get(currentXPos - 2) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos + 1).get(currentXPos - 1) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos - 1).get(currentXPos - 1) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos - 1).get(currentXPos - 2) instanceof EmptyTile
                    && dungeonMatrix.get(currentYPos + 1).get(currentXPos - 2) instanceof EmptyTile) {
                availableDirection = availableDirection + 0b0001;
            }
        }

        //if We can go the same direction
        if ((availableDirection & determinedDirection) == determinedDirection) {

        /*  Roll a dice to determinate if we are continuing going the same direction, dice should be a little loaded to
            make dungeons look more straight (I am testing 55% to 70% odds) */

        // It will still chose this direction if there is no other option
            if (randomNumber.nextInt(100) <= DICE_ROLL_ODDS || (determinedDirection == availableDirection)) {
                availableDirection = availableDirection - determinedDirection;
                checkAlternativeRoutes(currentXPos, currentYPos, availableDirection);
                //TODO Not random
                if (determinedDirection == 0b1000) {
                    digTunnel(currentXPos, currentYPos - 1, 0b1000, region);
                } else if (determinedDirection == 0b0100) {
                    digTunnel(currentXPos + 1, currentYPos, 0b0100, region);
                } else if (determinedDirection == 0b0010) {
                    digTunnel(currentXPos, currentYPos + 1, 0b0010, region);
                } else if (determinedDirection == 0b0001) {
                    digTunnel(currentXPos - 1, currentYPos, 0b0001, region);
                }

            //If we lose diceroll, we will go alternative routes that are left
            } else {
                availableDirection = availableDirection - determinedDirection;
                checkAlternativeRoutes(currentXPos, currentYPos, availableDirection);
                //TODO Not random
                if((availableDirection & 0b1000) == 0b1000){
                    digTunnel(currentXPos, currentYPos - 1, 0b1000, region);}
                else if((availableDirection & 0b0100) == 0b0100){
                    digTunnel(currentXPos + 1, currentYPos, 0b0100, region);}
                else if((availableDirection & 0b0010) == 0b0010){
                    digTunnel(currentXPos, currentYPos + 1, 0b0010, region);}
                else if((availableDirection & 0b0001) == 0b0001){
                    digTunnel(currentXPos - 1, currentYPos, 0b0001, region);}
            }
        //If we couldn't have to continue go the same way
        } else {
            //TODO Not random
            checkAlternativeRoutes(currentXPos, currentYPos, availableDirection);
            if((availableDirection & 0b1000) == 0b1000){ //&& (currentYPos > 1)){ todo
                digTunnel(currentXPos, currentYPos - 1, 0b1000, region);}
            else if((availableDirection & 0b0100) == 0b0100 && (currentXPos < dungeonWidth - 2)){
                digTunnel(currentXPos + 1, currentYPos, 0b0100, region);}
            else if((availableDirection & 0b0010) == 0b0010 && (currentYPos < dungeonHeight - 2)){
                digTunnel(currentXPos, currentYPos + 1, 0b0010, region);}
            else if((availableDirection & 0b0001) == 0b0001 && (currentXPos > 1)){
                digTunnel(currentXPos - 1, currentYPos, 0b0001, region);}
        }
         while(!directionStack.isEmpty()) backtrack(region);
    }

    /**
     * Method used to backtrack looking for all previous possible routes,
     * and if they are still available, digTunnel there.
     */
    private void backtrack(int region){
        int xPos = xPosStack.pop();
        int yPos = yPosStack.pop();
        int availableDirection = directionStack.pop();

        if(dungeonMatrix.get(yPos).get(xPos) instanceof EmptyTile){
            if((availableDirection & 0b1000) == 0b1000  && dungeonMatrix.get(yPos).get(xPos - 1) instanceof EmptyTile
                    && dungeonMatrix.get(yPos - 1).get(xPos) instanceof EmptyTile
                    && dungeonMatrix.get(yPos).get(xPos + 1) instanceof EmptyTile){
                digTunnel(xPos, yPos, 0b1000, region);}
            else if((availableDirection & 0b0100) == 0b0100 && dungeonMatrix.get(yPos - 1).get(xPos) instanceof EmptyTile
                    && dungeonMatrix.get(yPos).get(xPos + 1) instanceof EmptyTile
                    && dungeonMatrix.get(yPos + 1).get(xPos) instanceof EmptyTile){
                digTunnel(xPos, yPos, 0b0100, region);}
            else if((availableDirection & 0b0010) == 0b0010 && dungeonMatrix.get(yPos).get(xPos - 1) instanceof EmptyTile
                    && dungeonMatrix.get(yPos).get(xPos + 1) instanceof EmptyTile
                    && dungeonMatrix.get(yPos + 1).get(xPos) instanceof EmptyTile){
                digTunnel(xPos, yPos, 0b0010, region);}
            else if((availableDirection & 0b0001) == 0b0001 && dungeonMatrix.get(yPos).get(xPos - 1) instanceof EmptyTile
                    && dungeonMatrix.get(yPos - 1).get(xPos) instanceof EmptyTile
                    && dungeonMatrix.get(yPos + 1).get(xPos) instanceof EmptyTile){
                digTunnel(xPos, yPos, 0b0001, region);}
        }
    }

    private void checkAlternativeRoutes(int currentXPos, int currentYPos, int availableDirection){
        if((availableDirection & 0b1000) == 0b1000){
            directionStack.push(0b1000);
            xPosStack.push(currentXPos);
            yPosStack.push(currentYPos - 1);
        }
        if((availableDirection & 0b0100) == 0b0100){
            directionStack.push(0b0100);
            xPosStack.push(currentXPos + 1);
            yPosStack.push(currentYPos);
        }
        if((availableDirection & 0b0010) == 0b0010){
            directionStack.push(0b0010);
            xPosStack.push(currentXPos);
            yPosStack.push(currentYPos + 1);
        }
        if((availableDirection & 0b0001) == 0b0001){
            directionStack.push(0b0001);
            xPosStack.push(currentXPos - 1);
            yPosStack.push(currentYPos);
        }
    }


    //todo better method name, or better, rewrite it as I don't like this method, I might not even need it
    public void removeCorner(int limit){
        int emptyCorner;
        int tempX = 0;
        int tempY = 0;
        this.limitCorner = limit;
        for(int yPos = 0; yPos < dungeonHeight - 1; yPos++){
            for(int xPos = 0; xPos < dungeonWidth - 1; xPos++){
                emptyCorner = 0;

                if(dungeonMatrix.get(yPos).get(xPos) instanceof Corridor){

                    if(dungeonMatrix.get(yPos - 1).get(xPos) instanceof EmptyTile)emptyCorner++;
                    else{
                        tempX = xPos;
                        tempY = yPos - 1;
                    }

                    if(dungeonMatrix.get(yPos).get(xPos - 1) instanceof EmptyTile)emptyCorner++;
                    else{
                        tempX = xPos - 1;
                        tempY = yPos;
                    }

                    if(dungeonMatrix.get(yPos + 1).get(xPos) instanceof EmptyTile)emptyCorner++;
                    else{
                        tempX = xPos;
                        tempY = yPos + 1;
                    }

                    if(dungeonMatrix.get(yPos).get(xPos + 1) instanceof EmptyTile)emptyCorner++;
                    else{
                        tempX = xPos + 1;
                        tempY = yPos;
                    }

                    if(emptyCorner >= 3) {
                        dungeonMatrix.get(yPos).set(xPos, new EmptyTile(xPos, yPos));
                        removeCornerRecursive(tempX, tempY);
                    }
                }
            }
        }
    }


    private void removeCornerRecursive(int xPos, int yPos){
        int tempX = 0;
        int tempY = 0;
        int emptyCorner = 0;

        if(xPos > 0 || yPos > 0){
            if (dungeonMatrix.get(yPos - 1).get(xPos) instanceof EmptyTile) emptyCorner++;
            else {
                tempX = xPos;
                tempY = yPos - 1;
            }

            if (dungeonMatrix.get(yPos).get(xPos - 1) instanceof EmptyTile) emptyCorner++;
            else {
                tempX = xPos - 1;
                tempY = yPos;
            }

            if (dungeonMatrix.get(yPos + 1).get(xPos) instanceof EmptyTile) emptyCorner++;
            else {
                tempX = xPos;
                tempY = yPos + 1;
            }

            if (dungeonMatrix.get(yPos).get(xPos + 1) instanceof EmptyTile) emptyCorner++;
            else {
                tempX = xPos + 1;
                tempY = yPos;
            }

            if (emptyCorner >= 3 && this.limitCorner >= 0) {
                dungeonMatrix.get(yPos).set(xPos, new EmptyTile(xPos, yPos));
                this.limitCorner--;
                removeCornerRecursive(tempX, tempY);
            }
        }
    }


    //Part of the method where we find single standing out "teeth" of the dungeon that lead nowhere and are only
    //standing out from the dungeon design
    public void removeTeeth() {
        int emptyCorner;
        int tempX = 0;
        int tempY = 0;
        for (int yPos = 0; yPos < dungeonHeight - 1; yPos++) {
            for (int xPos = 0; xPos < dungeonWidth - 1; xPos++) {
                emptyCorner = 0;
                if (dungeonMatrix.get(yPos).get(xPos) instanceof Corridor) {

                    if (dungeonMatrix.get(yPos - 1).get(xPos) instanceof EmptyTile) emptyCorner++;
                    else {
                        tempX = xPos;
                        tempY = yPos - 1;
                    }

                    if (dungeonMatrix.get(yPos).get(xPos - 1) instanceof EmptyTile) emptyCorner++;
                    else {
                        tempX = xPos - 1;
                        tempY = yPos;
                    }

                    if (dungeonMatrix.get(yPos + 1).get(xPos) instanceof EmptyTile) emptyCorner++;
                    else {
                        tempX = xPos;
                        tempY = yPos + 1;
                    }

                    if (dungeonMatrix.get(yPos).get(xPos + 1) instanceof EmptyTile) emptyCorner++;
                    else {
                        tempX = xPos + 1;
                        tempY = yPos;
                    }

                    if (emptyCorner >= 3) {
                        removeTeeth2(xPos, yPos, tempX, tempY);
                    }

                }
            }
        }
    }

    private void removeTeeth2(int previousX, int previousY, int xPos, int yPos) {
        int emptyCorner = 0;
        if (dungeonMatrix.get(yPos).get(xPos) instanceof Corridor) {

            if (dungeonMatrix.get(yPos - 1).get(xPos) instanceof Corridor) emptyCorner++;
            if (dungeonMatrix.get(yPos).get(xPos - 1) instanceof Corridor) emptyCorner++;
            if (dungeonMatrix.get(yPos + 1).get(xPos) instanceof Corridor) emptyCorner++;
            if (dungeonMatrix.get(yPos).get(xPos + 1) instanceof Corridor) emptyCorner++;

            if (emptyCorner >= 3) {
                //todo these two lines might be wrong

                regionMap.put(((Corridor)dungeonMatrix.get(previousY).get(previousX)).getRegion(),//todo do a better formating
                        regionMap.get(((Corridor)dungeonMatrix.get(previousY).get(previousX)).getRegion()) - 1);

                if(regionMap.get(((Corridor)dungeonMatrix.get(previousY).get(previousX)).getRegion()) <= 0)
                    regionMap.remove(((Corridor)dungeonMatrix.get(previousY).get(previousX)).getRegion());



                dungeonMatrix.get(previousY).set(previousX, new EmptyTile(xPos, yPos));
            }
        }
    }


    //TODO, I might not need this stuff anymore
    public void removeDot() {
        for (int yPos = 0; yPos < dungeonHeight - 1; yPos++) {
            for (int xPos = 0; xPos < dungeonWidth - 1; xPos++) {
                int emptyCorner = 0;
                if (dungeonMatrix.get(yPos).get(xPos) instanceof Corridor) {

                    if (dungeonMatrix.get(yPos - 1).get(xPos) instanceof EmptyTile) emptyCorner++;
                    if (dungeonMatrix.get(yPos).get(xPos - 1) instanceof EmptyTile) emptyCorner++;
                    if (dungeonMatrix.get(yPos + 1).get(xPos) instanceof EmptyTile) emptyCorner++;
                    if (dungeonMatrix.get(yPos).get(xPos + 1) instanceof EmptyTile) emptyCorner++;

                    if (emptyCorner >= 4) {
                        dungeonMatrix.get(yPos).set(xPos, new EmptyTile(xPos, yPos));
                        //todo if my region removal works, i need to add this rule here
                    }
                }
            }
        }
    }



    private boolean tileTaken(int height, int width, int xPos, int yPos){
        if(height + yPos >= this.dungeonHeight || width + xPos >= this.dungeonWidth) return true; //Error checking if you try to create room outside of the dungeonMatrix
        if(xPos < 1 || yPos < 1) return true;                                                     //Error check if program tries to check outside of the array

        for(int i = 0; i < height + 2; i++){
            for(int j = 0; j < width + 2; j++){
                if (!(dungeonMatrix.get(i + yPos - 1).get(j + xPos - 1) instanceof EmptyTile)) return true; //True if tile IS NOT an Empty Tile
            }
        }
        return false;
    }




    /**
     * Method creates a number of rooms in the dungeon in random places and random size
     * @param noOfRooms A number of rooms that a method will try to put, however it might fail due to lack of space or
     *                  rooms overlapping.
     *                  More rooms will make denser dungeons
     * @param maxRoomSize Maximum room size that can be created, minimum is always 2, 6-7 max is recommended
     */
    void generateRooms(int noOfRooms, int maxRoomSize){

        for(int z = 0; z < noOfRooms; z++) {

            int randomRangeHeight = randomNumber.nextInt(maxRoomSize - 1) + 2;
            int randomRangeWidth = randomNumber.nextInt(maxRoomSize - 1) + 2;

            int randomXPos = randomNumber.nextInt(this.dungeonWidth);
            int randomYPos = randomNumber.nextInt(this.dungeonHeight);

            if(!tileTaken(randomRangeHeight, randomRangeWidth, randomXPos, randomYPos)){
                for (int y = 0; y < randomRangeHeight; y++) {
                    for (int x = 0; x < randomRangeWidth; x++) {
                        dungeonMatrix.get(y + randomYPos).set(x + randomXPos, new Room(x, y));
                    }
                }
                this.noOfRooms++; //TODO it counts how many rooms are here, purely for stats
            }
        }
    }

    /**
     * Generate doors that connect rooms with tunnels
     * TODO I still have to add option to connect between rooms
     */
    public void generateDoors(int maxDoorsPerRoom){
        for(int yPos = 1; yPos < this.dungeonHeight - 2; yPos++){
            for(int xPos = 1; xPos < this.dungeonWidth - 2; xPos++){
                //Find left top corner of the room, so we don't accidentally check the same room
                //multiple times
                if(dungeonMatrix.get(yPos).get(xPos) instanceof Room
                        && !(dungeonMatrix.get(yPos - 1).get(xPos) instanceof Room)
                        && !(dungeonMatrix.get(yPos).get(xPos - 1) instanceof Room)) {

                    int roomXEnd = xPos;
                    int roomYEnd = yPos;

                    while(dungeonMatrix.get(yPos).get(roomXEnd) instanceof Room){ roomXEnd++; }
                    while(dungeonMatrix.get(roomYEnd).get(xPos) instanceof Room){ roomYEnd++; }

                    //TODO I have changed Set to Sorted set to make it more logical, go back if its wrong
                    SortedSet<Integer> regionsAroundRoom = new TreeSet<>();     //Used temporally to determinate if there are
                                                                                //more regions around room, we want to connect all of them at least once
                    //Those two loops check around the room if there are corridors
                    for(int i = xPos; i < roomXEnd; i++){
                        if (yPos - 2 >= 0 && dungeonMatrix.get(yPos - 2).get(i) instanceof Corridor ) {
                            regionsAroundRoom.add(((Corridor)dungeonMatrix.get(yPos - 2).get(i)).getRegion());
                        }
                        if ((roomYEnd + 1 < dungeonHeight) && dungeonMatrix.get(roomYEnd + 1).get(i) instanceof Corridor){
                            regionsAroundRoom.add(((Corridor)dungeonMatrix.get(roomYEnd + 1).get(i)).getRegion());
                        }
                    }
                    for(int i = yPos; i < roomYEnd; i++){
                        if (xPos - 2 >= 0 && dungeonMatrix.get(i).get(xPos - 2) instanceof Corridor) {
                            regionsAroundRoom.add(((Corridor)dungeonMatrix.get(i).get(xPos - 2)).getRegion());
                        }
                        if (roomXEnd + 1 < dungeonWidth && dungeonMatrix.get(i).get(roomXEnd + 1) instanceof Corridor){
                            regionsAroundRoom.add(((Corridor)dungeonMatrix.get(i).get(roomXEnd + 1)).getRegion());
                        }
                    }

                    if (maxDoorsPerRoom < regionsAroundRoom.size()) maxDoorsPerRoom = regionsAroundRoom.size();

                    for(int y = yPos; y < roomYEnd; y++){
                        for(int x = xPos; x < roomXEnd; x++){
                            //todo its not random
                            try {
                                if (dungeonMatrix.get(y - 2).get(x) instanceof Corridor && maxDoorsPerRoom > 0 && ((Corridor)dungeonMatrix.get(y - 2).get(x)).getRegion() == regionsAroundRoom.first()) {
                                    dungeonMatrix.get(y - 1).set(x, new Entrance(x, y - 1));
                                    regionsAroundRoom.remove(regionsAroundRoom.first());
                                    maxDoorsPerRoom--;
                                }
                                if (dungeonMatrix.get(y).get(x + 2) instanceof Corridor && maxDoorsPerRoom > 0 && ((Corridor)dungeonMatrix.get(y).get(x + 2)).getRegion() == regionsAroundRoom.first()) {
                                    dungeonMatrix.get(y).set(x + 1, new Entrance(x + 1, y));
                                    regionsAroundRoom.remove(regionsAroundRoom.first());
                                    maxDoorsPerRoom--;
                                }
                                if (dungeonMatrix.get(y + 2).get(x) instanceof Corridor && maxDoorsPerRoom > 0 && ((Corridor)dungeonMatrix.get(y + 2).get(x)).getRegion() == regionsAroundRoom.first()) {
                                    dungeonMatrix.get(y + 1).set(x, new Entrance(x, y + 1));
                                    regionsAroundRoom.remove(regionsAroundRoom.first());
                                    maxDoorsPerRoom--;
                                }
                                if (dungeonMatrix.get(y).get(x - 2) instanceof Corridor && maxDoorsPerRoom > 0 && ((Corridor)dungeonMatrix.get(y).get(x - 2)).getRegion() == regionsAroundRoom.first()) {
                                    dungeonMatrix.get(y).set(x - 1, new Entrance(x - 1, y));
                                    regionsAroundRoom.remove(regionsAroundRoom.first());
                                    maxDoorsPerRoom--;
                                }
                            }
                            catch (IndexOutOfBoundsException e){
                            //todo maybe just make it not throw by using right boudaries
                            }
                            catch (NoSuchElementException e){
                            //todo I have swapped two arrays into one but I added exception
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method used by the end of the dungeon creation to make sure dungeon is possible to finish
     * It goes to every single room form the start and tries to enter a finish place.
     * OR it will go to every room from once place that will be considered a start, and it will calculate furthest
     * possible place to put and end there, start and finish can be in a room too!
     *
     * //TODO If I expand this game I should upgrade it to consider locked doors or blockades that I will create
     */
    void traverseDungeon(){

    }

    /**
     * Method used by the end of the generation to fill empty tiles with
     * wall elements.
     */
    public void fillEmptyTiles() {
        for (int yPos = 0; yPos < this.dungeonHeight; yPos++) {
            for (int xPos = 0; xPos < this.dungeonWidth; xPos++) {
                if (dungeonMatrix.get(yPos).get(xPos) instanceof EmptyTile) dungeonMatrix.get(yPos).set(xPos, new Wall(xPos, yPos));
            }
        }
    }


    public Tile getTileType(int xPos, int yPos){
        return dungeonMatrix.get(yPos).get(xPos);
    }



    public void removeSmallRegions(){
        ArrayList<Integer> regionsToRemove = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : regionMap.entrySet()) {
            if(entry.getValue() <= MINIMUM_CORRIDOR_LENGTH) regionsToRemove.add(entry.getKey());
        }

        for(int y = 0; y < dungeonHeight; y++){
            for(int x = 0; x < dungeonWidth; x++){

                if(dungeonMatrix.get(y).get(x) instanceof Corridor){
                    for(int i = 0; i < regionsToRemove.size(); i++){

                        if(dungeonMatrix.get(y).get(x) instanceof Corridor) {
                            if (((Corridor) dungeonMatrix.get(y).get(x)).getRegion() == regionsToRemove.get(i)) {
                                dungeonMatrix.get(y).set(x, new EmptyTile(x, y));
                                regionMap.put(regionsToRemove.get(i), regionMap.get(regionsToRemove.get(i)) - 1);
                                if (regionMap.get(regionsToRemove.get(i)) <= 0) {
                                    regionMap.remove(regionsToRemove.get(i));
                                    regionsToRemove.remove(regionsToRemove.get(i));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void createPlayerDebug(){

        for(int y = 0; y < dungeonHeight; y++){
            for(int x = 0; x < dungeonWidth; x++){
                if(dungeonMatrix.get(y).get(x) instanceof Room){
                    dungeonMatrix.get(y).get(x).setBasicUnit(new Hero(10,100,10, AssetDrawing.gnollAvatar));
                    heroLocationX = x;
                    heroLocationY = y;
                    BasicUnit player = dungeonMatrix.get(y).get(x).getBasicUnit();
//                    player.applyPassive(new BlessingOfKings(player));
//                    player.applyPassive(new Burning(player));
                    Goblin goblin = new Goblin(10,10,1000);
                    goblin.castSpell(0, player);
                    System.out.println(player.passives);
                    return;
                }
            }
        }

    }
}
