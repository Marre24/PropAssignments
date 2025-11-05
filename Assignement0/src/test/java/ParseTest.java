import org.junit.jupiter.api.Test;
import scanner.Scanner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ParseTest {

    private final static String PROGRAM_1_PATH = "src/main/resources/program1.txt";
    private final static String PROGRAM_2_PATH = "src/main/resources/program2.txt";

    @Test
    public void scannerCanOpenProgram1() {
        Scanner scanner = new Scanner();
        assertDoesNotThrow(() -> scanner.open(PROGRAM_1_PATH));
        try { scanner.close(); } catch (IOException _) { }
    }

    @Test
    public void scannerCanOpenProgram2() {
        Scanner scanner = new Scanner();
        assertDoesNotThrow(() -> scanner.open(PROGRAM_2_PATH));
        try { scanner.close(); } catch (IOException _) { }
    }

    @Test
    public void tokenizer_program1_notNull() {

    }


}
