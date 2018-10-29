import org.junit.Test;

import java.nio.file.Paths;

public class FirstStrategySodukuTest {

    @Test
    public void verify() throws Exception {
        String path = "src/main/resources/SamplePuzzles/Input";
//        String file = "Puzzle-4x4-0001.txt";
//        String file = "Puzzle-4x4-0002.txt";
//        String file = "Puzzle-4x4-0903.txt"; // same item in a row
//        String file = "Puzzle-4x4-0905.txt"; // rows missing
//        String file = "Puzzle-4x4-0907.txt"; // additional rows
        String file = "Puzzle-4x4-0906.txt"; // no blanks
        String filename = Paths.get(path, file).toString();

        FileReadCommand fileReadCommand = new FileReadCommand(filename);
        fileReadCommand.readPuzzle();


    }
}