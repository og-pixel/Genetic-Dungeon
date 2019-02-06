package Algorithms;

import java.util.ArrayList;

/**
 * Currently a simple class for basic matrix manipulations, such as creating matrices and
 * todo more features?
 */
public class Matrix {

//    ArrayList<ArrayList<Boolean>> matrix;
//
//    public Matrix(int width, int height){
//        matrix = createMatrix(width, height, true);
//    }

    /**
     * todo it will fill it with one object
     * @param width
     * @param height
     * @param type
     * @param <T>
     * @return
     */
    public static <T> ArrayList<ArrayList<T>> createMatrix(int width, int height, T type){
        ArrayList<ArrayList<T>> genericMatrix = new ArrayList<>();
        for(int yAxis = 0; yAxis < height; yAxis++){
            genericMatrix.add(new ArrayList<>());
            for(int xAxis = 0; xAxis < width; xAxis++){
                genericMatrix.get(yAxis).add(type);
            }
        }

        if(genericMatrix == null)throw new RuntimeException("Matrix cannot be null");
        return genericMatrix;
    }

    /**
     * inserts contents of one matrix to another
     * todo i might not need it
     */
    public static <T> void insertMatrix(ArrayList<ArrayList<T>> matrixA,
                                    ArrayList<ArrayList<T>> matrixB ){
        for (ArrayList<T> ts : matrixA) {
            for (int z = 0; z < ts.size(); z++) {
//                if(){
//
//                }
            }
        }
    }

}
