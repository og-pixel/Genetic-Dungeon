package Dungeon.Tile;

public class EmptyTile extends Tile{

    public EmptyTile(int xPos, int yPos){
        super(xPos, yPos);
    }

    public String toString(){
        return "  ";
    }

    public String tileName(){ return  "emptyTile"; }

}
