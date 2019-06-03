package Genetic_Algorithm.Data;

import Algorithms.Algorithms;
import Genetic_Algorithm.ChromosomeEvaluation.AttachLogChromosomeEvaluation;
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

    public void addGeneration(ArrayList<Map> row){
        try{
            generationsList.add(row);
        }catch (Exception e){
            e.printStackTrace();
        }
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

    public void saveAllResults(){
        for (int i = 0; i < generationsList.size(); i++) {
            for (int j = 0; j < generationsList.get(i).size(); j++) {
                try {
                    Algorithms.writeToFile("Row: " + i + " Column: " + j, generationsList.get(i).get(j));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //todo better name for string, it should be file directory to save
    public void saveAllResults(String string){
        System.out.println(string);

        try {
            Algorithms.writeToFile(string + "dungeon.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (int i = 0; i < generationsList.size(); i++) {
//            for (int j = 0; j < generationsList.get(i).size(); j++) {
//                try {
//                    Algorithms.writeToFile(string +"Row: " + i + " Column: " + j, generationsList.get(i).get(j));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
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
