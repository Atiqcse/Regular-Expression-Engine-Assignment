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
        FSA fsa =