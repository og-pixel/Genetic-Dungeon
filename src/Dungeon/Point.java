package Dungeon;

import Algorithms.Algorithms;

public class Point {
    //Small Helping class to store node positions in a little more appropriate way
    private int xPos;
    private int yPos;
    private int manhattanDistance;

    public Point(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        manhattanDistance = -1;
    }

    public void setManhattanDistance(int x){
        manhattanDistance = x;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
