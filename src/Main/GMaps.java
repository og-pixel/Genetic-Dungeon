package Main;

public class GMaps {
    public static void main(String[] args){

        //TODO dont delete, move to tests, that's how I will run all sorts of tests later
//        Matrix matrix = new MutabilityMatrix(10, 10);
//        matrix.fillMatrix(CORRIDOR);
//
//        System.out.println(matrix);
//        Chromosome chromosome = new Chromosome(matrix);
//
//
//        CorrectionStrategy correctionStrategy = new AddPermanentWallsStrategy();
//        correctionStrategy.correctMap(chromosome);
//
//        MutatorStrategy mutatorStrategy = new DefaultMutatorStrategy(0.5);
//        mutatorStrategy.mutateDungeon(chromosome);
//
//        System.out.println(matrix);
//
//        mutatorStrategy.mutateDungeon(chromosome);
//
//        System.out.println(matrix);
//
//        mutatorStrategy.mutateDungeon(chromosome);
//        System.out.println(matrix);
//
//        mutatorStrategy.mutateDungeon(chromosome);
//        System.out.println(matrix);
        new Interpreter(args);
    }
}
