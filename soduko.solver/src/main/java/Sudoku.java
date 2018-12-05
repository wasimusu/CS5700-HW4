import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Sudoku {
    protected int[][] cells;
    private HashMap<String, Integer> char_to_int = new HashMap<String, Integer>();
    private HashMap<Integer, String> int_to_char = new HashMap<Integer, String>();
    private String sudoku;
    private String charset; // character set used in sudoku
    protected int sudokuSize;
    protected int blockSize; // square root of sudoku size
    protected int BLANK = -2;
    private long executionTime = 0; // total time of execution
    public String status = "";

    // The difference between these values is used to check is a solver is making progress or not and thus if it should stop
    protected int previouslyMissing = 10000000;
    protected int currentlyMissing = 100000000;

    public int getSudokuSize() {
        return sudokuSize;
    }

    public String getCharset() {
        return charset;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public Sudoku(int sudokuSize, String map, String sudoku) {
        cells = new int[sudokuSize][sudokuSize];
        this.charset = map;
        this.sudoku = sudoku;  // the user input
        this.sudokuSize = sudokuSize;
        this.blockSize = (int) Math.sqrt((double) sudokuSize);
    }

    // This is the template method for solving sudoku. This can't be overridden
    public final Sudoku solveSudoku() {
        long startTime = System.currentTimeMillis();

        boolean validSudoku = this.buildSoduko(); // convert string to 2d array of intd
        if (validSudoku) this.solve(); // solve if the sudoku is valid

        long endTime = System.currentTimeMillis();
        this.executionTime = endTime - startTime;

        // There is still possibility that the sudoku might not be solved even after attempts
        if (this.getTotalMissingCells() == 0) status = "solved";

        Sudoku sudoku = new Sudoku(this.getSudokuSize(), this.getCharset(), this.toString());
        sudoku.buildSoduko();
        return sudoku;
    }

    public Sudoku solve() {
        System.out.println("This should not be really called at all except for testing");
        return this;
    }

    public String getSudoku() {
        return sudoku;
    }

    protected boolean buildSoduko() {
        // returns true if the sudoku is valid else false
        // Checks for invalid characters and invalid number of rows and columns
        // Also calls sanity check to find repeatition of characters

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
            this.status = "Invalid dimension of sudoku";
            return false;
        }
        for (int i = 0; i < this.sudokuSize; i++) {
            String[] items = sudokuElements[i].split(" ");
            if (items.length != this.sudokuSize) {
                this.status = "Invalid dimension of sudoku";
                return false;
            }
            for (int j = 0; j < this.sudokuSize; j++) {
                if (char_to_int.containsKey(items[j]))
                    cells[i][j] = char_to_int.get(items[j]);
                else {
                    this.status = "Sudoku contains invalid characters";
                    return false;
                }
            }
        }
        return this.sanityCheck();
    }

    public boolean sanityCheck() {
        // Check if the sudoku you're going to solve is correct
        // Check if the sudoku that you solved is correct
        for (int i = 0; i < this.sudokuSize; i++) {
            ArrayList<Integer> colCharset = new ArrayList<Integer>();
            ArrayList<Integer> rowCharset = new ArrayList<Integer>();

            for (int j = 0; j < this.sudokuSize; j++) {

                int item = cells[i][j]; // row
                if (item != BLANK && rowCharset.contains(item)) {
                    this.status = "Contains repeated item in row " + i;
                    return false;
                } else {
                    rowCharset.add(item);
                }

                item = cells[j][i]; // col
                if (item != BLANK && colCharset.contains(item)) {
                    this.status = "Contains repeated item in column " + j;
                    return false;
                } else {
                    colCharset.add(item);
                }
            }
        }
        return true;
    }

    public String toString() {
        // Convert the solved sudoku to string to display on console or write to file
        StringBuilder stringPuzzle = new StringBuilder();
        for (int i = 0; i < this.sudokuSize; i++) {
            for (int j = 0; j < this.sudokuSize; j++) {
                if (j == this.sudokuSize - 1) stringPuzzle.append(int_to_char.get(this.cells[i][j]));
                else stringPuzzle.append(int_to_char.get(this.cells[i][j]) + " ");
            }
            stringPuzzle.append(System.lineSeparator());
        }
        return stringPuzzle.toString();
    }

    public int[] missingInBlock(int r, int c) {
        // Returns array of missing numbers in a part - quad or nonet
        // missing contains all the possible values
        HashSet<Integer> missingHash = new HashSet<>();
        for (int i = 1; i <= this.sudokuSize; i++)
            missingHash.add(i);

        for (int i = r * blockSize; i < r * blockSize + blockSize; i++) {
            for (int j = c * blockSize; j < c * blockSize + blockSize; j++) {
                int value = cells[i][j];
                if (missingHash.contains(value)) {
                    missingHash.remove(value);
                }
            }
        }

        int[] missing = new int[missingHash.size()];
        int index = 0;
        for (int num : missingHash) {
            missing[index] = num;
            index++;
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
            return this.sudokuSize;
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

    protected boolean isValidForCell(int row, int col, int value) {
        // Check row and col to determine if a value is valid for a particular cell
        for (int i = 0; i < sudokuSize; i++) {
            if ((value == this.cells[row][i]) || (value == this.cells[i][col])) return false;
        }
        return true;
    }

    public String getSummary() {
        // This is the output that is written to output file
        String summary = String.valueOf(this.sudokuSize) + System.lineSeparator() +
                this.charset + System.lineSeparator() +
                this.getSudoku() + System.lineSeparator();

        if (!status.equals("Solved")) summary = summary.concat(status + "\n");
        return summary;
    }
}
