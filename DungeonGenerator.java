package Dungeon;

import Algorithms.Matrix;
import Dungeon.Tile.*;

import java.util.*;

public class DungeonGenerator {

    /* Statistic variables, used to keep information of how many rooms dungeons has etc. */
    private int noOfRooms;

    /* Constants */
    private final int FOCUS_STRAIGHT_CORRIDORS = 65;
    private final int MINIMUM_CORRIDOR_LENGTH = 10;

    /* Random generator */
    private static Random randomNumber;

    /*
     * Stack used for backtracking, whenever there is a possibility to go multiple directions, rest of them will be saved
     * onto the stack
     */
    private Stack<Integer> directionStack;
    private Stack<Integer> xPosStack;
    private Stack<Integer> yPosStack;

    /*
     * Hash Map used to store how big corridor regions are
     * Each key corresponds to a region, and a value is how many tiles this region has
     */
    private HashMap<Integer,Integer> regionMap;

    //TODO this is the only part I don't like, I just store the information about dungeon just so I can access is right
    // away, but considering most of the project is to be abandoned and redone, I can change my mind later
    private Matrix<Tile> dungeonMatrix;
    private int dungeonHeight;
    private int dungeonWidth;
    private int limitCorner;

    public DungeonGenerator(Dungeon dungeon){
        randomNumber = new Random();

        directionStack = new Stack<>();
        xPosStack = new Stack<>();
        yPosStack = new Stack<>();

        regionMap = new HashMap<>();

        dungeonMatrix = dungeon.getDungeonMatrix();
        dungeonHeight = dungeon.getDungeonHeight();
        dungeonWidth = dungeon.getDungeonWidth();
    }

