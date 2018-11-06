import org.junit.Test;

import java.nio.file.Paths;

public class FirstStrategySudokuTest {

    @Test
    public void verify() throws Exception {
        String path = "src/main/resources/SamplePuzzles/Input";

//        String file = "Puzzle-4x4-0901.txt"; // invalid
//        String file = "Puzzle-4x4-0903.txt"; // same item in a row -- invalid
//        String file = "Puzzle-4x4-0905.txt"; // rows missing -- invalid
//        String file = "Puzzle-4x4-0907.txt"; // additional rows -- invalid
//        String file = "Puzzle-25x25-0904.txt"; // invalid - solved

//        String file = "Puzzle-4x4-0001.txt";  // valid
//        String file = "Puzzle-9x9-0002.txt"; // valid - solved
//        String file = "Puzzle-9x9-0001.txt"; // valid - solved
//        String file = "Puzzle-4x4-0201.txt";  // valid - solved
//        String file = "Puzzle-4x4-0101.txt";  // valid - solved
//        String file = "Puzzle-16x16-0001.txt"; // valid - solved
//        String file = "Puzzle-25x25-0101.txt"; // valid - solved

//        String file = "Puzzle-9x9-0101.txt"; // valid - sometimes the guess is correct
        String file = "Puzzle-4x4-0904.txt"; // valid - twins - correct guess
//        String file = "Puzzle-4x4-0903.txt"; // invalid - workout
//        String file = "Puzzle-4x4-0902.txt"; // invalid - workout
//        String file = "Puzzle-16x16-0101.txt"; // invalid - workout
//        String file = "Puzzle-25x25-0901.txt"; // valid - solved -- OutOfBoundException
//        String file = "Puzzle-16x16-0201.txt"; // valid - solved -- OutOfBoundException

        String filename = Paths.get(path, file).toString();

        FileReadCommand fileReadCommand = new FileReadCommand(filename);
//        FirstStrategySoduku soduku = fileReadCommand.readPuzzle();
        GuessACell soduku = fileReadCommand.readPuzzle();
    }
}