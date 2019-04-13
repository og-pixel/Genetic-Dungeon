package Genetic_Algorithm.Data;

import Algorithms.Algorithms;
import Dungeon.Dungeon;

import java.io.IOException;
import java.util.ArrayList;

//TODO I am not sure how to name this class correctly but the idea is to save all dungeon maps to this class and later extract information that is needed
public class EvolutionDetails {

    private ArrayList<ArrayList<Dungeon>> list;
    public EvolutionDetails(){
        list = new ArrayList<>();
        //        for (int i = 0; i < numberOfRows; i++) {
//            list.add(new ArrayList<>());
//        }
    }









    public boolean addRow(ArrayList<Dungeon> row){
        try{
            list.add(row);
        }catch (Exception e){
            System.out.println("unexpected ");
            System.exit(1);
        }


        return true;
    }


    public void printResults(){
        for (int i = 0; i < list.size(); i++) {
            try {
                Algorithms.writeToFile("Row: " + i, list.get(i).get(0));
                System.out.println(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
