import org.junit.Test;
import java.nio.file.Paths;
public class FileReadWriteCommandTest {

    @Test
    public void testByCommandInspection() throws Exception {
        String inputPath = "src/main/resources/SamplePuzzles/Input";
        String outputPath = "src/main/resources/SamplePuzzles/Outputs2";

        String[] files = {
                "Puzzle-16x16-0001.txt", //
                "Puzzle-16x16-0101.txt", //
                "Puzzle-16x16-0102.txt", //
                "Puzzle-16x16-0201.txt", //

//                "Puzzle-16x16-0901.txt",//
//                "Puzzle-16x16-0902.txt", //
//
//                "Puzzle-4x4-0201.txt", //
//                "Puzzle-4x4-0904.txt", //
//                "Puzzle-25x25-0101.txt", //
//                "Puzzle-9x9-0101.txt", //
//                "Puzzle-9x9-0401.txt", //
//
//                "Puzzle-4x4-0901.txt", //
//                "Puzzle-4x4-0901.txt",
//                "Puzzle-4x4-0903.txt", // same item in a row
//                "Puzzle-4x4-0905.txt", // rows missing -- invalid
//                "Puzzle-4x4-0906.txt", // rows missing -- invalid
//                "Puzzle-4x4-0907.txt", // additional row
//                "Puzzle-25x25-0901.txt", // repeated characer in column 9
//                "Puzzle-25x25-0904.txt", // invalid characters
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