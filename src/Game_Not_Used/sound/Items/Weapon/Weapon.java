package Game_Not_Used.sound.Items.Weapon;

import Game_Not_Used.sound.Items.AbstractItem;
import Game_Not_Used.sound.Items.Rarity_Test.Rarity;
import Game_Not_Used.sound.Items.Rarity_Test.Slot;
import Game_Not_Used.sound.Items.Rarity_Test.WeaponType;
import javafx.scene.image.Image;

public class Weapon extends AbstractItem {

    private WeaponType weaponType;
    private int weaponDamage;
    private int weaponRange;


    public Weapon(String itemName, Slot itemSlot, Rarity itemRarity, Image itemImage,
                      int strengthIncrease, int agilityIncrease, int intelligenceIncrease,
                      WeaponType weaponType, int weaponDamage, int weaponRange) {

        super(itemName, itemSlot, itemRarity, itemImage, strengthIncrease, agilityIncrease, intelligenceIncrease);
        this.weaponType = weaponType;
        this.weaponDamage = weaponDamage;
        this.weaponRange = weaponRange;
    }

    public Weapon(){

    }





    public String getInfo() {
        return super.getInfo() +
                "\nWeapon: " + weaponType.getWeaponName() +
                "\nDamage: " + weaponDamage +
                "\nRange: " + weaponRange;
    }

    public String checkWeaponType(){
//        if(weaponType == 0) return "Sword";
//        else if(weaponType == 1) return "Mace";
//        else if(weaponType == 2) return "Axe";
//        else if(weaponType == 3) return "Shield";
//        else return "error";
        return "error";
    }

}
