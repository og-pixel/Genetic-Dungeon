package Game_Not_Used.sound.Unit;

import Exceptions.SumErrorException;
import Game_Not_Used.sound.Items.Armor.Armor;
import Game_Not_Used.sound.Items.ItemFactory;
import Game_Not_Used.sound.Items.Weapon.Weapon;
import Game_Not_Used.sound.Rendering.AssetDrawing;
import Game_Not_Used.sound.Magic.AbstractSpell;
import Game_Not_Used.sound.Magic.Passive;
import javafx.scene.image.Image;

import java.util.ArrayList;

abstract public class BasicUnit {

    /**
     * Blueprint values used during unit creation and existence to determinate unit's
     * more basic statistics, like health
     */
    private final static int LIFE_PER_STRENGTH = 10;
    private final static double ARMOR_PER_AGILITY = 0.1;
    private final static int MANA_PER_INTELLIGENCE = 25;
    private final static double EVASION_PER_AGILITY = 0.01;

    private final static double DEFAULT_CRITICAL = 2;
    private final static int DAMAGE_PER_PRIMARY_SKIL = 1;
    
    /**
     * Basic Game_Not_Used.sound.Unit statistics
     */
    private int health, maxHealth;
    private int mana, maxMana;
    private double armor, maxArmor;
    private double evasion;

    //Hero stats
    private double strength, agility, intelligence;
    private double strenghtGain, agilityGain, intelligenceGain;

    //Item factory creates random (duh) items
    private ItemFactory itemFactory;

    //Game_Not_Used.sound.Items equipped
    private Armor head;
    private Armor chest;
    private Armor legs;

    //todo change to private later
    public Weapon rightHand;
    private Weapon leftHand;



    int noOfMoves;
    BasicUnit targetBasicUnit;

    public ArrayList<Passive> passives = new ArrayList<>();
    protected ArrayList<AbstractSpell> spells = new ArrayList<>();
    private Image characterImage;

    //TODO this will be new better constructor
    public BasicUnit(int strength, int agility, int intelligence, double strenghtGain, double agilityGain, double intelligenceGain) throws SumErrorException {
        this.strength = strength;
        this.agility = agility ;
        this.intelligence = intelligence;
        this.strenghtGain = strenghtGain;
        this.agilityGain = agilityGain;
        this.intelligenceGain = intelligenceGain;

        noOfMoves = 3;

        health = strength * LIFE_PER_STRENGTH;
        maxHealth = health;

        armor = agility * ARMOR_PER_AGILITY;
        maxArmor = armor;

        evasion = agility * EVASION_PER_AGILITY;

        mana = intelligence * MANA_PER_INTELLIGENCE;
        maxMana = mana;


        //TODO
        itemFactory = new ItemFactory();


        rightHand = itemFactory.createRandomWeapon();
        chest = itemFactory.createRandomArmor();
        System.err.println();
    }


    public BasicUnit(int health, int mana){
        this.health = health;
        this.mana = mana;
        maxHealth = health;
        maxMana = mana;
        characterImage = AssetDrawing.defaultImage;

        noOfMoves = 3;
    }
    public BasicUnit(int health, int mana, Image characterImage){
        this.health = health;
        this.mana = mana;
        this.characterImage = characterImage;
        maxHealth = health;
        maxMana = mana;

        noOfMoves = 3;
    }


    public BasicUnit getBaseCharacter(){
        return this;
    }

    public Image getBaseCharacterImage(){
        return this.characterImage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    //TODO Method checking if basicUnit can go somewhere
    //0001 = top
    //0010 = right
    //0100 = down
    //1000 = left
    public void checkCollision(int direction){
        if(direction == 0b0001){

        }
        else if(direction == 0b0010){

        }
        else if(direction == 0b0100){

        }
        else if(direction == 0b1000){

        }

    }


    public int move(String directon){
        if(directon.equals("up")){
            return 0;
        }
        else if(directon.equals("right")){
            return 1;
        }
        else if(directon.equals("down")){
            return 2;
        }
        else {
            return 3;
        }
    }

    public boolean castSpell(int whichSpell, BasicUnit target){
        try{
            if(!spells.get(whichSpell).canCast(mana))return false;
            else{
                spells.get(whichSpell).castSpell(target);
                mana = mana - spells.get(whichSpell).getManaCost();
                return true;
            }
        }catch (NullPointerException e){
            System.err.println("Nullpointer Exception");
            return false;
        }
    }

    public void applyPassive(Passive passive){
        passives.add(passive);
    }

    public void notifyPassive(){
        for(int i = 0; i < passives.size(); i++){
            passives.get(i).tick(this);
            if(passives.get(i).timeRemaining <= 0){
                passives.remove(i);
                break;
            }
        }
    }

    public int getNoOfMoves() {
        return noOfMoves;
    }

    public void setNoOfMoves(int noOfMoves) {
        this.noOfMoves = noOfMoves;
    }


    public double getEvasion() {
        return evasion;
    }

    public double getStrength() {
        return strength;
    }

    public double getAgility() {
        return agility;
    }

    public double getIntelligence() {
        return intelligence;
    }

    public double getStrenghtGain() {
        return strenghtGain;
    }

    public double getAgilityGain() {
        return agilityGain;
    }

    public double getIntelligenceGain() {
        return intelligenceGain;
    }


}
