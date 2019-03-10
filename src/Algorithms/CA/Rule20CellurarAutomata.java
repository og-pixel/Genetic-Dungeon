package Algorithms.CA;

import Algorithms.Matrix;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Tile;
import Dungeon.Tile.Wall;

//TODO it works but I feel like it could be done much better!
public class Rule20CellurarAutomata implements CellurarAutomataImp {
    @Override
    public Matrix<Tile> generateMap(Matrix<Tile> map) {
        int mapHeight = map.getHeight();
        int mapWidth = map.getWidth();

        Matrix<Tile> newMap = new Matrix<Tile>(mapWidth, mapHeight);
        newMap.fillMatrix(null);


        //Very silly implementation
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int count = 0;
//                if(map.getElement(x, y) instanceof Wall) {

                    if (map.getUp(x, y) instanceof Wall) count++;
                    if (map.getDown(x, y) instanceof Wall) count++;
                    if (map.getRight(x, y) instanceof Wall) count++;
                    if (map.getLeft(x, y) instanceof Wall) count++;



                    //TODO lol
                    if((x - 1) > 0 && (y - 1) > 0){
                        if (map.getElement(x - 1, y - 1) instanceof Wall) count++;
                    }

                    if((x + 1) < mapWidth && (y - 1) > 0){
                        if (map.getElement(x + 1, y - 1) instanceof Wall) count++;
                    }

                    if((x + 1) < mapWidth && (y + 1) < mapHeight){
                        if (map.getElement(x + 1, y + 1) instanceof Wall) count++;
                    }

                    if((x - 1) > 0 && (y + 1) < mapHeight){
                        if (map.getElement(x - 1, y + 1) instanceof Wall) count++;
                    }



                    if(count > 3 && count < 6)newMap.put(x, y, new Wall(x, y));
                    else newMap.put(x, y, new Corridor(x, y));

//                }else{
//                    newMap.put(x, y, new Corridor(x, y));
//                }





            }
        }



        //TODO returning should be better I feel
        return newMap;
    }
}
