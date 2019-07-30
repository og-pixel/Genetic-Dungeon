package Chromosome;

import java.io.Serializable;

public class Point implements Serializable {

    //Small Helping class to store node positions in a little more appropriate way
    private int xPos;
    private int yPos;
    private int steps;
    private int distanceToFinish;
    private int totalCost;

    public Point(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        distanceToFinish = 0;
        steps = 0;
    }

    public Point(int xPos, int yPos, int steps) {
        this.xPos = xPos;
        this.yPos = yPos;
        distanceToFinish = 0;
        this.steps = steps;
    }


    //TODO delete
    public void setTotalCost(int totalCost){
        this.totalCost = totalCost;
    }


    public int getSteps() {
        totalCost = steps + distanceToFinish;
        return steps;
    }

    public int getDistanceToFinish() {
        totalCost = steps + distanceToFinish;
        return distanceToFinish;
    }

    public int getTotalCost(){
        totalCost = steps + distanceToFinish;
        return totalCost;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setDistanceToFinish(int distanceToFinish){
        this.distanceToFinish = distanceToFinish;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public String toString(){
        return "Point:[" + xPos + "," + yPos + "] ";
    }
}
