package Game_Not_Used.sound.Magic.Spells;

import Game_Not_Used.sound.Magic.AbstractSpell;
import Game_Not_Used.sound.Unit.BasicUnit;
import Game_Not_Used.sound.Magic.Debuffs.Burning;

public class Fireball extends AbstractSpell {

    public Fireball() {
        super(100,1, new int[]{22,2});
    }

    public Fireball(int manaCost, int cooldown) {
        super(manaCost, cooldown, new int[]{10,2});
    }

    @Override
    public void castSpell(BasicUnit target) {
        target.setHealth(target.getHealth() - getSpellPower()[0]);
        target.applyPassive(new Burning(target, 8));
    }

    @Override
    public void castSpell(int xPos, int yPos) {

    }

    public String toString(){
        return "Fireball";
    }
}
