package Algorithms;

import Dungeon.Dungeon;

/**
 * Interface defines what a fitness function needs to run it?
 */
public interface Fitness {
    int evaluateDungeon(Dungeon dungeon);
}

