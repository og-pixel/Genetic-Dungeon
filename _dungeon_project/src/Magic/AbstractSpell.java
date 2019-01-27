package Magic;
import Unit.BasicUnit;

public abstract class AbstractSpell {
    int manaCost;
    int cooldown;
    int timeLeft;

    int spellRank;

    int[] spellPower;

    public AbstractSpell(int manaCost, int cooldown, int[] list){
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        timeLeft = 0;
        spellRank = 1;

        spellPower = list;
    }

    public AbstractSpell(int manaCost, int cooldown, int[] list, int spellRank){
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        timeLeft = 0;
        this.spellRank = spellRank;

        spellPower = list;
    }


    public abstract void castSpell(BasicUnit target);
    public abstract void castSpell(int xPos, int yPos);

    public int getManaCost(){
        return manaCost;
    }
    public int getCooldown(){
        return cooldown;
    }
    public int[] getSpellPower(){
        return spellPower;
    }


    public void tickCooldown(){
        timeLeft++;
        if(timeLeft >= cooldown)timeLeft = 0;
    }



    public boolean canCast(int manaAvaliable){
        return manaAvaliable >= manaCost && timeLeft == 0;
    }
}
