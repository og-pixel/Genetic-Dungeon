package DataStructure;

import Exceptions.MatrixTooSmallException;

import java.io.Serializable;

/**
 * Relatively simple matrix class that is an array of numerical arrays
 * It provides basic functions to manipulate it, such as;
 * replacing rows and columns, cutting parts of the map etc.
 */
public class Matrix implements Serializable {

    private long[][] matrix;
    private int width, height;

    /**
     * Constructor
     * @param width
     * @param height
     */
    public Matrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new long[height][width];
    }

    /**
     * Fill the entire matrix with one element
     * @param element element to fill matrix with
     */
    public void fillMatrix(long element) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matrix[y][x] = element;
            }
        }
    }


    /**
     * Put an element e to the matrix at position x,y
     * @param x coordinate x
     * @param y coordinate y
     * @param e element to be put
     * @return true if successful, false otherwise
     */
    public boolean put(int x, int y, long e) {
        try {
            matrix[y][x] = e;
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * Get a whole row off the matrix
     * @param row row to be returned
     * @return row of elements long
     */
    public long[] getRow(int row) {
        return matrix[row];
    }

    public void replaceRow(int row, long[] content) {
        matrix[row] = content;
    }


    /**
     * Swap two elements on the matrix
     * Program will not replace any values if only
     * one put is successful
      * @param x1 coordinate x for point 1
     * @param y1 coordinate y for point 1
     * @param x2 coordinate x for point 2
     * @param y2 coordinate y for point 2
     * @return true if successful, false otherwise
     */
    public boolean swapElements(int x1, int y1, int x2, int y2){
        long element1 = getElement(x1, y1);
        long element2 = getElement(x2, y2);
        long tmp = getElement(x1, y1);

        if(put(x1, y1, element2)){
            if(put(x2, y2, element1))return true;
            else put(x1, y1, tmp); //return to what it was
        } return false;
    }

    /**
     * Cut a matrix, starting from the UPPER-LEFT corner as pickX and pickY coordinates
     *
     * This is X,Y point
     * ^####
     * #####
     * #####
     * #####
     * And has 4 width and 5 height
     *
     * @param pickX
     * @param pickY
     * @param width
     * @param height
     * @return
     */
    public Matrix cutMatrix(int pickX, int pickY, int width, int height){
        if(matrix.length < height || matrix[0].length < width)throw new MatrixTooSmallException();
        Matrix newMatrix = new Matrix(width, height);

        for (int y = pickY, normalY = 0; y < pickY + height; y++, normalY++) {
            for (int x = pickX, normalX = 0; x < pickX + width; x++, normalX++) {
                newMatrix.put(normalX, normalY, matrix[y][x]);
            }
        }

        return newMatrix;
    }

    //Getters

    public long getElement(int x, int y) {
        return matrix[y][x];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //NOTE ABOUT GET UP RIGHT DOWN and LEFT 
    //They return -1 if element picked was off the matrix
    //As I can't return null and I don't want to throw 
    //A runtime exception
    //-1 in TileList is supposted to mean "error" or "non existent"
    public long getUp(int x, int y) {
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

    public long getUpLeft(int x, int y){
        if(((y - 1) < 0) &&
                ((x - 1) < 0)) return -1;

        return getElement(x, y);
    }

    public long getUpRight(int x, int y){
        if(((y - 1) < 0) &&
                ((x + 1) >= width)) return -1;

        return getElement(x, y);
    }

    public long getDownLeft(int x, int y){
        if(((y + 1) >= height) &&
                ((x - 1) < 0)) return -1;

        return getElement(x, y);
    }

    public long getDownRight(int x, int y){
        if(((y + 1) >= height) &&
                ((x + 1) >= width)) return -1;

        return getElement(x, y);
    }


    //TODO this is meant to surround the matrix with the walls
    public void surroundMatrix(int value){


//        replaceRow(0, new long[]{value, value, value, value, value});
//        replaceRow(height, new long[]{value, value, value, value, value});
//


    }

    public long getVolume(){
        return width * height;
    }

    //Its a toString() method...
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (int y = 0; y < height; y++) {
            string.append(" {");
            for (int x = 0; x < width; x++) {
                string.append(matrix[y][x]);
                if(x != width - 1)string.append(", ");
            }
            string.append("}\n");
        }
        return string.toString();
    }
}
