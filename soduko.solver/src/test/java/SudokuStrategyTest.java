import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SudokuStrategyTest extends BlocksTest{
    @Test
    public void solve() throws Exception {
        String inputPath = "src/main/resources/SamplePuzzles/Input";

        String[] files = {
                "Puzzle-4x4-0201.txt", // twins is not solved by blocks
                "Puzzle-4x4-0904.txt", // twins - any guess is correct guess
                "Puzzle-16x16-0901.txt",// valid - too many correct guess required
                "Puzzle-16x16-0902.txt", //
                "Puzzle-25x25-0101.txt", // valid
                "Puzzle-9x9-0101.txt", // only one correct guess is required
                "Puzzle-9x9-0401.txt", // only one correct guess is required
        };

        for (String file : files) {
            String filename = Paths.get(inputPath, file).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);

            Sudoku sudo = fileReadCommand.execute();
            SudokuStrategy sudoku = new SudokuStrategy(sudo.getSudokuSize(), sudo.getCharset(), sudo.getSudoku());
            sudoku.solve();

            System.out.println(sudoku.toString());
        }
    }
}