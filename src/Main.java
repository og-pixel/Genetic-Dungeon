import Dungeon.Dungeon;
import Dungeon.Tile.Corridor;
import Dungeon.Tile.Tile;
import Dungeon.Tile.Wall;
import com.sun.javafx.scene.traversal.Algorithm;
import javafx.application.Application;
import Algorithms.*;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        ArrayList<Individual> population;
//        population = new ArrayList<>();
//        for(int i = 0; i < 1000; i++){
//            population.add(new Individual(100));
//        }
//
//        Fitness.runPopulation(population);

//        Application.launch(Views.Menu.class, args);
        Random random = new Random();

        ArrayList<Dungeon> population = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            population.add(new Dungeon(20,20,
                    10, 5, 65, 10));
            for(int x = 0; x < population.get(population.size() - 1).getDungeonWidth(); x++){
                for(int y = 0; y < population.get(population.size() - 1).getDungeonHeight(); y++){

                    if(random.nextFloat() > 0.85)population.get(population.size() - 1).getDungeonMatrix().get(x).set(y, new Wall(0,0));
                        else population.get(population.size() - 1).getDungeonMatrix().get(x).set(y, new Corridor(0,0));
                }
            }
        }
        Algorithms.floodFill(population.get(0));
        for(int a = 0; a < population.get(0).getDungeonWidth(); a++){
            System.out.println();
            for(int b = 0; b < population.get(0).getDungeonHeight(); b++){
                if(Algorithms.visitMap.get(a).get(b)) System.out.print("V");
                else System.out.print("X");
            }
        }
    }
}
