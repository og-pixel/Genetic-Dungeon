package Genetic_Algorithm.Selection;

import Dungeon.Dungeon;
import java.util.ArrayList;

public interface SelectionImp {
    ArrayList<Dungeon> selectFitIndividuals(ArrayList<Dungeon> dungeon);
}
