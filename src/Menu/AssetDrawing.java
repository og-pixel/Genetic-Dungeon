package Menu;

import Errors.VariableBoundsIncorrect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AssetDrawing {

    private static final int TILE_SIZE = Menu.TILE_SIZE;

    //Classes
    public final static Image warriorImage = new Image("img/Characters and Creatures/warrior.png");
    public final static Image hunterImage = new Image("img/Characters and Creatures/hunter.png");
    public final static Image mageImage = new Image("img/Characters and Creatures/mage.png");

    //Images to be loaded
    public final static Image wallImage = new Image("img/wall.png");
    public final static Image corridorImage = new Image("img/64corridor.png");
    public final static Image roomImage = new Image("img/roomFloor.png");
    public final static Image doorImage = new Image("img/gate.png");
    public final static Image rubbleImage = new Image("img/rubble.png");
    public final static Image swordInStoneImage = new Image("img/swordInStone.png");
    public final static Image solderImage = new Image("img/Warcraft 3/footman.png");

    //Items
    public final static Image swordImage = new Image("img/Weapons/Hand_31.png");

    //Frames
    public final static Image generalFrame = new Image("img/frame.png");

    //Icons
    public final static Image playerAvatar = new Image("img/footman.png");
    public final static Image banditAvatar = new Image("img/Warcraft 3/banditspearthrower.png");
    public final static Image gnollAvatar = new Image("img/Warcraft 3/gnolloverseer.png");
    public final static Image dotImage = new Image("img/dot.png");

    //TEMPImages, should only be used during debugging or errors
    public final static Image defaultImage = new Image("img/default.png");

    //Other, icons etc.
    public final static Image burningImage = new Image("img/Spells/Burnout.png");
    public final static Image blessingImage = new Image("img/Spells/BlessingOfProtection.png");


    /**
     * Takes a picture that first a square and makes it smaller by a percentage specified
     */
    public static void drawPicture(GraphicsContext gc, int x, int y, Image genericImage){
        gc.drawImage(genericImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    public static void drawPictureSmaller(GraphicsContext gc, int x, int y, Image genericImage, double offPercentage) {
        if (offPercentage > 0.99 || offPercentage < 0.01)
            throw new VariableBoundsIncorrect("Values only between 0.01 and 0.99");
        gc.drawImage(genericImage, x * TILE_SIZE + (TILE_SIZE * offPercentage / 2),
                                    y * TILE_SIZE + (TILE_SIZE * offPercentage / 2),
                                        TILE_SIZE - (TILE_SIZE * offPercentage),
                                        TILE_SIZE - (TILE_SIZE * offPercentage));
    }

    public static void drawPicture2(GraphicsContext gc, int x, int y, Image genericImage, int w, int h){
        gc.drawImage(genericImage, x * TILE_SIZE, y * TILE_SIZE, w, h);
    }
}