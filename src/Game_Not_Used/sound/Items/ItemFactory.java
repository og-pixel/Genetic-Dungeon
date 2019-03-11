package Game_Not_Used.sound.Items;

import Exceptions.SumErrorException;
import Game_Not_Used.sound.Items.Armor.Armor;
import Game_Not_Used.sound.Items.Rarity_Test.ArmorType;
import Game_Not_Used.sound.Items.Rarity_Test.Rarity;
import Game_Not_Used.sound.Items.Rarity_Test.Slot;
import Game_Not_Used.sound.Items.Rarity_Test.WeaponType;
import Game_Not_Used.sound.Items.Weapon.Weapon;
import Algorithms.ArrayListRandom;
import Game_Not_Used.sound.Rendering.AssetDrawing;

import java.util.Random;

public class ItemFactory {


    private final static int COMMON_ODDS = 40;
    private final static int UNCOMMON_ODDS = 25;
    private final static int RARE_ODDS = 20;
    private final static int EPIC_ODDS = 10;
    private final static int LEGENDARY_ODDS = 5;
    //Odds must be a 100 for full calculation, I might want a different system tho
    private final static int ODDS_SUM = COMMON_ODDS
                                                + UNCOMMON_ODDS
                                                + RARE_ODDS
                                                + EPIC_ODDS
                                                + LEGENDARY_ODDS;

    //TODO maybe these values can be public to access them somewhere else too
    public final static Slot HEAD = new Slot("Head");
    private final static Slot CHEST = new Slot("Chest");
    private final static Slot LEGS = new Slot("Legs");

    private final static Slot ONE_HAND = new Slot("One Hand");
    private final static Slot TWO_HAND = new Slot("Two Hand");

    private final static Slot RING = new Slot("Ring");
    private final static Slot TRINKET = new Slot("Trinket");

    private final static Rarity RARITY_COMMON = new Rarity("Common");
    private final static Rarity RARITY_UNCOMMON = new Rarity("Uncommon");
    private final static Rarity RARITY_RARE = new Rarity("Rare");
    private final static Rarity RARITY_EPIC = new Rarity("Epic");
    private final static Rarity RARITY_LEGENDARY = new Rarity("Legendary");

    private final static ArmorType CLOTH = new ArmorType("Cloth");
    private final static ArmorType LEATHER = new ArmorType("Leather");
    private final static ArmorType MAIL = new ArmorType("Mail");
    private final static ArmorType PLATE = new ArmorType("Plate");

    private final static WeaponType SWORD = new WeaponType("Sword");
    private final static WeaponType MACE = new WeaponType("Mace");
    private final static WeaponType AXE = new WeaponType("Axe");
    private final static WeaponType SHIELD = new WeaponType("Shield");

    private final static ArrayListRandom<Slot> slotsList = new ArrayListRandom<Slot>();
    private final static ArrayListRandom<Rarity> rarityList = new ArrayListRandom<Rarity>();
    private final static ArrayListRandom<ArmorType> armorList = new ArrayListRandom<ArmorType>();
    private final static ArrayListRandom<WeaponType> weaponList = new ArrayListRandom<WeaponType>();

    private final static ArrayListRandom<String> weaponNames = new ArrayListRandom<String>();
    private final static ArrayListRandom<String> armorNames = new ArrayListRandom<String>();

    Random random = new Random();

    public ItemFactory() throws SumErrorException {
        if (ODDS_SUM != 100) throw new SumErrorException(100);
        slotsList.add(HEAD);
        slotsList.add(CHEST);
        slotsList.add(LEGS);
        slotsList.add(ONE_HAND);
        slotsList.add(TWO_HAND);
        slotsList.add(RING);
        slotsList.add(TRINKET);

        rarityList.add(RARITY_COMMON);
        rarityList.add(RARITY_UNCOMMON);
        rarityList.add(RARITY_RARE);
        rarityList.add(RARITY_EPIC);
        rarityList.add(RARITY_LEGENDARY);

        armorList.add(CLOTH);
        armorList.add(LEATHER);
        armorList.add(MAIL);
        armorList.add(PLATE);

        weaponList.add(SWORD);
        weaponList.add(MACE);
        weaponList.add(AXE);
        weaponList.add(SHIELD);

        //TODO Maybe this should just be in the text file to be loaded
        weaponNames.add("Training");
        weaponNames.add("Magical");
        weaponNames.add("Copper");
        weaponNames.add("Steel");
        weaponNames.add("Runic");

        armorNames.add("Bloodstained");
        armorNames.add("Reinforced");
        armorNames.add("Magical");
        armorNames.add("Runic");
        armorNames.add("Training");

    }

    public Weapon createRandomWeapon(){

        //IF STATMENT HERE CHecking if we make one hand or two hand
        WeaponType weaponType = weaponList.getRandom();

        //todo slot needs to be pseudo random
        return new Weapon(weaponNames.getRandom() + " " + weaponType.getWeaponName(),
                ONE_HAND, rarityList.getRandom(), AssetDrawing.defaultImage,
                random.nextInt(3), random.nextInt(3), random.nextInt(3),
                weaponType, random.nextInt(3), random.nextInt(3));

    }

    public Armor createRandomArmor(){

        //IF STATMENT HERE CHecking if we make one hand or two hand
        ArmorType armorType = armorList.getRandom();
        //todo slot needs to be pseudo random
        return new Armor(weaponNames.getRandom() + " " + armorType.getArmorName(),
                ONE_HAND, rarityList.getRandom(), AssetDrawing.defaultImage,
                random.nextInt(3), random.nextInt(3), random.nextInt(3),
                armorType, random.nextInt(3));
    }
}

