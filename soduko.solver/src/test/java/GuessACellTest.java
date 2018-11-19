import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;

public class GuessACellTest extends BlocksTest {
    @Test
    public void solve() throws Exception {
        String inputPath = "src/main/resources/SamplePuzzles/Input";
        String assertsPath = "src/main/resources/SamplePuzzles/Asserts";

        String[] files = {
                "Puzzle-16x16-0101.txt", //
                "Puzzle-16x16-0201.txt", //
                "Puzzle-16x16-0301.txt", //

                "Puzzle-4x4-0201.txt", //
                "Puzzle-4x4-0904.txt", //
                "Puzzle-16x16-0901.txt",//
                "Puzzle-16x16-0902.txt", //
                "Puzzle-25x25-0101.txt", //
                "Puzzle-9x9-0101.txt", //
                "Puzzle-9x9-0401.txt", //
        };

        for (String file : files) {
            String filename = Paths.get(inputPath, file).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);

            Sudoku sudo = fileReadCommand.execute();
            GuessACell sudoku = new GuessACell(sudo.getSudokuSize(), sudo.getCharset(), sudo.getSudoku());
            sudoku.solveSudoku();

            String outputFilename = Paths.get(assertsPath, file).toString();
            String expectedOutput = readFile(outputFilename);

            System.out.println(sudoku.toString());
            System.out.println(expectedOutput);

            assert sudoku.getTotalMissingCells() != 0 || sudoku.toString().equals(expectedOutput);
        }
    }
}
