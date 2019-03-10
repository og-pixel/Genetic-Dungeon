package Genetic_Algorithm;

import Exceptions.VariableBoundsException;

public abstract class AbstractFitness implements FitnessImp {

    private double strength;

    public AbstractFitness(double strength){
        if (strength < 0 || strength > 1)throw new VariableBoundsException(0,1);
        this.strength = strength;
    }
}
