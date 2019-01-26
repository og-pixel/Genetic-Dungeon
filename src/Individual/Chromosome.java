package Individual;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Chromosome implements Serializable {

    private ArrayList<Character> chromosomeArray;
    private int geneLength;
    private Random random = new Random();


    public Chromosome(int geneLength){
        this.chromosomeArray = new ArrayList<>();
        this.geneLength = geneLength;
        for(int i = 0; i < geneLength; i++){
            int nextChar = random.nextInt(122 - 97) + 97;
            chromosomeArray.add((char) nextChar);
        }
    }

    public int getGeneLength() {
        return geneLength;
    }

    public ArrayList<Character> getChromosomeArray() {
        return chromosomeArray;
    }

    //TODO this name might be confusing
    public Character getGene(int position){
        return chromosomeArray.get(position);
    }

    public void setGeneAtPosition(int position, Character newGene){
        chromosomeArray.set(position, newGene);
    }

}
