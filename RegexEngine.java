public class RegexEngine {
    
    public static void main(String[] args) {
        RegexEngine engine = new RegexEngine();
        
        // Test basic functionality
        System.out.println("=== Testing Regular Expression Engine ===");
        testPattern(engine, "abc", "abc");
        testPattern(engine, "abc", "ab");
        testPattern(engine, "hello", "hello");
    }
    
    private static void testPattern(RegexEngine engine, String pattern, String input) {
        FSA fsa = engine.parseRegex(pattern);
        boolean result = fsa.evaluate(input);
        System.out.printf("Pattern '%s' with input '%s': %s%n", pattern, input, result);
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
        if (pos >= regex.length()) {
            return new ParseResult(createEmptyFSA(), pos);
        }
        
        // Parse first element
        ParseResult first = parseBasicElement(regex, pos);
        FSA result = first.fsa;
        pos = first.nextPos;
        
        // Handle concatenation (implicit)
        while (pos < regex.length()) {
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