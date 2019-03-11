package Game_Not_Used.sound.Items.Armor;

import Game_Not_Used.sound.Items.AbstractItem;
import Game_Not_Used.sound.Items.Rarity_Test.ArmorType;
import Game_Not_Used.sound.Items.Rarity_Test.Rarity;
import Game_Not_Used.sound.Items.Rarity_Test.Slot;
import javafx.scene.image.Image;

public class Armor extends AbstractItem {

    private ArmorType armorType;
    private int armorAmount;
//    private int weaponRange;


    public Armor(String itemName, Slot itemSlot, Rarity itemRarity, Image itemImage,
                 int strenghtIncrease, int agilityIncrease, int intelligenceIncrease,
                 ArmorType armorType, int armorAmount) {

        super(itemName, itemSlot, itemRarity, itemImage, strenghtIncrease, agilityIncrease, intelligenceIncrease);
        this.armorType = armorType;
        this.armorAmount = armorAmount;
    }

    public Armor(){

    }





    public String getInfo() {
        return super.getInfo() +
                "\nArmor: " + armorType.getArmorName() +
                "\nArmor Amount: " + armorAmount;
    }

    public String checkArmorType(){
//        if(armorType == 0) return "Sword";
//        else if(armorType == 1) return "Mace";
//        else if(armorType == 2) return "Axe";
//        else if(armorType == 3) return "Shield";
//        else return "error";
        return "error";
    }

}
