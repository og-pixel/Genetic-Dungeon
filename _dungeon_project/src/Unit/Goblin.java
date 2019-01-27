package Unit;

import Magic.Spells.Fireball;

public class Goblin extends BasicUnit {

    public Goblin(int power, int health, int mana){
        super(health, mana);
        spells.add(new Fireball());
    }

}
