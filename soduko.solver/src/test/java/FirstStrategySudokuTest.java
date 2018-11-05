import org.junit.Test;

import java.nio.file.Paths;

public class FirstStrategySudokuTest {

    @Test
    public void verify() throws Exception {
        String path = "src/main/resources/SamplePuzzles/Input";
//        String file = "Puzzle-4x4-0001.txt";  // valid
        String file = "Puzzle-4x4-0101.txt";  // valid - works
//        String file = "Puzzle-4x4-0201.txt";  // valid - does not
//        String file = "Puzzle-4x4-0901.txt"; // invalid
//        String file = "Puzzle-4x4-0903.txt"; // same item in a row -- invalid
//        String file = "Puzzle-4x4-0905.txt"; // rows missing -- invalid
//        String file = "Puzzle-4x4-0907.txt"; // additional rows -- invalid
//        String file = "Puzzle-9x9-0001.txt"; // valid
//        String file = "Puzzle-9x9-0002.txt"; // valid
//        String file = "Puzzle-9x9-0101.txt"; // valid

        String filename = Paths.get(path, file).toString();

        FileReadCommand fileReadCommand = new FileReadCommand(filename);
        FirstStrategySoduku soduku = fileReadCommand.readPuzzle();
        boolean valid = soduku.buildSoduko();

        if (valid) {
            soduku.solvePart();
            soduku.sanityCheck();
            System.out.println(soduku.toString());
        }
    }
}