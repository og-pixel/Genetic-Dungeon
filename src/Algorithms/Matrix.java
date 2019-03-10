package Algorithms;

import Exceptions.VariableBoundsException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Currently a simple class for basic matrix manipulations, such as creating matrices and
 * todo more features?
 * todo matrix is quite universal and accepts all types (it wont crash if matrix has numbers and strings)
 */
public class Matrix<E> implements Serializable {

    private ArrayList<ArrayList<E>> matrix;
    private int width, height;


    private int chromosome;//todo this will be stringfiled version of
                            // dungeon to use for comparsion

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
    public boolean addRow(E element){
        matrix.add(new ArrayList<>());
        for(int i = 0; i < width; i++) {
            matrix.get(height).add(element);
        }
        height++;
        return true;
    }

    /**
     * Adds a new row to the matrix (on the bottom)
     * @return
     */
    public boolean addColumn(E element){
        for(int i = 0; i < height; i++) {
            matrix.get(i).add(element);
        }
        width++;
        return true;
    }

    public boolean put(int x, int y, E e){
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

    public void replaceRow(int row, ArrayList<E> content){
        matrix.set(row, content);
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

    /**
     * Fill a matrix with two elements,
     * @param element
     * @param element2
     * @param odds
     */
    public void cellularAutomate(E element, E element2, double odds){
        if(odds < 0.1 || odds > 1) throw new VariableBoundsException(0.1, 1.0);
        Random random = new Random();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(random.nextDouble() > odds) matrix.get(y).set(x, element);
                else matrix.get(y).set(x, element2);
            }
        }
    }


    public E getUp(int x, int y){
        if((y - 1) < 0){
            return null;
        }

        return getElement(x, y - 1);
    }

    public E getRight(int x, int y){
        if((x + 1) >= width){
            return null;
        }

        return getElement(x + 1, y);
    }

    public E getDown(int x, int y){
        if((y + 1) >= height){
            return null;
        }
        return getElement(x, y + 1);
    }

    public E getLeft(int x, int y){
        if((x - 1) < 0){
            return null;
        }

        return getElement(x - 1, y);
    }

    /**
     * @return
     */
    public String toString(){
        StringBuilder string = new StringBuilder();

        for(int y = 0; y < height; y++){
            string.append(" { ");
            for (int x = 0; x < width; x++){
                if(matrix.get(y).get(x) == null) string.append("[],");
                else string.append(matrix.get(y).get(x).toString()).append(", ");
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
