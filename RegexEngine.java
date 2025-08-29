public class RegexEngine {
    
    public static void main(String[] args) {
        RegexEngine engine = new RegexEngine();
        
        // Test basic functionality
        FSA fsa = engine.parseRegex("abc");
        System.out.println("Testing 'abc' pattern:");
        System.out.println("'abc' matches: " + fsa.evaluate("abc"));
        System.out.println("'ab' matches: " + fsa.evaluate("ab"));
        System.out.println("'abcd' matches: " + fsa.evaluate("abcd"));
    }
    
   
    public FSA parseRegex(String regex) {
        if (regex == null || regex.isEmpty()) {
            return createEmptyFSA();
        }
        
        // For now, handle simple character sequences
        return parseSimpleSequence(regex);
    }
    
    
    private FSA parseSimpleSequence(String sequence) {
        FSA fsa = new FSA();
        State current = new State();
        State start = current;
        
        fsa.setStartState(start);
        fsa.addState(start);
        
        // Create a chain of states for each character
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            State next = new State();
            current.addTransition(new Transition(next, c));
            fsa.addState(next);
            current = next;
        }
        
        // Last state is accept state
        fsa.addAcceptState(current);
        
        return fsa;
    }
    
   
    private FSA createEmptyFSA() {
        FSA fsa = new FSA();
        State state = new State();
        fsa.setStartState(state);
        fsa.addAcceptState(state);
        fsa.addState(state);
        return fsa;
    }
    
  
    public boolean evaluateInput(FSA fsa, String input) {
        return fsa.evaluate(input);
    }
}