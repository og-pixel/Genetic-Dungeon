package Genetic_Algorithm.CorrectionStrategy;

import Chromosome.Chromosome;
import DataStructure.MutabilityMatrix;

import static Chromosome.TileList.WALL;

public class AddPermanentWallsStrategy implements CorrectionStrategy {

    public static final String IMPLEMENTATION = "add_permanent_walls";

    @Override
    public void correctMap(Chromosome chromosome) {
//        if(!(chromosome.getMapMatrix() instanceof MutabilityMatrix))
//            throw new WrongClassInstanceException(MutabilityMatrix.class.toString());

        //TODO while this does throw an error, if cast is wrong, I think its fine as
        // I don't want to continue execution if objects are wrong
        MutabilityMatrix matrix = (MutabilityMatrix) chromosome.getMapMatrix();

        for (int x = 0; x < matrix.getWidth(); x++) {
            matrix.put(x, 0, WALL);
            matrix.put(x, matrix.getHeight() - 1, WALL);

            matrix.makeImmutable(x, 0);
            matrix.makeImmutable(x, matrix.getHeight() - 1);
        }

        for (int y = 0; y < matrix.getHeight(); y++) {
            matrix.put(0, y, WALL);
            matrix.put(matrix.getWidth() - 1, y, WALL);

            matrix.makeImmutable(0, y);
            matrix.makeImmutable(matrix.getWidth() - 1, y);
        }

//        for (int y = 0; y < matrix.getHeight(); y++) {
//            for (int x = 0; x < matrix.getWidth(); x++) {
//                matrix.replaceRow(0,  new long[]{});
//            }
//        }
    }
}
