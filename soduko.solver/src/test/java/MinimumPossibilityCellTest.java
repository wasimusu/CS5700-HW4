import org.junit.Test;

import java.nio.file.Paths;

public class MinimumPossibilityCellTest extends BlocksTest {
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
            MinimumPossibilityCell sudoku = new MinimumPossibilityCell(sudo.getSudokuSize(), sudo.getCharset(), sudo.getSudoku());
            sudoku.solveSudoku();
            String outputFilename = Paths.get(assertsPath, file).toString();
            String expectedOutput = readFile(outputFilename);
            System.out.println(sudoku.toString());
            System.out.println(expectedOutput);
            assert sudoku.toString().equals(expectedOutput);
        }
    }
}