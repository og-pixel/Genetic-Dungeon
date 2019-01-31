package Dungeon_GA.Individual;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Fitness {
    static int work = 0;
    static int iteration = 0;
    static int mutations = 0;
    private static final String modelAnswer = "mynameismiloszjakubanisandiamastudentatuniversityofhuddersfield";


    /**
     * Evaluates an individual based on how close the string
     * is to the "Hello World" string
     * @param individual
     * @return
     */
    private static int evaluateGene(Individual individual){
        Chromosome individualChromosome = individual.getChromosome();
        int score = 0;

        for(int i = 0; i < modelAnswer.length(); i++){
            if(individualChromosome.getGene(i) == modelAnswer.charAt(i)){
                score++;
            }
        }
        return score;
    }


    private static void crossoverPopulation(ArrayList<Individual> population){
        int bestPercentage = (int) (population.size() * 0.1); //10% of population will be chosen to mate
        double mutationChance = 0.10; //10% chance for each gene to be affected
        int crossoverRange = population.get(0).getChromosome().getGeneLength(); //todo Just range where we can cross genes
        //todo crossover range might need to -1 maybe even -2

        //Error Checking
        if(bestPercentage < 1)throw new RuntimeException("Starting population needs to be big enough");



        ArrayList<Individual> bestOfThePopulation = new ArrayList<>();

        //Get the top 10% of the population for breeding
        int count = 0;
        while(bestOfThePopulation.size() < bestPercentage){
            bestOfThePopulation.add(population.get(population.size() - count - 1));
            count++;
        }


        Random random = new Random();
        //todo I think I should cross all parents, 5 individuals give 10 combinations
        // 10 would give 45
        ArrayList<Individual> nextGeneration = new ArrayList<>();
        while(nextGeneration.size() < 1000){
            //todo same parent can occur, i think its bad
            Individual parent1 = bestOfThePopulation.get(random.nextInt(bestOfThePopulation.size()));
            Individual parent2 = bestOfThePopulation.get(random.nextInt(bestOfThePopulation.size()));

            int crossoverPoint = random.nextInt(crossoverRange); //between 0 and 9 but 9 is bad too (nothing to cross over?)

            Individual child1 = deepClone(parent1);
            Individual child2 = deepClone(parent2);

//            System.out.println("Crossover Point is: " + crossoverPoint);
            for(int i = crossoverPoint; i < child1.getChromosome().getGeneLength(); i++){
                if(random.nextFloat() < mutationChance){
                    mutations++;
                    child1.getChromosome().setGeneAtPosition(i, (char) (random.nextInt(123 - 97) + 97));
                }else {
                    child1.getChromosome().setGeneAtPosition(i, parent2.getChromosome().getGene(i));
                }
            }

            for(int i = crossoverPoint; i < child2.getChromosome().getGeneLength(); i++){
                if(random.nextFloat() < mutationChance){
                    mutations++;
                    child2.getChromosome().setGeneAtPosition(i, (char) (random.nextInt(123 - 97) + 97));
                }else {
                    child2.getChromosome().setGeneAtPosition(i, parent1.getChromosome().getGene(i));
                }
            }

            nextGeneration.add(child1);
            nextGeneration.add(child2);
        }
        work++;
        iteration++;
        runPopulation(nextGeneration);
    }



    //todo weird name
    public static void runPopulation(ArrayList<Individual> population){


        for(Individual individual : population) {
            individual.setScore(Fitness.evaluateGene(individual));
        }

        //Sort list so last individuals on the list are the "best" ones
        // (with best scores)
        Collections.sort(population, Comparator.comparing(Individual::getScore));

//        System.out.println("Best of this population have score: " + population.get(population.size() - 1).getScore());
        if(work >= 100) {
            System.out.println("Example of best individual: " + population.get(population.size() - 1).getChromosome().getChromosomeArray());
            work = 0;
        }
        if(population.get(population.size() - 1).getScore() >= modelAnswer.length()){
            System.out.println("Best individual: " + population.get(population.size() - 1).getChromosome().getChromosomeArray());
            System.out.println("Found best individuals after: " + iteration + " iterations");
        }else {
            crossoverPopulation(population);
        }
    }

    //THis method is so fucking dumb
    public static Individual deepClone(Individual individual){
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(individual);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Individual) ois.readObject();
        }catch (Exception e) {
            return null;
        }
    }
}
