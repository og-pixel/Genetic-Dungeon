package Game_Not_Used.sound.Magic.Debuffs;

import Game_Not_Used.sound.Magic.Passive;
import Game_Not_Used.sound.Unit.BasicUnit;
import Game_Not_Used.sound.Rendering.AssetDrawing;

public class Burning extends Passive {

    public Burning(BasicUnit abstractBasicUnit){
        super(AssetDrawing.burningImage);
        targetedHero = abstractBasicUnit;
        timeRemaining = 5;
    }
    public Burning(BasicUnit abstractBasicUnit, int timeRemaining ){
        super(AssetDrawing.burningImage);
        targetedHero = abstractBasicUnit;
        this.timeRemaining = timeRemaining ;
    }

    @Override
    public void tickReaction(BasicUnit target) {
        System.err.println("YOU ARE BURNING");
        target.setHealth(target.getHealth() - 10);
    }

    public String toString(){
        return "Burning";
    }
}
