import org.junit.Test;

import java.nio.file.Paths;

public class SudokuTest {

    @Test
    public void solve() {
    }

    @Test
    public void buildSoduko() {
    }

    @Test
    public void sanityCheck() throws Exception {
        String path = "src/main/resources/SamplePuzzles/Input";

        String[] invalidFiles = {
                "Puzzle-4x4-0901.txt",
                "Puzzle-4x4-0903.txt", // same item in a row
                "Puzzle-4x4-0905.txt", // rows missing -- invalid
                "Puzzle-4x4-0906.txt", // rows missing -- invalid
                "Puzzle-4x4-0907.txt", // additional row
                "Puzzle-25x25-0901.txt", // repeated characer in column 9
                "Puzzle-25x25-0904.txt", // invalid characters

        };

        String[] validFiles = {
                "Puzzle-4x4-0001.txt",
                "Puzzle-4x4-0101.txt",
                "Puzzle-4x4-0201.txt",
                "Puzzle-9x9-0002.txt",
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0201.txt",
                "Puzzle-9x9-0001.txt",
                "Puzzle-9x9-0002.txt",
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0102.txt",
                "Puzzle-9x9-0103.txt",
                "Puzzle-16x16-0001.txt",
        };

        // These are my invalid files
        for (String file : invalidFiles) {
            String filename = Paths.get(path, file).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            GuessACell sudoku = fileReadCommand.readSudoku();
            boolean result = sudoku.buildSoduko();
            assert !result;
        }

        // These are my valid files
        for (String file : validFiles) {
            System.out.println(file);
            String filename = Paths.get(path, file).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            GuessACell sudoku = fileReadCommand.readSudoku();
            assert sudoku.buildSoduko();
        }
    }

    @Test
    public void toStringTest() {

    }

    @Test
    public void missingInBlock() throws Exception {
        String path = "src/main/resources/SamplePuzzles/Input";

        String[] files = {
                "Puzzle-4x4-0001.txt",
                "Puzzle-4x4-0101.txt",
                "Puzzle-4x4-0201.txt",
                "Puzzle-9x9-0002.txt",
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0201.txt",
                "Puzzle-9x9-0001.txt",
                "Puzzle-9x9-0002.txt",
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0102.txt",
                "Puzzle-9x9-0103.txt",
                "Puzzle-16x16-0001.txt",
        };

        int[][] missingInRow4by4 = new int[][]{
                {1, 1, 1, 1},
                {2, 2, 3, 3},
                {3, 2, 3, 3},
        };

        for (int i = 0; i < 3; i++) {
            String filename = Paths.get(path, files[i]).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            GuessACell sudoku = fileReadCommand.readSudoku();
            sudoku.buildSoduko();

            for (int r = 0; r < 4; r++) {
                assert missingInRow4by4[i][r] == sudoku.missingInRowCount(r);
            }
        }

    }

    @Test
    public void missingInBlockCount() {
    }

    @Test
    public void missingInRowCount() throws Exception {
        String path = "src/main/resources/SamplePuzzles/Input";

        String[] files = {
                "Puzzle-4x4-0001.txt",
                "Puzzle-4x4-0101.txt",
                "Puzzle-4x4-0201.txt",
                "Puzzle-9x9-0001.txt",  //9*9
                "Puzzle-9x9-0002.txt",  //9*9
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0201.txt",
                "Puzzle-9x9-0001.txt",
                "Puzzle-9x9-0002.txt",
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0102.txt",
                "Puzzle-9x9-0103.txt",
                "Puzzle-16x16-0001.txt",
        };

        int[][] missingInRow4by4 = new int[][]{
                {1, 1, 1, 1},
                {2, 2, 3, 3},
                {3, 2, 3, 3},
        };

        for (int i = 0; i < 3; i++) {
            String filename = Paths.get(path, files[i]).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            GuessACell sudoku = fileReadCommand.readSudoku();
            sudoku.buildSoduko();

            for (int r = 0; r < 4; r++) {
                assert missingInRow4by4[i][r] == sudoku.missingInRowCount(r);
            }
        }

        int[][] missingInRow9by9 = new int[][]{
                {2, 5, 3, 5, 5, 5, 3, 4, 4}, //0001
                {5, 4, 8, 4, 4, 4, 8, 4, 5}, //0004
                {7, 4, 7, 4, 9, 4, 7, 4, 7}, // 0101
                {6, 6, 7, 7, 4, 7, 7, 6, 6}, // 0201
        };

        int j = 0;
        for (int i = 3; i < 7; i++) {
            String filename = Paths.get(path, files[i]).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            GuessACell sudoku = fileReadCommand.readSudoku();
            sudoku.buildSoduko();

//            System.out.println(files[i]);
            for (int r = 0; r < 9; r++) {
//                System.out.print(sudoku.missingInRowCount(r) + " " + missingInRow9by9[j][r] + "\t");
                assert missingInRow9by9[j][r] == sudoku.missingInRowCount(r);
            }
            j++;
        }


    }

