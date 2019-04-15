package Algorithms.CA;

import Algorithms.Matrix;
import Dungeon.*;

//TODO rule number might be wrong
public class Rule20CellularAutomate implements CellularAutomateImp, TileList {
    @Override
    public Matrix generateMap(Matrix map) {
        int mapHeight = map.getHeight();
        int mapWidth = map.getWidth();

        Matrix newMap = new Matrix(mapWidth, mapHeight);
        newMap.fillMatrix(-1);

        //Very silly implementation
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                int count = 0;

                    if (map.getUp(x, y) == WALL) count++;
                    if (map.getDown(x, y) == WALL) count++;
                    if (map.getRight(x, y) == WALL) count++;
                    if (map.getLeft(x, y) == WALL) count++;

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
        return newMap;
    }
}
