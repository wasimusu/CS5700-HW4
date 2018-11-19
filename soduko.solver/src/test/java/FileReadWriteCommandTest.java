import org.junit.Test;

import java.nio.file.Paths;

public class FileReadWriteCommandTest {

    @Test
    public void testByCommandInspection() throws Exception {
        String inputPath = "src/main/resources/SamplePuzzles/Input";
        String outputPath = "src/main/resources/SamplePuzzles/Outputs2";

        String[] files = {
                "Puzzle-4x4-0201.txt", // twins is not solved by blocks
                "Puzzle-4x4-0904.txt", // twins - any guess is correct guess
                "Puzzle-16x16-0902.txt", // valid - solved
                "Puzzle-16x16-0901.txt",// valid - too many correct guess required
                "Puzzle-9x9-0101.txt", // works most of the times -- only one correct guess is required
                "Puzzle-9x9-0401.txt", // works most of the times -- only one correct guess is required
                "Puzzle-25x25-0101.txt", // valid - sometimes it solves

//                "Puzzle-16x16-0101.txt", // valid - too many correct guess required
//                "Puzzle-16x16-0201.txt", // valid - too many correct guess required
//                "Puzzle-16x16-0301.txt", // valid - too many correct guess required
        };

        for (String file : files) {
            String inputFilename = Paths.get(inputPath, file).toString();
            String outputFilename = Paths.get(outputPath, file).toString();
            FileReadWriteCommand writeSolved = new FileReadWriteCommand(inputFilename, outputFilename);
            Sudoku sudo = writeSolved.execute();
            SudokuStrategy strategy = new SudokuStrategy(sudo.getSudokuSize(), sudo.getCharset(), sudo.getSudoku());
            strategy.solve();
        }
    }
}