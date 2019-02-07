package Algorithms;

import com.sun.javafx.scene.traversal.Algorithm;

import java.util.ArrayList;

/**
 * Currently a simple class for basic matrix manipulations, such as creating matrices and
 * todo more features?
 */
public class Matrix<E> {

    private ArrayList<ArrayList<Object>> matrix; //for now, its object
    private int width, height; //todo make it count on contrsuctor and adding/removing rows

    /**
     * Default constructor makes booleans
     * @param width
     * @param height
     */
    public Matrix(int width, int height){
        matrix = createMatrix(width, height, false);
        this.width = width;
        this.height = height;
    }

    public <T> Matrix (int width, int height, T type){
        matrix = createMatrix(width, height, type);
        this.width = width;
        this.height = height;
    }


//
//    public Matrix getMatrix(){
//        return matrix;
//    }
    public Object getElement(int x, int y){
        return matrix.get(y).get(x);
    }


    /**
     * Adds a new row to the matrix (on the bottom)
     * @return
     */
    public boolean addRow(){
        matrix.add(new ArrayList<>());
        height++;
        return true;
    }



    public boolean putElementAt(E e, int x, int y){
        try{
           matrix.get(y).set(x, e);
        }catch (Exception ex){
            ex.printStackTrace();
            return false; //todo one or the other
        }
        return true;
    }







    //TODO this might go into matrix factory (ehhh)
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
        return genericMatrix;
    }


    public static <T> ArrayList<ArrayList<T>> createEmptyMatrix(int height){
        ArrayList<ArrayList<T>> emptyMatrix = new ArrayList<>();
        for(int yAxis = 0; yAxis < height; yAxis++){
            emptyMatrix.add(new ArrayList<>());
        }
        return emptyMatrix;
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

    /**
     * TODO I made it into a string builder (as IDE suggested), a little weird tho
     * @return
     */
    public String toString(){
        StringBuilder string = new StringBuilder();

        for(int y = 0; y < height; y++){
            string.append(" { ");
            for (int x = 0; x < width; x++){
                string.append(matrix.get(y).get(x).toString()).append(", ");
            }
            string.append("}\n");
        }
        return string.toString();
    }

}
