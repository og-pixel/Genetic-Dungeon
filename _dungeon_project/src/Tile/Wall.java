package Tile;

public class Wall extends Tile {
    public Wall(int xPos, int yPos){
        super(xPos, yPos);
    }

    public String toString(){
        return "x";
    }

    public String tileName(){ return  "wall"; }


}
