package Dungeon.Tile;

public class Entrance extends Tile{

    public Entrance(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public String toString(){
        return "e";
    }

    public String tileName(){ return  "entrance"; }


}
