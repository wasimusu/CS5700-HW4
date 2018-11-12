import org.junit.Test;

import java.nio.file.Paths;

public class SudokuTest {

    @Test
    public void solve() {
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
                "Puzzle-4x4-0904.txt", // twins - any guess is correct guess
                "Puzzle-4x4-0902.txt", // invalid - workout

                "Puzzle-9x9-0001.txt", // valid - solved
                "Puzzle-9x9-0101.txt", // works most of the times -- only one correct guess is required
                "Puzzle-9x9-0002.txt",
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0201.txt",
                "Puzzle-9x9-0001.txt",
                "Puzzle-9x9-0002.txt",
                "Puzzle-9x9-0101.txt",
                "Puzzle-9x9-0102.txt",
                "Puzzle-9x9-0103.txt",
                "Puzzle-16x16-0001.txt",
                "Puzzle-16x16-0101.txt",
                "Puzzle-16x16-0102.txt", // valid - solved
                "Puzzle-16x16-0201.txt", // valid - too many correct guess required
                "Puzzle-16x16-0301.txt", // valid - too many correct guess required
                "Puzzle-16x16-0901.txt", // valid - too many correct guess required
                "Puzzle-16x16-0902.txt", // valid - sometimes it solves
                "Puzzle-25x25-0101.txt", // valid - solved
        };


        // These are my invalid files
        for (String file : invalidFiles) {
            String filename = Paths.get(path, file).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            Sudoku sudoku = fileReadCommand.execute();
            boolean result = sudoku.buildSoduko();
//            System.out.println(file);
            assert !result;
        }

        // These are my valid files
        for (String file : validFiles) {
            System.out.println(file);
            String filename = Paths.get(path, file).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            Sudoku sudoku = fileReadCommand.execute();
            boolean result = sudoku.buildSoduko();
//            System.out.println(file);
            assert result;
        }
    }

    @Test
    public void toStringTest() throws Exception {
        String path = "src/main/resources/SamplePuzzles/Input";

        String[] files = {
                "Puzzle-4x4-0001.txt",
                "Puzzle-4x4-0101.txt",
                "Puzzle-4x4-0201.txt",
                "Puzzle-4x4-0001.txt",
                "Puzzle-4x4-0101.txt",
                "Puzzle-4x4-0201.txt",
                "Puzzle-4x4-0904.txt", // twins - any guess is correct guess
                "Puzzle-4x4-0902.txt",}; // invalid - workout        };

        for(String file: files){
            String filename = Paths.get(path, file).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            Sudoku sudoku = fileReadCommand.execute();
            sudoku.buildSoduko();

            // getSudoku returns the origin sudoku and toString converts cells back to original sudoku
            // we did not solve any cell here.
            assert sudoku.toString().equals(sudoku.getSudoku());
        }
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

        int[][] missingInBlock4by4 = new int[][]{
                {1, 1, 1, 1},
                {2, 2, 3, 3},
                {3, 2, 3, 3},
        };

        for (int i = 0; i < 3; i++) {
            String filename = Paths.get(path, files[i]).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            Sudoku sudoku = fileReadCommand.execute();
            sudoku.buildSoduko();

            for (int r = 0; r < 4; r++) {
                assert missingInBlock4by4[i][r] == sudoku.getMissingInRowCount(r);
            }
        }

    }

    @Test
    public void getMissingInBlockCount() throws Exception {
        String path = "src/main/resources/SamplePuzzles/Input";

        String[] files = {
                "Puzzle-4x4-0001.txt",
                "Puzzle-4x4-0201.txt",
                "Puzzle-9x9-0201.txt",
        };

        int[][][] missingInBlock4by4 = new int[][][]{
                {{1, 1}, {1, 1}},
                {{3, 2}, {3, 3}},
        };

        int[][] missingInBlock9by9 = new int[][]{
                {6, 6, 7},
                {6, 6, 6},
                {7, 6, 6},
        };

        for (int k = 0; k < 2; k++) {
            String filename = Paths.get(path, files[k]).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            Sudoku sudoku = fileReadCommand.execute();
            sudoku.buildSoduko();
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int missingBlock = sudoku.getMissingInBlockCount(i, j);
//                    System.out.print(missingBlock + " ");
                    assert missingInBlock4by4[k][i][j] == missingBlock;
                }
//                System.out.println();
            }
        }

        String filename = Paths.get(path, files[2]).toString();
        FileReadCommand fileReadCommand = new FileReadCommand(filename);
        Sudoku sudoku = fileReadCommand.execute();
        sudoku.buildSoduko();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int missingBlock = sudoku.getMissingInBlockCount(i, j);
//                System.out.print(missingBlock + " ");
                assert missingInBlock9by9[i][j] == missingBlock;
            }
//            System.out.println();
        }

    }

    @Test
    public void getMissingInRowCount() throws Exception {
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
            Sudoku sudoku = fileReadCommand.execute();
            sudoku.buildSoduko();

            for (int r = 0; r < 4; r++) {
                assert missingInRow4by4[i][r] == sudoku.getMissingInRowCount(r);
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
            Sudoku sudoku = fileReadCommand.execute();
            sudoku.buildSoduko();

//            System.out.println(files[i]);
            for (int r = 0; r < 9; r++) {
//                System.out.print(sudoku.getMissingInRowCount(r) + " " + missingInRow9by9[j][r] + "\t");
                assert missingInRow9by9[j][r] == sudoku.getMissingInRowCount(r);
            }
            j++;
        }


    }

    @Test
    public void getMissingInColCount() throws Exception {
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
            Sudoku sudoku = fileReadCommand.execute();
            sudoku.buildSoduko();

            for (int r = 0; r < 4; r++) {
                assert missingInCol4by4[i][r] == sudoku.getMissingInColCount(r);
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
            Sudoku sudoku = fileReadCommand.execute();
            sudoku.buildSoduko();

//            System.out.println(files[i]);
            for (int r = 0; r < 9; r++) {
//                System.out.print(sudoku.getMissingInRowCount(r) + " " + missingInRow9by9[j][r] + "\t");
                assert missingInCol9by9[j][r] == sudoku.getMissingInColCount(r);
            }
            j++;
        }

    }

    @Test
    public void getTotalMissingCells() throws Exception {
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
            Sudoku sudoku = fileReadCommand.execute();
            sudoku.buildSoduko();
//            System.out.println(sudoku.getTotalMissingCells());
            assert totalMissingCount[i] == sudoku.getTotalMissingCells();
        }

    }
}