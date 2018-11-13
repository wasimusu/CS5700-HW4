import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;

public class GuessACellTest extends BlocksTest {
    @Test
    public void solve() throws Exception {
        String inputPath = "src/main/resources/SamplePuzzles/Input";
        String assertsPath = "src/main/resources/SamplePuzzles/Asserts";

        String[] files = {
//                "Puzzle-4x4-0201.txt", // twins is not solved by blocks
//                "Puzzle-4x4-0904.txt", // twins - any guess is correct guess
//                "Puzzle-16x16-0101.txt", // valid - too many correct guess required
//                "Puzzle-16x16-0201.txt", // valid - too many correct guess required
//                "Puzzle-16x16-0301.txt", // valid - too many correct guess required
//                "Puzzle-16x16-0901.txt",// valid - too many correct guess required
                "Puzzle-16x16-0902.txt", // valid - sometimes it solves
//                "Puzzle-25x25-0901.txt", // valid - sometimes it solves
//                "Puzzle-9x9-0101.txt", // works most of the times -- only one correct guess is required
//                "Puzzle-9x9-0401.txt", // works most of the times -- only one correct guess is required
        };

        for (String file : files) {
            String filename = Paths.get(inputPath, file).toString();
//            FileReadCommand fileReadCommand = new FileReadCommand(filename);
//
//            Sudoku sudo = fileReadCommand.execute();
//            SudokuStrategy strategy = new SudokuStrategy(sudo.getSudokuSize(), sudo.getCharset(), sudo.getSudoku());
//            strategy.solve();
            FileReadWriteCommand writeSudoku = new FileReadWriteCommand(filename, "Newfile.txt");
            writeSudoku.execute();
//            Sudoku sudo = fileReadCommand.execute();
//            SudokuStrategy strategy = new SudokuStrategy(sudo.getSudokuSize(), sudo.getCharset(), sudo.getSudoku());
//            strategy.solve();

//            GuessACell sudoku = new GuessACell(sudo.getSudokuSize(), sudo.getCharset(), sudo.getSudoku());
//            sudoku.buildSoduko();
//            sudoku.solve();
//
//            String outputFilename = Paths.get(assertsPath, file).toString();
//            String expectedOutput = readFile(outputFilename);
//
//            System.out.println(sudoku.toString());
//            System.out.println(expectedOutput);
//
//            assert sudoku.toString().equals(expectedOutput);
        }
    }

    @Test
    public void testSnaps() {
        ArrayList<int[][]> snapshots;
        snapshots = new ArrayList<int[][]>();

        int[][] org = new int[][]{{1, 1}, {1, 1}};
        snapshots.add(org.clone());

        for (int i = 1; i <= 2; i++) {
            int[][] tempOrg = new int[2][2];
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    tempOrg[j][k] = org[j][k] + i;
                }
            }
            snapshots.add(tempOrg.clone());
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    System.out.print((snapshots.get(i)[j][k]) + " ");
                }
                System.out.println();
            }
        }
    }
}