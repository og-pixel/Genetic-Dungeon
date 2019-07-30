package Genetic_Algorithm.ChromosomeEvaluation;

import java.util.logging.Logger;

public abstract class AbstractChromosomeEvaluation implements ChromosomeEvaluation {

    public static final Logger logger = Logger.getLogger(AbstractChromosomeEvaluation.class.getName());

    public Logger getLogger(){
        return logger;
    }

}
