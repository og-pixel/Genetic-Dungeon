package Magic.Spells;

import Magic.AbstractSpell;
import Unit.BasicUnit;

public class LesserHeal extends AbstractSpell {

    public LesserHeal(){
        super(50, 5, new int[] {5});

    }

    public LesserHeal(int manaCost, int cooldown){
        super(manaCost,cooldown, new int[] {5});
    }

    @Override
    public void castSpell(BasicUnit target) {
        target.setHealth(target.getHealth() + getSpellPower()[0]);
    }

    @Override
    public void castSpell(int xPos, int yPos) {
        //NOT HERE
    }

}