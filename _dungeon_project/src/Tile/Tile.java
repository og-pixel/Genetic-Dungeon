package Tile;
import Element.*;
import Unit.BasicUnit;

abstract public class Tile {

    int region;
    protected int xPos, yPos;
    private Element baseElement;
    private BasicUnit basicUnit;


    public Tile(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        baseElement = null;
        basicUnit = null;
    }


    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public Tile getTile(){ return this; }

    public Element getBaseElement(){ return baseElement; }

    public void setBaseElement(Element element){ baseElement = element; }

    public BasicUnit getBasicUnit() {
        return basicUnit;
    }
    public void setBasicUnit(BasicUnit basicUnit) {
        this.basicUnit = basicUnit;
    }

    public String toString(){
        return "FF";
    }

    public String tileName(){ return  "abstactTile"; }

}
