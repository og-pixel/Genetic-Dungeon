package Individual;

import java.io.Serializable;

public class Individual implements Serializable {

    private Chromosome chromosome;
    private Integer score;


    public Individual(int geneLength){
        this.chromosome = new Chromosome(geneLength);
    }

    public Individual(Chromosome chromosome){
        this.chromosome = chromosome;
    }
    public Chromosome getChromosome() {
        return chromosome;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

}
