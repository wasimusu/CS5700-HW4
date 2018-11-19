import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

public class BlocksTest {

    String readFile(String filename) throws Exception {
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
        };

        for (String file : files) {
            String filename = Paths.get(inputPath, file).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);

            Sudoku sudo = fileReadCommand.execute();
            Blocks sudoku = new Blocks(sudo.getSudokuSize(), sudo.getCharset(), sudo.getSudoku());
            sudoku.solveSudoku();

            String outputFilename = Paths.get(assertsPath, file).toString();
            String expectedOutput = readFile(outputFilename);

            System.out.println(sudoku.toString());
            System.out.println(expectedOutput);

            assert sudoku.toString().equals(expectedOutput);
        }
    }
}