package Genetic_Algorithm.Selection;

//TODO https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_parent_selection.htm
public enum SelectionEnum implements SelectionImp {
    Rolletue{
        @Override
        public void useSelection() {

        }
    },
    //TODO its stochastic universal sampling
    StochasticTwo{
        @Override
        public void useSelection() {

        }
    },
    Tournament{
        @Override
        public void useSelection() {

        }
    },
    Rank{
        @Override
        public void useSelection() {

        }
    }
}
