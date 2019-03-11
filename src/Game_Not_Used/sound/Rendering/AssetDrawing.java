package Game_Not_Used.sound.Rendering;

import Exceptions.VariableBoundsException;
import Game_Not_Used.sound.Views.Menu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AssetDrawing {

    private static final int TILE_SIZE = Menu.TILE_SIZE;

    //Classes
    public final static Image warriorImage = new Image("Game_Not_Used/sound/img/Characters and Creatures/warrior.png");
    public final static Image hunterImage = new Image("Game_Not_Used/sound/img/Characters and Creatures/hunter.png");
    public final static Image mageImage = new Image("Game_Not_Used/sound/img/Characters and Creatures/mage.png");

    //Images to be loaded
    public final static Image wallImage = new Image("Game_Not_Used/sound/img/wall.png");
    public final static Image corridorImage = new Image("Game_Not_Used/sound/img/64corridor.png");
    public final static Image roomImage = new Image("Game_Not_Used/sound/img/roomFloor.png");
    public final static Image doorImage = new Image("Game_Not_Used/sound/img/gate.png");
    public final static Image rubbleImage = new Image("Game_Not_Used/sound/img/rubble.png");
    public final static Image swordInStoneImage = new Image("Game_Not_Used/sound/img/swordInStone.png");
    public final static Image solderImage = new Image("Game_Not_Used/sound/img/Warcraft 3/footman.png");

    //Game_Not_Used.sound.Items
    public final static Image swordImage = new Image("Game_Not_Used/sound/img/Weapons/Hand_31.png");

    //Frames
    public final static Image generalFrame = new Image("Game_Not_Used/sound/img/frame.png");

    //Icons
    public final static Image playerAvatar = new Image("Game_Not_Used/sound/img/footman.png");
    public final static Image banditAvatar = new Image("Game_Not_Used/sound/img/Warcraft 3/banditspearthrower.png");
    public final static Image gnollAvatar = new Image("Game_Not_Used/sound/img/Warcraft 3/gnolloverseer.png");
    public final static Image dotImage = new Image("Game_Not_Used/sound/img/dot.png");

    //TEMPImages, should only be used during debugging or errors
    public final static Image defaultImage = new Image("Game_Not_Used/sound/img/default.png");

    //Other, icons etc.
    public final static Image burningImage = new Image("Game_Not_Used/sound/img/Spells/Burnout.png");
    public final static Image blessingImage = new Image("Game_Not_Used/sound/img/Spells/BlessingOfProtection.png");


    /**
     * Takes a picture that first a square and makes it smaller by a percentage specified
     */
    public static void drawPicture(GraphicsContext gc, int x, int y, Image genericImage){
        gc.drawImage(genericImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    public static void drawPictureSmaller(GraphicsContext gc, int x, int y, Image genericImage, double offPercentage) {
        if (offPercentage > 0.99 || offPercentage < 0.01)
            throw new VariableBoundsException("Values only between 0.01 and 0.99");
        gc.drawImage(genericImage, x * TILE_SIZE + (TILE_SIZE * offPercentage / 2),
                                    y * TILE_SIZE + (TILE_SIZE * offPercentage / 2),
                                        TILE_SIZE - (TILE_SIZE * offPercentage),
                                        TILE_SIZE - (TILE_SIZE * offPercentage));
    }

    public static void drawPicture2(GraphicsContext gc, int x, int y, Image genericImage, int w, int h){
        gc.drawImage(genericImage, x * TILE_SIZE, y * TILE_SIZE, w, h);
    }
}