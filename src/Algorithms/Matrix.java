package Algorithms;

import java.util.ArrayList;

/**
 * Currently a simple class for basic matrix manipulations, such as creating matrices and
 * todo more features?
 */
public class Matrix {
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
}
