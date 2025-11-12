import Nodes.INode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserException;
import tokenizer.TokenizerException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class EvaluationTest {

    private final static String PROGRAM_2_PATH = "src/main/resources/program2.txt";
    private INode root;

    @BeforeEach
    public void parserCanOpenProgram1() {
        Parser parser  = new Parser();
        assertDoesNotThrow(() -> parser.open(PROGRAM_2_PATH));
        try {
            root = parser.parse();
        } catch (IOException | TokenizerException | ParserException e) {
            System.err.println("Could not parse: " + e.getMessage());
        }
        assertDoesNotThrow(parser::close);
    }

    @Test
    public void evaluateDoesNotThrowException(){
        Object[] objects = new Object[0];
        assertDoesNotThrow(() -> root.evaluate(objects));
    }
}
