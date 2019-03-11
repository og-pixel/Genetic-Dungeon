package Genetic_Algorithm.Population;

import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Wall;
import Exceptions.VariableBoundsException;

import java.util.ArrayList;
import java.util.Random;

//This version creates a noise map with start and end point
public class NoisePopulation implements PopulationImp {
    @Override
    public ArrayList<Dungeon> createPopulation(int width, int height, int numberOfMaps, double odds) {
        if (odds < 0.1 || odds > 1) throw new VariableBoundsException(0.1, 1.0); //TOOD i am not sure if i need that

        ArrayList<Dungeon> mapList = new ArrayList<>();
        for (int i = 0; i < numberOfMaps; i++) {
            mapList.add(new Dungeon(width, height));
            mapList.get(i).getDungeonMatrix().cellularAutomate(new Corridor(0, 0), new Wall(0, 0), odds);//todo position is wrong

            Random random = new Random();
            for (int j = 0; j < mapList.size(); j++) {


                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (random.nextDouble() > odds) mapList.get(j).getDungeonMatrix().put(x, y, new Corridor(x, y));
                        else mapList.get(j).getDungeonMatrix().put(x, y, new Wall(x, y));
                    }
                }
            }
            mapList.get(i).createStartPosition();
            mapList.get(i).createEndPosition();
        }
        return mapList;
    }
}
