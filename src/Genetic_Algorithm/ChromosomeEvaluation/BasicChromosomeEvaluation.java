    package Genetic_Algorithm.ChromosomeEvaluation;

import Algorithms.Algorithms;
import Dungeon.Dungeon;
import Exceptions.VariableBoundsException;
import Genetic_Algorithm.Data.EvolutionDetails;
import Genetic_Algorithm.Fitness.FitnessImp;
import Genetic_Algorithm.ManualCorrections.CorrectionEnum;
import Genetic_Algorithm.Mutation.MutationsEnum;
import Genetic_Algorithm.Offspring.OffspringEnum;
import Genetic_Algorithm.Premutation.PremutationEnum;
import Genetic_Algorithm.Selection.SelectionEnum;

import java.util.ArrayList;
import java.util.logging.*;

public class BasicChromosomeEvaluation extends AbstractChromosomeEvaluation {

    private static final Logger LOGGER = Logger.getLogger(BasicChromosomeEvaluation.class.getName());
    //TODO need to work on top pop and pop size, they are slighlty silly
    private double TOP_POP;
    private double POP_SIZE;

    private ArrayList<Dungeon> mapList;
    private ArrayList<FitnessImp> fitnessImpList;
    private int numberOfGenerations;
    private MutationsEnum mutation;
    private SelectionEnum selection;
    private PremutationEnum premutation;
    private CorrectionEnum correction;
    private OffspringEnum offspring;

    public BasicChromosomeEvaluation(double topPopulation, double populationSize,
                                     ArrayList<Dungeon> mapList, ArrayList<FitnessImp> fitnessImpList,
                                     int numberOfGenerations,
                                     MutationsEnum mutation, SelectionEnum selection, PremutationEnum premutation,
                                     CorrectionEnum correction, OffspringEnum offspring){
        LOGGER.setLevel(Level.WARNING);
        if(topPopulation < 0.1 || topPopulation > 1)throw new VariableBoundsException(0.1, 1);


        if(populationSize > 1000) System.err.println("Population size is beyond 1000, program might take a long time and" +
                "results probably won't be better");

        if(topPopulation != 0.1) System.err.println("Default crossover behaviour recommends 0.1 (10%) of the best " +
                "population to mate");

        POP_SIZE = populationSize;
        TOP_POP = topPopulation * populationSize;

        //If there are less maps than to make even 1, then we have to force it
        if(TOP_POP < 1)TOP_POP = 1;//todo consider 2




        //TODO consctreuctor now can determinate whenever yoou can use the method or not (it can have methods to complete what's missing)
        this.mapList = mapList;
        this.fitnessImpList = fitnessImpList;
        this.numberOfGenerations = numberOfGenerations;
        this.mutation = mutation;
        this.selection = selection;
        this.premutation = premutation;
        this.correction = correction;
        this.offspring = offspring;
    }

    @Override
    public EvolutionDetails crossoverPopulation() {

        //Setup Phase
        ArrayList<Dungeon> newPopulation;
         //todo this is new data structure
        EvolutionDetails evolutionDetails = new EvolutionDetails();

        double iteration = numberOfGenerations * 0.01; //every 1%
        int percentageDone = 0;

        //Run Project
        for (int generation = 0; generation < numberOfGenerations; generation++) {

            //Evaluate all dungeon based on all fitness implementations on the list
            for (FitnessImp fitnessImp : fitnessImpList) {
                for (Dungeon dungeon : mapList) fitnessImp.evaluateDungeon(dungeon);
            }

            //Correct Maps
            for (Dungeon dungeon : mapList) correction.correct(dungeon);

            //Selection
            mapList = selection.selectFitIndividuals(mapList);
            if(generation % iteration == 0) {
                //TODO while this isnt really necessary, it is nice for debugging
                LOGGER.log(Level.INFO, generation + "th generation\nTop Speciment Score: "
                        + mapList.get(0).getScore() + "\nTop Speciment Number of Rooms: "
                        + mapList.get(0).getNumberOfRooms() + "\n");
                percentageDone++;
                System.out.println(percentageDone + "%");
            }

            evolutionDetails.addRow(Algorithms.deepClone(mapList));

            //TODO WORK HERE, its offspring generator
            newPopulation = offspring.createNewGeneration(mapList, POP_SIZE, TOP_POP);


            mutation.mutateDungeons(newPopulation);//TODO it might not mutate sort makes no sense as i didnt evaluate it again

            mapList = newPopulation;//TODO it might make it work or not
//            newPopulation.sort(Comparator.comparing(Dungeon::getScore).reversed());
        }

        return evolutionDetails;
    }
}