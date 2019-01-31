package Dungeon.Tile;

public class End extends Tile {

    public End(int xPos, int yPos){
        super(xPos, yPos);
    }

    public String toString(){
        return "E";
    }

    public String tileName(){ return  "end"; }


}
