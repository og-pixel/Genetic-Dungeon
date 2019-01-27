package Element;

abstract public class Element {
    boolean blocks;
    public Element(boolean blocks){
        this.blocks = false;
    }

    public Element getElement(){ return this; }
}
