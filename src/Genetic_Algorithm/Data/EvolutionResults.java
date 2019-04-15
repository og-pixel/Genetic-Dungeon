package Genetic_Algorithm.Data;

import Algorithms.Algorithms;
import Map.Map;

import java.io.IOException;
import java.util.ArrayList;

public class EvolutionResults {

    //Each row represents an entire generation
    //Number of columns is the generation size
    //Number of rows is how many generations were there
    private ArrayList<ArrayList<Map>> generationsList;

    public EvolutionResults(){
        generationsList = new ArrayList<>();
    }

    //TODO make sure the return value is used
    public boolean addGeneration(ArrayList<Map> row){
        try{
            generationsList.add(row);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public void saveResults(){
        for (int i = 0; i < generationsList.size(); i++) {
            try {
                Algorithms.writeToFile("Row: " + i, generationsList.get(i).get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map findBest(){
        Map best = generationsList.get(0).get(0);
        for (ArrayList<Map> maps : generationsList) {
            for (Map map : maps) {
                if (best.getFitnessScore() < map.getFitnessScore()) best = map;
            }
        }
        return best;
    }
}
