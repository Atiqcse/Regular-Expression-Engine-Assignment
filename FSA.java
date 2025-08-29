import java.util.*;

public class FSA {
    private State startState;
    private Set<State> acceptStates;
    private Set<State> allStates;
    
    public FSA() {
        this.allStates = new HashSet<>();
        this.acceptStates = new HashSet<>();
    }
    
    // Getters and setters
    public State getStartState() {
        return startState;
    }
    
    public void setStartState(State startState) {
        this.startState = startState;
    }
    
    public Set<State> getAcceptStates() {
        return acceptStates;
    }
    
    public void addAcceptState(State state) {
        this.acceptStates.add(state);
    }
    
    public Set<State> getAllStates() {
        return allStates;
    }
    
    public void addState(State state) {
        this.allStates.add(state);
    }
}