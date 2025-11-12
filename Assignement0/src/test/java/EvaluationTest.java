import Nodes.INode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserException;
import tokenizer.TokenizerException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

public class EvaluationTest {

    private final static String PROGRAM_2_PATH = "src/main/resources/program2.txt";
    private static INode root;
    private static final Map<String, Double> EXPECTED = Map.ofEntries(
            entry("a", 1.8d),
            entry("b", -0.2d),
            entry("c", 1.6d)
    );


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
    public void correctVariablesAreSaved() throws Exception {
        Map<String, Double> actual = new HashMap<>();
        root.evaluate(new Object[]{actual});
        Set<String> expectedKeys = EXPECTED.keySet();
        Set<String> actualKeys = actual.keySet();
        assertEquals(expectedKeys, actualKeys);
    }

    @Test
    public void variablesHaveCorrectValue() throws Exception {
        Map<String, Double> actual = new HashMap<>();
        root.evaluate(new Object[]{actual});
        for (var key : EXPECTED.keySet()){
            System.out.println("Variable: " + key + " expected to be: " + EXPECTED.get(key) + " and was: " + actual.get(key));
        }

        for (var key : EXPECTED.keySet()){
            assertEquals(EXPECTED.get(key), actual.get(key));
        }
    }
}
