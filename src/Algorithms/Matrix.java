package Algorithms;

import java.util.ArrayList;
import java.util.Random;

/**
 * Currently a simple class for basic matrix manipulations, such as creating matrices and
 * todo more features?
 * todo matrix is quite universal and accepts all types (it wont crash if matrix has numbers and strings)
 */
public class Matrix<E> {

    private ArrayList<ArrayList<E>> matrix;
    private int width, height;

    public Matrix (int width, int height, E element){
        for(int y = 0; y < height; y++){
            matrix = new ArrayList<>();
            for(int x = 0; x < width; x++){
                matrix.get(y).add(null);
            }
        }
        System.out.println("Debug:" + this);
        this.width = width;
        this.height = height;
    }

    /**
     * TODO constructor without creating stuff inside (nulls)
     * @param width
     * @param height
     */
    public Matrix(int width, int height){
        matrix = new ArrayList<>();

        for(int y = 0; y < height; y++){
            matrix.add(new ArrayList<>());
            for(int x = 0; x < width; x++){
                matrix.get(y).add(null);
            }
        }
        this.width = width;
        this.height = height;
    }

    public E getElement(int x, int y){
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
    public boolean addColumn(E element){
        for(int i = 0; i < height; i++) { //todo i had to -1
            matrix.get(i).add(element);
        }
        width++;
        return true;
    }


    public boolean putElementAt(int x, int y, E e){
        try{
           matrix.get(y).set(x, e);
        }catch (Exception ex){
            ex.printStackTrace();
            return false; //todo one or the other
        }
        return true;
    }

    public ArrayList<E> getRow(int row){
        return matrix.get(row);
    }

    public ArrayList<E> getColumn(int column){
        ArrayList<E> columnArray = new ArrayList<>();
        for(int i = 0; i < height; i++){
            columnArray.add(matrix.get(column).get(i));
        }
        return columnArray;
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

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    /**
     * TODO It fill is with one the same element
     * @param element
     */
    public void fillMatrix(E element){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                matrix.get(y).set(x, element);
            }
        }
    }

    public void cellurarAutomata(E element, E element2, double odds){
        Random random = new Random();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(random.nextDouble() > odds) matrix.get(y).set(x, element);
                else matrix.get(y).set(x, element2);
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
