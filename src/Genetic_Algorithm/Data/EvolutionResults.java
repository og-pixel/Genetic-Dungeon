package Genetic_Algorithm.Data;

import Algorithms.Algorithms;
import Map.GameMap;

import java.io.IOException;
import java.util.ArrayList;

public class EvolutionResults {

    //Each row represents an entire generation
    //Number of columns is the generation size
    //Number of rows is how many generations were there
    private ArrayList<ArrayList<GameMap>> generationsList;

    public EvolutionResults(){
        generationsList = new ArrayList<>();
    }

    public void addGeneration(ArrayList<GameMap> row){
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

    public void saveAllResults(String outputDirectory){
        for (int i = 0; i < generationsList.size(); i++) {
            for (int j = 0; j < generationsList.get(i).size(); j++) {
                try {
                    Algorithms.writeToFile(outputDirectory +"Row: " + i + " Column: " + j, generationsList.get(i).get(j));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public GameMap findBest(){
        GameMap best = generationsList.get(0).get(0);
        for (ArrayList<GameMap> gameMaps : generationsList) {
            for (GameMap gameMap : gameMaps) {
                if (best.getFitnessScore() < gameMap.getFitnessScore()) best = gameMap;
            }
        }
        return best;
    }
}
