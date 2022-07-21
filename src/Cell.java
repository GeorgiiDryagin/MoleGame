public class Cell {
    public char state;/*
    f = free
    c = closed
    m = mole
    g = grain
    b = bag
    w = bag With grain
    */
    public boolean[] walls;
    public boolean target;

    public Cell(){
        target = false;
        walls = new boolean[4];
    }
}
