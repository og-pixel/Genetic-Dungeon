import Algorithms.CA.CellurarAutomataImp;
import Algorithms.CA.Rule20CellurarAutomata;
import Algorithms.Matrix;
import Dungeon.*;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Tile;
import Dungeon.Tile.Wall;

public class Main {
    public static void main(String[] args){


        new Interpreter(args);

//        Dungeon dungeon = new Dungeon(30, 10);
//        dungeon.getDungeonMatrix().cellularAutomate(new Wall(0,0),new Corridor(0,0), 0.5);//todo as per usualy corrdinates are wrong
//
//        System.out.println(dungeon.dungeonToString());
//
//        CellurarAutomataImp ca = new Rule20CellurarAutomata();
//
//
//        Matrix<Tile> zz = ca.generateMap(dungeon.getDungeonMatrix());
//        Dungeon newDung = new Dungeon(zz);
//        System.err.println(newDung.dungeonToString());
//
//        for (int i = 0; i < 5; i++) {
//            zz = ca.generateMap(newDung.getDungeonMatrix());
//            newDung = new Dungeon(zz);
//            System.err.println(newDung.dungeonToString());
//        }

    }
}
