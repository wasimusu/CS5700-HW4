import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Sudoku implements SudokuBasic {
    protected int[][] cells;
    private HashMap<String, Integer> char_to_int = new HashMap<String, Integer>();
    private HashMap<Integer, String> int_to_char = new HashMap<Integer, String>();
    private String sudoku;
    private String charset;
    protected int sudokuSize;
    protected int blockSize; // square root of sudoku size
    protected int BLANK = -2;
    private long executionTime = 0; // total time of execution
    protected HashMap<Integer, Integer> minimumCells = new HashMap<Integer, Integer>();

    public Sudoku(int sudokuSize, String map, String sudoku) {
        cells = new int[sudokuSize][sudokuSize];
        this.charset = map;
        this.sudoku = sudoku;  // the user input
        this.sudokuSize = sudokuSize;

        this.minimumCells.put(4, 4);
        this.minimumCells.put(9, 17);
        this.minimumCells.put(16, 16);
        this.minimumCells.put(25, 25);

        this.blockSize = (int) Math.sqrt((double) sudokuSize);
    }

    // This is the solve sudoku template
    public void solveSudoku(String map, String sudoku) {
        long startTime = System.currentTimeMillis();
        boolean validSudoku = this.buildSoduko(); // build sudoku from the map, sudoku
        // If the sudoku is valid solve only then
        if (validSudoku) {
            boolean sane = this.sanityCheck(); // check if the sudoku is correct
            if (sane) this.solve(map, sudoku); // if boolean is sane
        }
        long endTime = System.currentTimeMillis();
        this.executionTime = endTime - startTime;
    }

    public void solve(String map, String sudoku) {
    }


    protected boolean buildSoduko() {
        // returns true if the sudoku is valid else false
        // Checks of invalid characters and invalid number of rows and columns
        // Also calls sanity check to find repeatition of characters

        int missingItemCount = sudoku.length() - sudoku.replace("-", "").length();
        boolean sane = true; // if the sudoku is valid or not

        if (missingItemCount < this.minimumCells.get(this.sudokuSize)) {
            System.out.println("Less number of missing elements than permitted.");
            return false;
        }
        String[] maps = this.charset.split(" ");

        // Insert BLANK char into the hashmap
        this.char_to_int.put("-", BLANK);
        this.int_to_char.put(BLANK, "-");

        // Build dictionary for sudoku to cell conversion and vice versa
        for (int i = 1; i <= this.sudokuSize; i++) {
            this.int_to_char.put(i, maps[i - 1]);
            this.char_to_int.put(maps[i - 1], i);
        }

        // Populate members of the cells
        String[] sudokuElements = this.sudoku.split(System.lineSeparator());
        if (sudokuElements.length != this.sudokuSize) {
            System.out.println("Invalid dimension of sudoku.");
            return false;
        }
        for (int i = 0; i < this.sudokuSize; i++) {
            String[] items = sudokuElements[i].split(" ");
            if (items.length != this.sudokuSize) {
                System.out.println("Invalid dimension of sudoku.");
                return false;
            }
            for (int j = 0; j < this.sudokuSize; j++) {
                if (char_to_int.containsKey(items[j]))
                    cells[i][j] = char_to_int.get(items[j]);
                else {
                    System.out.println("Contains invalid characters");
                    return false;
                }
            }
        }
        return this.sanityCheck();
    }

    public boolean sanityCheck() {
        // Check if the sudoku you're going to solve is correct
        // Check if the sudoku that you solved is correct
        int item = BLANK;
        for (int i = 0; i < this.sudokuSize; i++) {

            ArrayList<Integer> colCharset = new ArrayList<Integer>();
            ArrayList<Integer> rowCharset = new ArrayList<Integer>();

            for (int j = 0; j < this.sudokuSize; j++) {

                item = cells[i][j]; // row
                if (rowCharset.contains(item) && item != BLANK) {
                    System.out.println("Contains repeated item in row : " + i + " : " + item);
                    return false;
                } else {
                    rowCharset.add(item);
                }

                item = cells[j][i]; // col
                if (colCharset.contains(item) && item != BLANK) {
                    System.out.println("Contains repeated item in column : " + j);
                    return false;
                } else {
                    colCharset.add(item);
                }
            }
        }
        return true;
    }

    public int[] getConflictingCell() {
        // Check if the sudoku you're going to solve is correct
        // Check if the sudoku that you solved is correct

        int conflictRow = -1;
        int conflictCol = -1;
        int item = BLANK;
        for (int i = 0; i < this.sudokuSize; i++) {
            ArrayList<Integer> colCharset = new ArrayList<Integer>();
            ArrayList<Integer> rowCharset = new ArrayList<Integer>();

            for (int j = 0; j < this.sudokuSize; j++) {
                item = cells[i][j]; // row
                if (rowCharset.contains(item) && item != BLANK) {
                    System.out.println("Contains repeated item in row : " + i + " : " + item);
                    // So we detected a conflict, see which position earlier in this row had this same value
                    for (int r = 0; r < this.sudokuSize; r++) {
                        if (this.cells[r][j] == item) {
                            conflictRow = j;
                        }
                    }
                    return new int[]{i, conflictRow};
                } else {
                    rowCharset.add(item);
                }

                item = cells[j][i]; // col
                if (colCharset.contains(item) && item != BLANK) {
                    System.out.println("Contains repeated item in column : " + j);
                    // So we detected a conflict, see which position earlier in this row had this same value
                    for (int c = 0; c < this.sudokuSize; c++) {
                        if (this.cells[j][c] == item) {
                            conflictCol = c;
                        }
                    }
                    return new int[]{j, conflictCol};
                } else {
                    colCharset.add(item);
                }
            }
        }
        return new int[]{conflictRow, conflictCol};
    }

    public String toString() {
        // Convert the solved sudoku to string to display on console or write to file
        String stringPuzzle = "";
        for (int i = 0; i < this.sudokuSize; i++) {
            for (int j = 0; j < this.sudokuSize; j++) {
                stringPuzzle = stringPuzzle.concat(int_to_char.get(this.cells[i][j]) + " ");
//                stringPuzzle = stringPuzzle.concat(String.valueOf(cells[i][j]) + " ");
            }
            stringPuzzle = stringPuzzle.concat(System.lineSeparator());
        }
        return stringPuzzle;
    }

    public int[] missingInBlock(int r, int c, int missCount) {
        // Returns array of missing numbers in a part - quad or nonet
//        System.out.println(r + " " + c + " : " + missCount);
        int[] missing = new int[missCount];
        HashSet<Integer> present = new HashSet<>();
        int index = 0;
        for (int i = r * blockSize; i < r * blockSize + blockSize; i++) {
            for (int j = c * blockSize; j < c * blockSize + blockSize; j++) {
                if (this.cells[i][j] >= 0) {
                    present.add(this.cells[i][j]);
                    index++;
                }
            }
        }

        index = 0;
        for (int i = 1; i <= sudokuSize; i++) {
            if (!present.contains(i)) {
                missing[index] = i;
                index++;
            }
        }

        return missing;
    }

    public int getMissingInBlockCount(int r, int c) {
        // How many items are missing in a quad or nonet and so on
        int count = 0;
        for (int i = r * blockSize; i < r * blockSize + blockSize; i++) {
            for (int j = c * blockSize; j < c * blockSize + blockSize; j++) {
                if (this.cells[i][j] < 0) count++;
            }
        }
        if (count == 0) {
            return this.sudokuSize;
        } else return count;
    }

    public int getMissingInRowCount(int r) {
        // No of missing item in given row
        int count = 0;
        for (int i = 0; i < this.sudokuSize; i++) {
            if (this.cells[r][i] < 0) count++;
        }
        if (count == 0) {
            {
                return this.sudokuSize;
            }
        } else return count;
    }

    public int getMissingInColCount(int c) {
        // No of missing item in given col
        int count = 0;
        for (int i = 0; i < this.sudokuSize; i++) {
            if (this.cells[i][c] < 0) count++;
        }
        if (count == 0) {
            return this.sudokuSize;
        } else return count;
    }

    public int getTotalMissingCells() {
        int count = 0;
        for (int i = 0; i < this.sudokuSize; i++) {
            for (int j = 0; j < this.sudokuSize; j++) {
                if (this.cells[i][j] == this.BLANK) count++;
            }
        }
        return count;
    }

    public boolean isValidForCell(int row, int col, int value) {
        // Check row and col to determine if a value is valid for a particular cell
        boolean valid = true;
        for (int i = 0; i < sudokuSize; i++) {
            if (value == this.cells[row][i]) {
                valid = false;
                break;
            }
            if (value == this.cells[i][col]) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public String getSummary(){
        // This is the output that is written to output file
        return "Input Sudoku :\n" +
                this.sudoku + System.lineSeparator() + "\n" +
                "Output Sudoku :\n" +
                this.toString() + "\n\n" +
                "Execution Time : " + String.valueOf(this.executionTime) + System.lineSeparator();
    }
}
