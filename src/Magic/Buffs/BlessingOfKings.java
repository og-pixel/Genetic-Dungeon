package Magic.Buffs;

import Unit.BasicUnit;
import Magic.Passive;
import Rendering.AssetDrawing;

public class BlessingOfKings extends Passive {

    public BlessingOfKings(BasicUnit abstractBasicUnit){
        super(AssetDrawing.blessingImage);
        targetedHero = abstractBasicUnit;
        timeRemaining = 3;
    }

    @Override
    public void tickReaction(BasicUnit target) {
        System.err.println("YOU ARE BLESSED");
        target.setHealth(target.getHealth() + 10);
    }

    public String toString(){
        return "Blessed";
    }
}
