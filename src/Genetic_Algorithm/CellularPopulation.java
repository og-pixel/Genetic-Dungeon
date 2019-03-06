package Genetic_Algorithm;

import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Wall;

import java.util.ArrayList;

//This version creates a cellular map with start and end point
public class CellularPopulation implements PopulationImp {
    @Override
    public ArrayList<Dungeon> createPopulation(int width, int height, int numberOfMaps, double odds) {
        ArrayList<Dungeon> mapList = new ArrayList<>();

        for(int i = 0; i < numberOfMaps; i++){
                mapList.add(new Dungeon(width, height));
                mapList.get(i).getDungeonMatrix().cellularAutomate(new Corridor(0,0), new Wall(0,0), odds);//todo position is wrong
                mapList.get(i).createStartPosition();
                mapList.get(i).createEndPosition();
        }

        return mapList;
    }
}
