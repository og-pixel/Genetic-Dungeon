package Dungeon;

public class Position {
    //Small Helping class to store node positions in a little more appropriate way
    private int xPos;
    private int yPos;

    public Position(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
}
