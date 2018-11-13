import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class BlocksTest {

    public String readFile(String filename) throws Exception {
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        String puzzle = "";
        while ((line = reader.readLine()) != null)
            puzzle = puzzle.concat(line + System.lineSeparator());

        return puzzle;
    }

    @Test
    public void solve() throws Exception {
        String inputPath = "src/main/resources/SamplePuzzles/Input";
        String assertsPath = "src/main/resources/SamplePuzzles/Asserts";

        String[] files = {
                "Puzzle-4x4-0001.txt",
                "Puzzle-4x4-0002.txt",
                "Puzzle-4x4-0101.txt",
//                "Puzzle-4x4-0201.txt", // twins is not solved by blocks
//                "Puzzle-4x4-0904.txt", // twins - any guess is correct guess
//                "Puzzle-4x4-0902.txt",
        };

        for (String file : files) {
            String filename = Paths.get(inputPath, file).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);

            Sudoku sudo = fileReadCommand.execute();
            Blocks sudoku = new Blocks(sudo.getSudokuSize(), sudo.getCharset(), sudo.getSudoku());
            sudoku.buildSoduko();
            sudoku.solve();

            String outputFilename = Paths.get(assertsPath, file).toString();
            String expectedOutput = readFile(outputFilename);

            System.out.println(sudoku.toString());
            System.out.println(expectedOutput);

            assert sudoku.toString().equals(expectedOutput);
        }
    }

    @Test
    public void verify() throws Exception {
        String path = "src/main/resources/SamplePuzzles/Input";

//        String file = "Puzzle-4x4-0901.txt"; // invalid
//        String file = "Puzzle-4x4-0903.txt"; // same item in a row -- invalid
//        String file = "Puzzle-4x4-0905.txt"; // rows missing -- invalid
//        String file = "Puzzle-4x4-0907.txt"; // additional rows -- invalid
//        String file = "Puzzle-25x25-0904.txt"; // invalid - solved
//        String file = "Puzzle-4x4-0903.txt"; // invalid - workout
//        String file = "Puzzle-4x4-0902.txt"; // invalid - workout
//        String file = "Puzzle-25x25-0901.txt"; // invalid - column 8 contains repeated items

//        String file = "Puzzle-16x16-0001.txt"; // valid - solved
//        String file = "Puzzle-25x25-0101.txt"; // valid - solved
//        String file = "Puzzle-4x4-0001.txt";  // valid - solved using blocks
//        String file = "Puzzle-9x9-0002.txt"; // valid - solved
//        String file = "Puzzle-9x9-0001.txt"; // valid - solved
//        String file = "Puzzle-4x4-0201.txt";  // valid - solved
//        String file = "Puzzle-4x4-0101.txt";  // valid - solved - blocks
        String file = "Puzzle-4x4-0001.txt";  // valid - solved - blocks
//        String file = "Puzzle-25x25-0101.txt"; // valid - solved
//        String file = "Puzzle-16x16-0102.txt"; // valid - solved

//        String file = "Puzzle-16x16-0101.txt"; // valid - too many correct guess required
//        String file = "Puzzle-16x16-0201.txt"; // valid - too many correct guess required
//        String file = "Puzzle-16x16-0301.txt"; // valid - too many correct guess required
//        String file = "Puzzle-16x16-0901.txt"; // valid - too many correct guess required
//        String file = "Puzzle-16x16-0902.txt"; // valid - sometimes it solves

//        String file = "Puzzle-4x4-0904.txt"; // twins - any guess is correct guess
//        String file = "Puzzle-9x9-0101.txt"; // works most of the times -- only one correct guess is required
//        String file = "Puzzle-16x16-0101.txt"; // valid - requires too many correct guesses

        String filename = Paths.get(path, file).toString();

        FileReadCommand fileReadCommand = new FileReadCommand(filename);
        Sudoku sudo = fileReadCommand.execute();
        OnlyChoice sudoku = new OnlyChoice(sudo.getSudokuSize(), sudo.getCharset(), sudo.getSudoku());
        sudoku.buildSoduko();
        sudoku.solve();

        System.out.println(sudoku.toString());
        System.out.println("Missing : " + sudoku.getTotalMissingCells());
    }

}