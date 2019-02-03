import Dungeon.Dungeon;

import java.util.ArrayList;

public class Interpreter {

    private final String CREATE = "-c";

    public Interpreter(String... args){
        interpretArguments(args);
    }

    private void interpretArguments(String... args){
        if(args[0].equals(CREATE)){
            System.out.println("Create");
        }
    }




    private ArrayList<Dungeon> generatePopulation(int width, int height, int amount){
        ArrayList<Dungeon> list = new ArrayList<>();
        for(int i = 0; i < amount; i++){
            list.add(new Dungeon(width, height));
        }
        return list;
    }

}
