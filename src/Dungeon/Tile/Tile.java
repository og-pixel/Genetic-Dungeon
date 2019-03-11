package Dungeon.Tile;
import Dungeon.Dungeon_Element.*;
//import Game_Not_Used.sound.Unit.BasicUnit;

import java.io.Serializable;

abstract public class Tile implements Serializable {

    int region;
    protected int xPos, yPos;
    private Element baseElement;

    //TODO currently commented out as its part of the "game"
//    private BasicUnit basicUnit;


    public Tile(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        baseElement = null;
//        basicUnit = null;
    }


    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    //TODO isn't that unnecessary?
    public Tile getTile(){ return this; }

    public Element getBaseElement(){ return baseElement; }

    public void setBaseElement(Element element){ baseElement = element; }

//    public BasicUnit getBasicUnit() {
//        return basicUnit;
//    }
//    public void setBasicUnit(BasicUnit basicUnit) {
//        this.basicUnit = basicUnit;
//    }

    public String toString(){
        return "FF";
    }

    public String tileName(){ return  "abstactTile"; }

}
