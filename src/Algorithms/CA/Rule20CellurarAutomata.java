package Algorithms.CA;

import Algorithms.Matrix;
import Dungeon.*;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Tile;
import Dungeon.Tile.Wall;

//TODO it works but I feel like it could be done much better!
public class Rule20CellurarAutomata implements CellurarAutomataImp, TileList {
    @Override
    public Matrix generateMap(Matrix map) {
        int mapHeight = map.getHeight();
        int mapWidth = map.getWidth();

        Matrix newMap = new Matrix(mapWidth, mapHeight);
        newMap.fillMatrix(-1);//todo it was null


        //Very silly implementation
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int count = 0;
//                if(map.getElement(x, y) instanceof Wall) {

                    if (map.getUp(x, y) == WALL) count++;
                    if (map.getDown(x, y) == WALL) count++;
                    if (map.getRight(x, y) == WALL) count++;
                    if (map.getLeft(x, y) == WALL) count++;



                    //TODO lol
                    if((x - 1) > 0 && (y - 1) > 0){
                        if (map.getElement(x - 1, y - 1) == WALL) count++;
                    }

                    if((x + 1) < mapWidth && (y - 1) > 0){
                        if (map.getElement(x + 1, y - 1) == WALL) count++;
                    }

                    if((x + 1) < mapWidth && (y + 1) < mapHeight){
                        if (map.getElement(x + 1, y + 1) == WALL) count++;
                    }

                    if((x - 1) > 0 && (y + 1) < mapHeight){
                        if (map.getElement(x - 1, y + 1) == WALL) count++;
                    }



                    if(count >= 3 /*&& count < 6 */)newMap.put(x, y, WALL);
                    else newMap.put(x, y, CORRIDOR);

            }
        }

        //TODO returning should be better I feel
        return newMap;
    }
}
