public class Step {
    //состояния на момент перед шагом
    public Cell Mole;
    public Cell Next;
    public Cell AfterNext;
    public char NextState;
    public char AfterNextState;

    public Step(Cell mole, Cell next, Cell afterNext, char nextState, char afterNextState){
        Mole = mole;
        Next = next;
        AfterNext = afterNext;
        NextState = nextState;
        AfterNextState = afterNextState;
    }
}
