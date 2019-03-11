package Game_Not_Used.sound.Magic;

import Game_Not_Used.sound.Unit.BasicUnit;

public interface Debuff{
    void tick(BasicUnit player);
    void tickReaction(BasicUnit target);

}
