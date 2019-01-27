package Magic;

import Unit.BasicUnit;

public interface Debuff{
    void tick(BasicUnit player);
    void tickReaction(BasicUnit target);

}