    @Test
    public void missingInColCount() throws Exception{
        String path = "src/main/resources/SamplePuzzles/Input";

        String[] files = {
                "Puzzle-4x4-0001.txt",
                "Puzzle-4x4-0101.txt",
                "Puzzle-4x4-0201.txt",
                "Puzzle-9x9-0001.txt",  //9*9
                "Puzzle-9x9-0002.txt",  //9*9
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0201.txt",
                "Puzzle-9x9-0001.txt",
                "Puzzle-9x9-0002.txt",
                "Puzzle-9x9-0101.txt",
        };

        int[][] missingInCol4by4 = new int[][]{
                {1, 1, 1, 1},
                {2, 3, 3, 2},
                {3, 3, 3, 2},
        };

        for (int i = 0; i < 3; i++) {
            String filename = Paths.get(path, files[i]).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            GuessACell sudoku = fileReadCommand.readSudoku();
            sudoku.buildSoduko();

            for (int r = 0; r < 4; r++) {
                assert missingInCol4by4[i][r] == sudoku.missingInColCount(r);
            }
        }

        int[][] missingInCol9by9 = new int[][]{
                {5, 3, 4, 4, 3, 5, 5, 1, 6}, //0001
                {4, 4, 7, 6, 4, 6, 7, 4, 4}, //0004
                {5, 5, 9, 6, 3, 6, 9, 5, 5}, // 0101
                {5, 7, 7, 6, 6, 6, 7, 7, 5}, // 0201
        };

        int j = 0;
        for (int i = 3; i < 7; i++) {
            String filename = Paths.get(path, files[i]).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            GuessACell sudoku = fileReadCommand.readSudoku();
            sudoku.buildSoduko();

//            System.out.println(files[i]);
            for (int r = 0; r < 9; r++) {
//                System.out.print(sudoku.missingInRowCount(r) + " " + missingInRow9by9[j][r] + "\t");
                assert missingInCol9by9[j][r] == sudoku.missingInColCount(r);
            }
            j++;
        }

    }

    @Test
    public void totalMissingCells() throws Exception {
        String path = "src/main/resources/SamplePuzzles/Input";

        String[] files = {
                "Puzzle-4x4-0001.txt",
                "Puzzle-4x4-0101.txt",
                "Puzzle-4x4-0201.txt",
                "Puzzle-9x9-0001.txt",  //9*9
                "Puzzle-9x9-0002.txt",  //9*9
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0201.txt",
                "Puzzle-9x9-0001.txt",
                "Puzzle-9x9-0002.txt",
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0102.txt",
                "Puzzle-9x9-0103.txt",
                "Puzzle-16x16-0001.txt",
        };

        int[] totalMissingCount = new int[]{
                4, 10, 11, 36, 46, 53, 56
        };

        for (int i = 0; i < 7; i++) {
            String filename = Paths.get(path, files[i]).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            GuessACell sudoku = fileReadCommand.readSudoku();
            sudoku.buildSoduko();
//            System.out.println(sudoku.totalMissingCells());
            assert totalMissingCount[i] == sudoku.totalMissingCells();
        }

    }
}