    public Dungeon generateDungeon(){
        generateRooms(200, 5);
        createMaze();
        removeSmallRegions();
        removeTeeth();
        generateDoors(1);
        fillEmptyTiles();

        return null;
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
        if (dungeonMatrix.getElement(currentXPos, currentYPos - 1) instanceof EmptyTile && currentYPos > 1) {
            if (dungeonMatrix.getElement(currentXPos + 1, currentYPos - 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos - 1, currentYPos - 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos, currentYPos - 2) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos - 1, currentYPos - 2) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos + 1, currentYPos - 2) instanceof EmptyTile) {
                availableDirection = availableDirection + 0b1000;
            }
        }
        /* Check if we can go RIGHT */
        if (dungeonMatrix.getElement(currentXPos + 1, currentYPos) instanceof EmptyTile && currentXPos < dungeonWidth - 2) {
            if (dungeonMatrix.getElement(currentXPos + 2, currentYPos) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos + 1, currentYPos + 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos + 1, currentYPos - 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos + 2, currentYPos - 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos + 2, currentYPos + 1) instanceof EmptyTile) {
                availableDirection = availableDirection + 0b0100;
            }
        }
        /* Check if we can go DOWN */
        if (dungeonMatrix.getElement(currentXPos, currentYPos + 1) instanceof EmptyTile && currentYPos < dungeonHeight - 2) {
            if (dungeonMatrix.getElement(currentXPos + 1, currentYPos + 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos, currentYPos + 2) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos - 1, currentYPos + 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos - 1, currentYPos + 2) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos + 1, currentYPos + 2) instanceof EmptyTile) {
                availableDirection = availableDirection + 0b0010;
            }
        }
        /* Check if we can go LEFT */
        if (dungeonMatrix.getElement(currentXPos - 1, currentYPos) instanceof EmptyTile && currentXPos > 1) {
            if (dungeonMatrix.getElement(currentXPos - 2, currentYPos) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos - 1, currentYPos + 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos - 1, currentYPos - 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos - 2, currentYPos - 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(currentXPos - 2, currentYPos + 1) instanceof EmptyTile) {
                availableDirection = availableDirection + 0b0001;
            }
        }

        //if We can go the same direction
        if ((availableDirection & determinedDirection) == determinedDirection) {

        /*  Roll a dice to determinate if we are continuing going the same direction, dice should be a little loaded to
            make dungeons look more straight (I am testing 55% to 70% odds) */

        // It will still chose this direction if there is no other option
            if (randomNumber.nextInt(100) <= FOCUS_STRAIGHT_CORRIDORS || (determinedDirection == availableDirection)) {
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

        if(dungeonMatrix.getElement(xPos, yPos) instanceof EmptyTile){
            if((availableDirection & 0b1000) == 0b1000  && dungeonMatrix.getElement(xPos - 1, yPos) instanceof EmptyTile
                    && dungeonMatrix.getElement(xPos, yPos - 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(xPos + 1, yPos) instanceof EmptyTile){
                digTunnel(xPos, yPos, 0b1000, region);}
            else if((availableDirection & 0b0100) == 0b0100 && dungeonMatrix.getElement(xPos, yPos - 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(xPos + 1, yPos) instanceof EmptyTile
                    && dungeonMatrix.getElement(xPos, yPos + 1) instanceof EmptyTile){
                digTunnel(xPos, yPos, 0b0100, region);}
            else if((availableDirection & 0b0010) == 0b0010 && dungeonMatrix.getElement(xPos - 1, yPos) instanceof EmptyTile
                    && dungeonMatrix.getElement(xPos + 1, yPos) instanceof EmptyTile
                    && dungeonMatrix.getElement(xPos, yPos + 1) instanceof EmptyTile){
                digTunnel(xPos, yPos, 0b0010, region);}
            else if((availableDirection & 0b0001) == 0b0001 && dungeonMatrix.getElement(xPos - 1, yPos) instanceof EmptyTile
                    && dungeonMatrix.getElement(xPos, yPos - 1) instanceof EmptyTile
                    && dungeonMatrix.getElement(xPos, yPos + 1) instanceof EmptyTile){
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

                if(dungeonMatrix.getElement(xPos, yPos) instanceof Corridor){

                    if(dungeonMatrix.getElement(xPos, yPos - 1) instanceof EmptyTile)emptyCorner++;
                    else{
                        tempX = xPos;
                        tempY = yPos - 1;
                    }

                    if(dungeonMatrix.getElement(xPos - 1, yPos) instanceof EmptyTile)emptyCorner++;
                    else{
                        tempX = xPos - 1;
                        tempY = yPos;
                    }

                    if(dungeonMatrix.getElement(xPos, yPos + 1) instanceof EmptyTile)emptyCorner++;
                    else{
                        tempX = xPos;
                        tempY = yPos + 1;
                    }

                    if(dungeonMatrix.getElement(xPos + 1, yPos) instanceof EmptyTile)emptyCorner++;
                    else{
                        tempX = xPos + 1;
                        tempY = yPos;
                    }

                    if(emptyCorner >= 3) {
                        dungeonMatrix.putElementAt(xPos, yPos,  new EmptyTile(xPos, yPos));
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
            if (dungeonMatrix.getElement(xPos, yPos - 1) instanceof EmptyTile) emptyCorner++;
            else {
                tempX = xPos;
                tempY = yPos - 1;
            }

            if (dungeonMatrix.getElement(xPos - 1, yPos) instanceof EmptyTile) emptyCorner++;
            else {
                tempX = xPos - 1;
                tempY = yPos;
            }

            if (dungeonMatrix.getElement(xPos, yPos + 1) instanceof EmptyTile) emptyCorner++;
            else {
                tempX = xPos;
                tempY = yPos + 1;
            }

            if (dungeonMatrix.getElement(xPos + 1, yPos) instanceof EmptyTile) emptyCorner++;
            else {
                tempX = xPos + 1;
                tempY = yPos;
            }

            if (emptyCorner >= 3 && this.limitCorner >= 0) {
                dungeonMatrix.putElementAt(xPos, yPos,  new EmptyTile(xPos, yPos));
                this.limitCorner--;
                removeCornerRecursive(tempX, tempY);
            }
        }
    }


    //Part of the method where we find single standing out "teeth" of the dungeonGenerator that lead nowhere and are only
    //standing out from the dungeonGenerator design
    public void removeTeeth() {
        int emptyCorner;
        int tempX = 0;
        int tempY = 0;
        for (int yPos = 0; yPos < dungeonHeight - 1; yPos++) {
            for (int xPos = 0; xPos < dungeonWidth - 1; xPos++) {
                emptyCorner = 0;
                if (dungeonMatrix.getElement(xPos, yPos) instanceof Corridor) {

                    if (dungeonMatrix.getElement(xPos, yPos - 1) instanceof EmptyTile) emptyCorner++;
                    else {
                        tempX = xPos;
                        tempY = yPos - 1;
                    }

                    if (dungeonMatrix.getElement(xPos - 1, yPos) instanceof EmptyTile) emptyCorner++;
                    else {
                        tempX = xPos - 1;
                        tempY = yPos;
                    }

                    if (dungeonMatrix.getElement(xPos, yPos + 1) instanceof EmptyTile) emptyCorner++;
                    else {
                        tempX = xPos;
                        tempY = yPos + 1;
                    }

                    if (dungeonMatrix.getElement(xPos + 1, yPos) instanceof EmptyTile) emptyCorner++;
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
        if (dungeonMatrix.getElement(xPos, yPos) instanceof Corridor) {

            if (dungeonMatrix.getElement(xPos, yPos - 1) instanceof Corridor) emptyCorner++;
            if (dungeonMatrix.getElement(xPos - 1, yPos) instanceof Corridor) emptyCorner++;
            if (dungeonMatrix.getElement(xPos, yPos + 1) instanceof Corridor) emptyCorner++;
            if (dungeonMatrix.getElement(xPos + 1, yPos) instanceof Corridor) emptyCorner++;

            if (emptyCorner >= 3) {
                //todo these two lines might be wrong

                regionMap.put(((Corridor)dungeonMatrix.getElement(previousX, previousY)).getRegion(),//todo do a better formating
                        regionMap.get(((Corridor)dungeonMatrix.getElement(previousX, previousY)).getRegion()) - 1);

                if(regionMap.get(((Corridor)dungeonMatrix.getElement(previousX, previousY)).getRegion()) <= 0)
                    regionMap.remove(((Corridor)dungeonMatrix.getElement(previousX, previousY)).getRegion());



                dungeonMatrix.getElement(previousX, previousY, new EmptyTile(xPos, yPos));
            }
        }
    }


    //TODO, I might not need this stuff anymore
    public void removeDot() {
        for (int yPos = 0; yPos < dungeonHeight - 1; yPos++) {
            for (int xPos = 0; xPos < dungeonWidth - 1; xPos++) {
                int emptyCorner = 0;
                if (dungeonMatrix.getElement(xPos, yPos) instanceof Corridor) {

                    if (dungeonMatrix.getElement(xPos, yPos - 1) instanceof EmptyTile) emptyCorner++;
                    if (dungeonMatrix.getElement(xPos - 1, yPos) instanceof EmptyTile) emptyCorner++;
                    if (dungeonMatrix.getElement(xPos, yPos + 1) instanceof EmptyTile) emptyCorner++;
                    if (dungeonMatrix.getElement(xPos + 1, yPos) instanceof EmptyTile) emptyCorner++;

                    if (emptyCorner >= 4) {
                        dungeonMatrix.putElementAt(xPos, yPos,  new EmptyTile(xPos, yPos));
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
                if (!(dungeonMatrix.getElement(j + xPos - 1, i + yPos - 1) instanceof EmptyTile)) return true; //True if tile IS NOT an Empty Dungeon.Tile
            }
        }
        return false;
    }




    /**
     * Method creates a number of rooms in the dungeonGenerator in random places and random size
     * @param noOfRooms A number of rooms that a method will try to put, however it might fail due to lack of space or
     *                  rooms overlapping.
     *                  More rooms will make denser dungeons
     * @param maxRoomSize Maximum room size that can be created, minimum is always 2, 6-7 max is recommended
     */
    void generateRooms(int noOfRooms, int maxRoomSize){

        for(int z = 0; z < noOfRooms; z++) {

            int randomRangeHeight = randomNumber.nextInt(maxRoomSize - 1) + 2;
            int randomRangeWidth = randomNumber.nextInt(maxRoomSize - 1) + 2;

            int randomXPos = randomNumber.nextInt(dungeonWidth);
            int randomYPos = randomNumber.nextInt(dungeonHeight);

            if(!tileTaken(randomRangeHeight, randomRangeWidth, randomXPos, randomYPos)){
                for (int y = 0; y < randomRangeHeight; y++) {
                    for (int x = 0; x < randomRangeWidth; x++) {
                        dungeonMatrix.putElementAt(x + randomXPos, y + randomYPos, new Room(x, y));
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
                if(dungeonMatrix.getElement(xPos, yPos) instanceof Room
                        && !(dungeonMatrix.getElement(xPos, yPos - 1) instanceof Room)
                        && !(dungeonMatrix.getElement(xPos - 1, yPos) instanceof Room)) {

                    int roomXEnd = xPos;
                    int roomYEnd = yPos;

                    while(dungeonMatrix.getElement(roomXEnd, yPos) instanceof Room){ roomXEnd++; }
                    while(dungeonMatrix.getElement(xPos, roomYEnd) instanceof Room){ roomYEnd++; }

                    //TODO I have changed Set to Sorted set to make it more logical, go back if its wrong
                    SortedSet<Integer> regionsAroundRoom = new TreeSet<>();     //Used temporally to determinate if there are
                                                                                //more regions around room, we want to connect all of them at least once
                    //Those two loops check around the room if there are corridors
                    for(int i = xPos; i < roomXEnd; i++){
                        if (yPos - 2 >= 0 && dungeonMatrix.getElement(i, yPos - 2) instanceof Corridor ) {
                            regionsAroundRoom.add(((Corridor)dungeonMatrix.getElement(i, yPos - 2)).getRegion());
                        }
                        if ((roomYEnd + 1 < dungeonHeight) && dungeonMatrix.getElement(i, roomYEnd + 1) instanceof Corridor){
                            regionsAroundRoom.add(((Corridor)dungeonMatrix.getElement(i, roomYEnd + 1)).getRegion());
                        }
                    }
                    for(int i = yPos; i < roomYEnd; i++){
                        if (xPos - 2 >= 0 && dungeonMatrix.getElement(xPos - 2, i) instanceof Corridor) {
                            regionsAroundRoom.add(((Corridor)dungeonMatrix.getElement(xPos - 2, i)).getRegion());
                        }
                        if (roomXEnd + 1 < dungeonWidth && dungeonMatrix.getElement(roomXEnd + 1, i) instanceof Corridor){
                            regionsAroundRoom.add(((Corridor)dungeonMatrix.getElement(roomXEnd + 1, i)).getRegion());
                        }
                    }

                    if (maxDoorsPerRoom < regionsAroundRoom.size()) maxDoorsPerRoom = regionsAroundRoom.size();

                    for(int y = yPos; y < roomYEnd; y++){
                        for(int x = xPos; x < roomXEnd; x++){
                            //todo its not random
                            try {
                                if (dungeonMatrix.getElement(x, y - 2) instanceof Corridor && maxDoorsPerRoom > 0 && ((Corridor)dungeonMatrix.getElement(x, y - 2)).getRegion() == regionsAroundRoom.first()) {
                                    dungeonMatrix.putElementAt(x, y - 1,  new Entrance(x, y - 1));
                                    regionsAroundRoom.remove(regionsAroundRoom.first());
                                    maxDoorsPerRoom--;
                                }
                                if (dungeonMatrix.getElement(x + 2, y) instanceof Corridor && maxDoorsPerRoom > 0 && ((Corridor)dungeonMatrix.getElement(x + 2, y)).getRegion() == regionsAroundRoom.first()) {
                                    dungeonMatrix.putElementAt(xPos + 1, yPos,  new Entrance(x + 1, y));
                                    regionsAroundRoom.remove(regionsAroundRoom.first());
                                    maxDoorsPerRoom--;
                                }
                                if (dungeonMatrix.getElement(x, y + 2, ) instanceof Corridor && maxDoorsPerRoom > 0 && ((Corridor)dungeonMatrix.getElement(x, y + 2, )).getRegion() == regionsAroundRoom.first()) {
                                    dungeonMatrix.putElementAt(x, y + 1,  new Entrance(x, y + 1));
                                    regionsAroundRoom.remove(regionsAroundRoom.first());
                                    maxDoorsPerRoom--;
                                }
                                if (dungeonMatrix.getElement(x - 2, y) instanceof Corridor && maxDoorsPerRoom > 0 && ((Corridor)dungeonMatrix.getElement(x - 2, y)).getRegion() == regionsAroundRoom.first()) {
                                    dungeonMatrix.putElementAt(x - 2, y,  new Entrance(x - 1, y));
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
     * Method used by the end of the generation to fill empty tiles with
     * wall elements.
     */
    public void fillEmptyTiles() {
        for (int yPos = 0; yPos < this.dungeonHeight; yPos++) {
            for (int xPos = 0; xPos < this.dungeonWidth; xPos++) {
                if (dungeonMatrix.getElement(xPos, yPos) instanceof EmptyTile) dungeonMatrix.putElementAt(xPos, yPos,  new Wall(xPos, yPos));
            }
        }
    }

    public void removeSmallRegions(){
        ArrayList<Integer> regionsToRemove = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : regionMap.entrySet()) {
            if(entry.getValue() <= MINIMUM_CORRIDOR_LENGTH) regionsToRemove.add(entry.getKey());
        }

        for(int y = 0; y < dungeonHeight; y++){
            for(int x = 0; x < dungeonWidth; x++){

                if(dungeonMatrix.getElement(x, y) instanceof Corridor){
                    for(int i = 0; i < regionsToRemove.size(); i++){

                        if(dungeonMatrix.getElement(x, y) instanceof Corridor) {
                            if (((Corridor) dungeonMatrix.getElement(x, y)).getRegion() == regionsToRemove.get(i)) {
                                dungeonMatrix.putElementAt(x, y,  new EmptyTile(x, y));
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
}
