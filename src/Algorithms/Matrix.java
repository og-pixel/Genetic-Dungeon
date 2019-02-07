package Algorithms;

import Dungeon.Tile.Tile;
import com.sun.javafx.scene.traversal.Algorithm;

import java.util.ArrayList;

/**
 * Currently a simple class for basic matrix manipulations, such as creating matrices and
 * todo more features?
 * todo matrix is quite universal and accepts all types (it wont crash if matrix has numbers and strings)
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
//        matrix = createMatrix(width, height, false);
        for(int y = 0; y < height; y++){
            matrix = new ArrayList<>();
            for(int x = 0; x < width; x++){
                matrix.get(y).add(new Object());//todo i made it object
            }
        }
        this.width = width;
        this.height = height;
    }

    public <T> Matrix (int width, int height, T type){
//        matrix = createMatrix(width, height, type);
        for(int y = 0; y < height; y++){
            matrix = new ArrayList<>();
            for(int x = 0; x < width; x++){
                matrix.get(y).add(type);//todo i made it object
            }
        }
        this.width = width;
        this.height = height;
    }

    //todo creating a little cheat
    public Matrix (int width, int height, Tile tiletype){
//        matrix = createMatrix(width, height, tiletype);//todo might be the same thing with one objec
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
    public boolean addRow(E type){
        matrix.add(new ArrayList<>());
        for(int i = 0; i < width; i++) { //todo i had to -1
            matrix.get(height).add(type);
        }
        height++;
        return true;
    }

    /**
     * Adds a new row to the matrix (on the bottom)
     * @return
     */
    public boolean addColumn(E type){
        for(int i = 0; i < height; i++) { //todo i had to -1
            matrix.get(i).add(type);
        }
        width++;
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
     */
//    public static <T> Matrix<T> createMatrix(int width, int height, T type){
//        Matrix<T> genericMatrix = new Matrix<T>(width, height, type);//todo it was empty and I dont know what to put back
////        for(int yAxis = 0; yAxis < height; yAxis++){
////            genericMatrix.add(new ArrayList<>());
////            for(int xAxis = 0; xAxis < width; xAxis++){
////                genericMatrix.get(yAxis).add(type);
////            }
////        }
//        return genericMatrix;
//    }


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
