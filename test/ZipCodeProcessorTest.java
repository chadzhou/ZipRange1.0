import com.company.ZipCodeProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

public class ZipCodeProcessorTest {


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private ZipCodeProcessor zipCodeProcessor = new ZipCodeProcessor();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // starting positive test cases
    @Test
    public void positiveTest0() {
        String positiveInput = "[94133,94133] [94200,94299] [94600,94699]";
        zipCodeProcessor.generateOutput(positiveInput);
        String outputString = this.outContent.toString().trim();
        assertEquals("[94133,94133] [94200,94299] [94600,94699]", outputString);

    }
    @Test
    public void positiveTest1() {
        String positiveInput = "[94133,94133] [94200,94299] [94226,94399]";
        zipCodeProcessor.generateOutput(positiveInput);
        String outputString = this.outContent.toString().trim();
        assertEquals("[94133,94133] [94200,94399]", outputString);
    }

    @Test
    public void positiveTest2() {
        String positiveInput = "[00100,00133]   [94200,94299]     [94600,94699]";
        zipCodeProcessor.generateOutput(positiveInput);
        String outputString = this.outContent.toString().trim();
        assertEquals("[00100,00133] [94200,94299] [94600,94699]", outputString);
    }

    @Test
    public void positiveTest3() {
        String FILE_PATH = "./resources/zipFile.txt";
        try {
            String positiveInput = readFile(FILE_PATH, UTF_8);
            zipCodeProcessor.generateOutput(positiveInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String outputString = this.outContent.toString().trim();
        assertEquals("[00000,00100] [12100,12100] [12101,12102]", outputString);
    }

    // starting negative test cases
    @Test
    public void negativeTest0() {
        String negativeInput = "[0100,00133] [94200,94299] [94600,94699]";
        zipCodeProcessor.generateOutput(negativeInput);
        String outputString = this.errContent.toString().trim();
        assertEquals("zip code format error", outputString);

    }

    @Test
    public void negativeTest1() {
        String negativeInput = "[01000500133] [94200,94299] [94600,94699]";
        zipCodeProcessor.generateOutput(negativeInput);
        String outputString = this.errContent.toString().trim();
        assertEquals("zip code bound is incomplete in input", outputString);

    }

    @Test
    public void negativeTest2() {
        String negativeInput = "[01000,00133] [94200,94299] [94600,94699]";
        zipCodeProcessor.generateOutput(negativeInput);
        String outputString = this.errContent.toString().trim();
        assertEquals("lower_bound cannot be bigger than upper_bound", outputString);

    }

    private String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
