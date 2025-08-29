import java.util.*;

public class FSA {
    private State startState;
    private Set<State> acceptStates;
    private Set<State> allStates;
    
    public FSA() {
        this.allStates = new HashSet<>();
        this.acceptStates = new HashSet<>();
    }
    
    /**
     * Evaluate if an input string is accepted by this FSA
     * @param input The input string to evaluate
     * @return true if accepted, false otherwise
     */
    public boolean evaluate(String input) {
        if (startState == null) {
            return false;
        }
        
        Set<State> currentStates = new HashSet<>();
        currentStates.add(startState);
        
        // Add epsilon closure of start state
        currentStates = getEpsilonClosure(currentStates);
        
        // Process each character in input
        for (char c : input.toCharArray()) {
            currentStates = processCharacter(currentStates, c);
            if (currentStates.isEmpty()) {
                return false;
            }
        }
        
        // Check if any current state is an accept state
        for (State state : currentStates) {
            if (acceptStates.contains(state)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Get epsilon closure of a set of states
     * @param states The initial set of states
     * @return Set of states reachable via epsilon transitions
     */
    private Set<State> getEpsilonClosure(Set<State> states) {
        Set<State> closure = new HashSet<>(states);
        Stack<State> stack = new Stack<>();
        stack.addAll(states);
        
        while (!stack.isEmpty()) {
            State current = stack.pop();
            for (Transition transition : current.getTransitions()) {
                if (transition.isEpsilon()) {
                    State target = transition.getToState();
                    if (!closure.contains(target)) {
                        closure.add(target);
                        stack.push(target);
                    }
                }
            }
        }
        
        return closure;
    }
    
    /**
     * Process a character and return the set of reachable states
     * @param currentStates Current set of states
     * @param c Character to process
     * @return New set of reachable states
     */
    private Set<State> processCharacter(Set<State> currentStates, char c) {
        Set<State> nextStates = new HashSet<>();
        
        for (State state : currentStates) {
            for (Transition transition : state.getTransitions()) {
                if (!transition.isEpsilon() && transition.getSymbol() == c) {
                    nextStates.add(transition.getToState());
                }
            }
        }
        
        return getEpsilonClosure(nextStates);
    }
    
    // Getters and setters remain the same...
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