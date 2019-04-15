package Algorithms;

import java.util.ArrayList;
import java.util.Random;

/**
 * Extension to the ArrayList Class with
 * additional method getRandom()
 * to receive a random element from the arrayList
 */
public class ArrayListRandom<E> extends ArrayList<E>
{
    private Random random;

    public ArrayListRandom(){
        super();
        random = new Random();
    }



    public E getRandom(){
        return super.get(random.nextInt(size()));
    }

}
