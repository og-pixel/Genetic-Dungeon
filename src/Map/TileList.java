package Map;

public interface TileList {
    long NOT_A_NUMBER = -1; //For Error codes
    long UNIVERSAL = 0; //"Don't care" variable, if its 0, it is anything
    long CORRIDOR = 1;
    long WALL = 2;
    long START = 3;
    long END = 4;
}
