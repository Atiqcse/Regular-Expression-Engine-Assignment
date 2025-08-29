public class RegexEngine {
    
    public static void main(String[] args) {
        RegexEngine engine = new RegexEngine();
        
        // Test basic functionality
        System.out.println("=== Testing Regular Expression Engine ===");
        testPattern(engine, "abc", "abc");
        testPattern(engine, "a|b", "a");
        testPattern(engine, "a|b", "b");
        testPattern(engine, "a|b", "c");
        testPattern(engine, "hello|world", "hello");
        testPattern(engine, "hello|world", "world");
    }
    
    private static void testPattern(RegexEngine engine, String pattern, String input) {
        try {
            FSA fsa = engine.parseRegex(pattern);
            boolean result = fsa.evaluate(input);
            System.out.printf("Pattern '%s' with input '%s': %s%n", pattern, input, result);
        } catch (Exception e) {
            System.out.printf("Error with pattern '%s': %s%n", pattern, e.getMessage());
        }
    }
    
  
    public FSA parseRegex(String regex) {
        if (regex == null || regex.isEmpty()) {
            return createEmptyFSA();
        }
        
        return parseExpression(regex, 0).fsa;
    }
    
   
    private static class ParseResult {
        FSA fsa;
        int nextPos;
        
        ParseResult(FSA fsa, int nextPos) {
            this.fsa = fsa;
            this.nextPos = nextPos;
        }
    }
    
    
    private ParseResult parseExpression(String regex, int pos) {
        ParseResult left = parseConcatenation(regex, pos);
        pos = left.nextPos;
        
        // Check for union operator |
        while (pos < regex.length() && regex.charAt(pos) == '|') {
            pos++; // skip |
            ParseResult right = parseConcatenation(regex, pos);
            left.fsa = union(left.fsa, right.fsa);
            pos = right.nextPos;
        }
        
        return new ParseResult(left.fsa, pos);
    }
    
  
    private ParseResult parseConcatenation(String regex, int pos) {
        if (pos >= regex.length()) {
            return new ParseResult(createEmptyFSA(), pos);
        }
        
        // Parse first element
        ParseResult first = parseBasicElement(regex, pos);
        FSA result = first.fsa;
        pos = first.nextPos;
        
        // Handle concatenation (implicit)
        while (pos < regex.length() && regex.charAt(pos) != '|') {
            ParseResult next = parseBasicElement(regex, pos);
            result = concatenate(result, next.fsa);
            pos = next.nextPos;
        }
        
        return new ParseResult(result, pos);
    }
    
  
    private ParseResult parseBasicElement(String regex, int pos) {
        if (pos >= regex.length()) {
            return new ParseResult(createEmptyFSA(), pos);
        }
        
        char c = regex.charAt(pos);
        
        // Skip union operator in basic element parsing
        if (c == '|') {
            return new ParseResult(createEmptyFSA(), pos);
        }
        
        FSA fsa = createCharacterFSA(c);
        return new ParseResult(fsa, pos + 1);
    }
    
    private FSA createCharacterFSA(char c) {
        FSA fsa = new FSA();
        State start = new State();
        State accept = new State();
        
        start.addTransition(new Transition(accept, c));
        
        fsa.setStartState(start);
        fsa.addAcceptState(accept);
        fsa.addState(start);
        fsa.addState(accept);
        
        return fsa;
    }
    

    private FSA union(FSA first, FSA second) {
        FSA result = new FSA();
        State newStart = new State();
        State newAccept = new State();
        
        // Add new start state
        result.setStartState(newStart);
        result.addState(newStart);
        
        // Copy all states from both FSAs
        for (State state : first.getAllStates()) {
            result.addState(state);
        }
        for (State state : second.getAllStates()) {
            result.addState(state);
        }
        
        // Add new accept state
        result.addState(newAccept);
        result.addAcceptState(newAccept);
        
        // Connect new start to both FSA start states
        newStart.addTransition(new Transition(first.getStartState()));
        newStart.addTransition(new Transition(second.getStartState()));
        
        // Connect both FSA accept states to new accept state
        for (State acceptState : first.getAcceptStates()) {
            acceptState.addTransition(new Transition(newAccept));
        }
        for (State acceptState : second.getAcceptStates()) {
            acceptState.addTransition(new Transition(newAccept));
        }
        
        return result;
    }
    
   
    private FSA concatenate(FSA first, FSA second) {
        FSA result = new FSA();
        
        // Copy all states from both FSAs
        for (State state : first.getAllStates()) {
            result.addState(state);
        }
        for (State state : second.getAllStates()) {
            result.addState(state);
        }
        
        // Start state is the start state of first FSA
        result.setStartState(first.getStartState());
        
        // Connect accept states of first FSA to start state of second FSA via epsilon
        for (State acceptState : first.getAcceptStates()) {
            acceptState.addTransition(new Transition(second.getStartState()));
        }
        
        // Accept states are the accept states of second FSA
        for (State acceptState : second.getAcceptStates()) {
            result.addAcceptState(acceptState);
        }
        
        return result;
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