import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegexEngineTest {
    
    @Test
    public void testBasicSetup() {
        RegexEngine engine = new RegexEngine();
        assertNotNull(engine);
    }

}