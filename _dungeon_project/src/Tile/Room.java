package Tile;


public class Room extends Tile {

    public Room(int xPos, int yPos){
        super(xPos, yPos);
    }




    public String toString(){
        return "▓";
    }

    public String tileName(){
        return  "room";
    }


}
