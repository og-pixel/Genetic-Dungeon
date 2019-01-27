package Items;

import Unit.BasicUnit;

public interface Item {

    void equipItem(BasicUnit basicUnit);
    void itemEffect();
    String getInfo();
}
