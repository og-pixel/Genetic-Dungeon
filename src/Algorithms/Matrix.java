package Algorithms;

import Exceptions.VariableBoundsException;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Point of this class is an exercise to change ArrayList<ArrayList<E>> into
 * something more generic
 * Matrix[][] and manipulate that instead (its speed concern)
 */
public class Matrix implements Serializable {

    //    private ArrayList<ArrayList<E>> matrix;
    private long[][] matrix; //todo i dont know how to do it otherwise, it has to be a number i think, i might just embbedeed an enum here to have static choices
//    private E[] list;

    private int width, height;


    private int chromosome;//todo this will be stringfiled version of
    // dungeon to use for comparsion


    public Matrix(int width, int height) {

        this.width = width;
        this.height = height;

        this.matrix = new long[height][width];


    }


//
//
//    /**
//     * TODO constructor without creating stuff inside (nulls)
//     * @param width
//     * @param height
//     */
//    public Matrix(int width, int height){
//        matrix = new ArrayList<>();
//        for(int y = 0; y < height; y++){
//            matrix.add(new ArrayList<>());
//            for(int x = 0; x < width; x++){
//                matrix.get(y).add(null);
//            }
//        }
//        this.width = width;
//        this.height = height;
//    }

    public long getElement(int x, int y) {
        return matrix[y][x];
    }


    public boolean put(int x, int y, long e) {
        try {
            matrix[y][x] = e;//todo i might need to delete this try
        } catch (Exception ex) {
//            ex.printStackTrace();
            return false; //todo one or the other
        }
        return true;
    }

    public long[] getRow(int row) {
        return matrix[row];
    }

    //TODO
//    public ArrayList<E> getColumn(int column){
//        ArrayList<E> columnArray = new ArrayList<>();
//        for(int i = 0; i < height; i++){
//            columnArray.add(matrix.get(column).get(i));
//        }
//        return columnArray;
//    }

    public void replaceRow(int row, long[] content) {
        matrix[row] = content;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * TODO It fill is with one the same element
     *
     * @param element
     */
    public void fillMatrix(long element) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = element;
            }
        }
    }


    public long getUp(int x, int y) {
//        if ((y - 1) < 0) throw new IndexOutOfBoundsException();
        if ((y - 1) < 0) return -1;
        return getElement(x, y - 1);
    }

    public long getRight(int x, int y) {
        if ((x + 1) >= width) return -1;
        return getElement(x + 1, y);
    }

    public long getDown(int x, int y) {
        if ((y + 1) >= height) return -1;
        return getElement(x, y + 1);
    }

    public long getLeft(int x, int y) {
        if ((x - 1) < 0) return -1;
        return getElement(x - 1, y);
    }

    public boolean swapElements(int x1, int y1, int x2, int y2){
        long element1 = getElement(x1, y1);
        long element2 = getElement(x2, y2);


        //TODO it tells me i can simplify it but i dont like it atm
        if(put(x1, y1, element2) && put(x2, y2, element1)) return true;
        else return false;
    }

    /**
     * @return
     */
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (int y = 0; y < height; y++) {
            string.append(" { ");
            for (int x = 0; x < width; x++) {
                string.append(matrix[height][width]).append(", ");
            }
            string.append("}\n");
        }
        return string.toString();
    }

    public int getChromosome() {
        int z = 1;


        return z;
    }
}
