package Unit;

import Errors.SumErrorException;
import Magic.Spells.Fireball;
import javafx.scene.image.Image;

public class Hero extends BasicUnit {

    public Hero(int power, int health, int mana, Image image){
        super(health, mana, image);
        spells.add(new Fireball());
    }

    public Hero(int strength, int agility, int intelligence, double strenghtGain, double agilityGain, double intelligenceGain) throws SumErrorException {
        super(strength, agility, intelligence, strenghtGain, agilityGain, intelligenceGain);
        spells.add(new Fireball());
    }
}