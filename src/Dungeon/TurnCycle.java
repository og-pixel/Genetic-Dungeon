package Dungeon;
import Unit.BasicUnit;

public class TurnCycle implements Runnable{
    DungeonGenerator dungeonGenerator;


    TurnCycle(DungeonGenerator dungeonGenerator){
        this.dungeonGenerator = dungeonGenerator;
        this.dungeonGenerator.attachObserver(this);
    }

    public void run(){
//        BasicUnit player = dungeonGenerator.dungeonMatrix.get(dungeonGenerator.heroLocationY).get(dungeonGenerator.heroLocationX).getBasicUnit();
//        player.setNoOfMoves(3);
    }

    public void update(){
        System.out.println("TODO I CAN FINISH TURN HERE");
//        Unit player = dungeonGenerator.dungeonMatrix.get(dungeonGenerator.heroLocationY).get(dungeonGenerator.heroLocationX).getBasicUnit();
//        if(player.getNoOfMoves() < 1){
//            System.out.println("Hero's life left: " + player.getHealth());
//        }
    }

}
