package DataStructure;

public class MutabilityMatrix extends Matrix{

    private boolean[][] canMutateMatrix;
    /**
     * Constructor
     *
     * @param width
     * @param height
     */
    public MutabilityMatrix(int width, int height) {
        super(width, height);

        this.canMutateMatrix = new boolean[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                canMutateMatrix[y][x] = true;
            }
        }
    }

    public boolean put(int x, int y, long e){
        if(canMutateMatrix[y][x]) {
            super.put(x, y, e);
            return true;
        }else {
            return false;
        }
    }

    public void replaceRow(int row, long[] content) {
        int width = getWidth();

        long[] newContent = new long[width];

        for (int x = 0; x < width; x++) {
            if(canMutateMatrix[row][x]) {
                newContent[x] = content[x];
            }else {
                newContent[x] = getElement(x, row);
            }
        }

        super.replaceRow(row, newContent);
    }

    //TODO I think its good but let me make sure
    public boolean fillMatrixArea(int pickX, int pickY, Matrix smallerMatrix){
        int smallWidth = smallerMatrix.getWidth();
        int smallHeight = smallerMatrix.getHeight();

        for (int y = pickY; y < smallHeight + pickY; y++) {
            for (int x = pickX; x < smallWidth + pickX; x++) {
                if(!canMutateMatrix[y][x]) {
                    System.err.println("Cant put room here");
                    return false;
                }
            }
        }
        System.out.println("Room has been put");
        super.fillMatrixArea(pickX, pickY, smallerMatrix);
        return true;
    }


    public void makeAreaImmutable(int pickX, int pickY, Matrix smallerMatrix){
        int matrixWidth = smallerMatrix.getWidth();
        int matrixHeight = smallerMatrix.getHeight();

        if(getWidth() > (matrixWidth + pickX))
            if(getHeight() > (matrixHeight + pickY)){

                for (int y = 0; y < matrixHeight; y++) {
                    for (int x = 0; x < matrixWidth; x++) {
                        makeImmutable(pickX + x, pickY + y);
                    }
                }
            }
    }

    public void makeImmutable(int x, int y){
        canMutateMatrix[y][x] = false;
    }

    public void makeMutable(int x, int y){
        canMutateMatrix[y][x] = true;
    }

    public boolean getMutateElement(int x, int y){
        return canMutateMatrix[y][x];
    }


    //Its a toString() method...
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(super.toString()).append("\n\n Mutablity Matrix:\n");

        for (int y = 0; y < getHeight(); y++) {
            string.append(" {");
            for (int x = 0; x < getWidth(); x++) {
                if(canMutateMatrix[y][x])string.append(1);
                else string.append(0);

                if(x != getWidth() - 1) string.append(", ");
            }
            string.append("}\n");
        }
        return string.toString();
    }
}
