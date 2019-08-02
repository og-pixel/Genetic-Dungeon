package Genetic_Algorithm.Data;

import Algorithms.Algorithms;
import Chromosome.*;

import java.io.IOException;
import java.util.ArrayList;

public class EvolutionResults {

    //Each row represents an entire generation
    //Number of columns is the generation size
    //Number of rows is how many generations were there
    private ArrayList<ArrayList<Chromosome>> generationsList;

    public EvolutionResults(){
        generationsList = new ArrayList<>();
    }

    public void addGeneration(ArrayList<Chromosome> row){
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

    public Chromosome findBest(){
        Chromosome best = generationsList.get(0).get(0);
        for (ArrayList<Chromosome> chromosomes : generationsList) {
            for (Chromosome chromosome : chromosomes) {
                if (best.getFitnessScore() < chromosome.getFitnessScore()) best = chromosome;
            }
        }
        return best;
    }
}
