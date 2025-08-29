import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class RegexEngineTest {
    
    private RegexEngine engine;
    
    @BeforeEach
    public void setUp() {
        engine = new RegexEngine();
    }
    
    @Test
    public void testBasicSetup() {
        assertNotNull(engine);
    }
    
    @Test
    public void testSimpleFSAEvaluation() {
        // Create a simple FSA that accepts "a"
        FSA fsa = new FSA();
        State start = new State();
        State accept = new State();
        
        start.addTransition(new Transition(accept, 'a'));
        
        fsa.setStartState(start);
        fsa.addAcceptState(accept);
        fsa.addState(start);
        fsa.addState(accept);
        
        assertTrue(fsa.evaluate("a"));
        assertFalse(fsa.evaluate("b"));
        assertFalse(fsa.evaluate("aa"));
    }
    
    @Test
    public void testSimpleSequenceParsing() {
        FSA fsa = engine.parseRegex("abc");
        
        assertTrue(fsa.evaluate("abc"));
        assertFalse(fsa.evaluate("ab"));
        assertFalse(fsa.evaluate("abcd"));
        assertFalse(fsa.evaluate("xyz"));
    }
    
    @Test
    public void testConcatenation() {
        FSA fsa = engine.parseRegex("hello");
        
        assertTrue(fsa.evaluate("hello"));
        assertFalse(fsa.evaluate("hell"));
        assertFalse(fsa.evaluate("hellow"));
        assertFalse(fsa.evaluate("world"));
    }
    
    @Test
    public void testEmptyRegex() {
        FSA fsa = engine.parseRegex("");
        assertTrue(fsa.evaluate(""));
        assertFalse(fsa.evaluate("a"));
    }
    
    @Test
    public void testSingleCharacter() {
        FSA fsa = engine.parseRegex("x");
        
        assertTrue(fsa.evaluate("x"));
        assertFalse(fsa.evaluate("y"));
        assertFalse(fsa.evaluate("xx"));
        assertFalse(fsa.evaluate(""));
    }
}