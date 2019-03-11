package Game_Not_Used.sound.Items;

import Game_Not_Used.sound.Unit.BasicUnit;

public interface Item {

    void equipItem(BasicUnit basicUnit);
    void itemEffect();
    String getInfo();
}
