package Dungeon;

public enum DungeonTiles {
    CORRIDOR(0),
    WALL(1),
    START(2),
    END(3);
    private int x;

    DungeonTiles(int x){
        this.x = x;
    }

    public int getX() {
        return x;
    }
}
