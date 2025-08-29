import java.util.*;

public class State {
    private static int nextId = 0;
    private int id;
    private List<Transition> transitions;
    
    public State() {
        this.id = nextId++;
        this.transitions = new ArrayList<>();
    }
    
    public int getId() {
        return id;
    }
    
    public List<Transition> getTransitions() {
        return transitions;
    }
    
    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State state = (State) obj;
        return id == state.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "State{" + "id=" + id + '}';
    }
}