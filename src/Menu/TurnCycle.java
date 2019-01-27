package Menu;
import Unit.BasicUnit;

public class TurnCycle implements Runnable{
    Dungeon dungeon;


    TurnCycle(Dungeon dungeon){
        this.dungeon = dungeon;
        this.dungeon.attachObserver(this);
    }

    public void run(){
        BasicUnit player = dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit();
        player.setNoOfMoves(3);
    }

    public void update(){
        System.out.println("TODO I CAN FINISH TURN HERE");
//        Unit player = dungeon.dungeonMatrix.get(dungeon.heroLocationY).get(dungeon.heroLocationX).getBasicUnit();
//        if(player.getNoOfMoves() < 1){
//            System.out.println("Hero's life left: " + player.getHealth());
//        }
    }

}
