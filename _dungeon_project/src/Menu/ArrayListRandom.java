package Menu;

import java.util.ArrayList;
import java.util.Random;

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
