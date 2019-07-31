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

        for (int y = 0; y < row; y++) {
            if(canMutateMatrix[row][y])
                newContent[y] = content[y];
        }

        super.replaceRow(row, newContent);
    }


    public void makeImmutable(int x, int y){
        canMutateMatrix[y][x] = false;
    }

    public void makeMutable(int x, int y){
        canMutateMatrix[y][x] = true;
    }
}
