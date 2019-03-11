package Game_Not_Used.sound.Items;

import Game_Not_Used.sound.Items.Rarity_Test.Rarity;
import Game_Not_Used.sound.Items.Rarity_Test.Slot;
import Game_Not_Used.sound.Unit.BasicUnit;
import javafx.scene.image.Image;

public abstract class AbstractItem implements Item {

    protected Slot itemSlot;
    protected Rarity itemRarity;

    protected Image itemImage;

    protected String itemName;
    protected int strengthIncrease, agilityIncrease, intelligenceIncrease;

    public AbstractItem(String itemName, Slot itemSlot, Rarity itemRarity, Image itemImage, int strengthIncrease, int agilityIncrease, int intelligenceIncrease) {
        this.itemName = itemName;
        this.itemSlot = itemSlot;
        this.itemRarity = itemRarity;
        this.itemImage = itemImage;
        this.strengthIncrease = strengthIncrease;
        this.agilityIncrease = agilityIncrease;
        this.intelligenceIncrease = intelligenceIncrease;
    }
    public AbstractItem(){

    }


//
//
//    public String checkItemSlot(){
//        if(itemSlot == 0) return "Head";
//        else if(itemSlot == 1) return "Chest";
//        else if(itemSlot == 2) return "Legs";
//        else if(itemSlot == 3) return "One Hand";
//        else if(itemSlot == 4) return "Two Hand";
//        else if(itemSlot == 5) return "Ring";
//        else if(itemSlot == 6) return "Trinket";
//        else return "error";
//    }
//
//    public String checkItemRarity(){
//        if(itemSlot == 0) return "Common";
//        else if(itemSlot == 1) return "Uncommon";
//        else if(itemSlot == 2) return "Rare";
//        else if(itemSlot == 3) return "Epic";
//        else if(itemSlot == 4) return "Legendary";
//        else return "error";
//    }
//
    public String getInfo(){
        return  "\nItem Name: " + itemName +
                "\nSlot: " + itemSlot.getSlotName() +
                "\nRarity: " + itemRarity.getRarityName() +
                "\nStrength: " + strengthIncrease +
                "\nAgility: " + agilityIncrease +
                "\nIntelligence: " + intelligenceIncrease;
    }
//
//
//
//        return "Slot: " + checkItemSlot() + "\n" +
//                "Rarity: " + checkItemRarity() + "\n";
//
//    }


    @Override
    public void itemEffect() {

    }

    @Override
    public void equipItem(BasicUnit basicUnit) {
    }
}
