public class Transition {
    private State toState;
    private char symbol;
    private boolean isEpsilon;
    
    public Transition(State toState, char symbol) {
        this.toState = toState;
        this.symbol = symbol;
        this.isEpsilon = false;
    }
    
    public Transition(State toState) {
        this.toState = toState;
        this.isEpsilon = true;
    }
    
    public State getToState() {
        return toState;
    }
    
    public char getSymbol() {
        return symbol;
    }
    
    public boolean isEpsilon() {
        return isEpsilon;
    }
    
    @Override
    public String toString() {
        return "Transition{" +
                "toState=" + toState +
                ", symbol=" + (isEpsilon ? "ε" : symbol) +
                '}';
    }
}