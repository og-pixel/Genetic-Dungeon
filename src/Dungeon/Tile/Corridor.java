package Dungeon.Tile;

public class Corridor extends Tile {

    public Corridor(int xPos, int yPos){
        super(xPos, yPos);
    }

    public void setRegion(int region){
        this.region = region;
    }

    public int getRegion(){
        return region;
    }

    public String toString(){
        return "░";
    }

    public String tileName(){ return  "corridor"; }
}
