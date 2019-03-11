package Dungeon;

public class TurnCycle implements Runnable{
    DungeonGenerator dungeonGenerator;


    TurnCycle(DungeonGenerator dungeonGenerator){
        this.dungeonGenerator = dungeonGenerator;
    }

    public void run(){
//        BasicUnit player = dungeonGenerator.dungeonMatrix.get(dungeonGenerator.heroLocationY).get(dungeonGenerator.heroLocationX).getBasicUnit();
//        player.setNoOfMoves(3);
    }

    public void update(){
        System.out.println("TODO I CAN FINISH TURN HERE");
//        Game_Not_Used.sound.Unit player = dungeonGenerator.dungeonMatrix.get(dungeonGenerator.heroLocationY).get(dungeonGenerator.heroLocationX).getBasicUnit();
//        if(player.getNoOfMoves() < 1){
//            System.out.println("Hero's life left: " + player.getHealth());
//        }
    }

}
