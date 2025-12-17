import Nodes.INode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserException;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvaluationTest {

    private final static String PROGRAM_2_PATH = "src/main/resources/program2.txt";
    private static INode root;
    private static final String EXPECTED =
            "a = 1.8\n" +
            "b = -0.2\n" +
            "c = 1.6\n";


    @BeforeAll
    public static void parserCanOpenProgram1() {
        Parser parser = new Parser();
        assertDoesNotThrow(() -> parser.open(PROGRAM_2_PATH));
        try {
            root = parser.parse();
        } catch (IOException | TokenizerException | ParserException e) {
            System.err.println("Could not parse: " + e.getMessage());
        }
        assertDoesNotThrow(parser::close);
    }

    @Test
    public void evaluateDoesNotThrowException() {
        Map<String, Double> variables = new HashMap<>();
        assertDoesNotThrow(() -> root.evaluate(new Object[]{variables}));
    }

    @Test
    public void variablesHaveCorrectValue() throws Exception {
        String actual = (String) root.evaluate(null);
        assertEquals(EXPECTED, actual);
    }
}
