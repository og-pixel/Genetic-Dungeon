package Game_Not_Used.sound.Magic;
import Game_Not_Used.sound.Unit.BasicUnit;
import javafx.scene.image.Image;

public abstract class Passive implements Debuff, Buff{

    public int timeRemaining;
    protected BasicUnit targetedHero;
    private Image passiveImage;

    public Passive(Image image){
        passiveImage = image;
    }

    public Image getPassiveImage(){
        return passiveImage;
    }

    @Override
    public void tick(BasicUnit player) {
        timeRemaining--;
        if (timeRemaining >= 0) {
            tickReaction(targetedHero);
        }
    }
}
