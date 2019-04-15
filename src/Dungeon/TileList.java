package Dungeon;

public interface TileList {
    long UNIVERSAL = -1; //"Don't care" variable, if its -1, it is anything
    long CORRIDOR = 0;
    long WALL = 1;
    long START = 2;
    long END = 3;
}
