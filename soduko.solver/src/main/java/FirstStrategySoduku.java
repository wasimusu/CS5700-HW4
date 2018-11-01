import java.util.ArrayList;
import java.util.HashMap;

public class FirstStrategySoduku {
    private int[][] cells;
    private HashMap<String, Integer> char_to_int = new HashMap<String, Integer>();
    private HashMap<Integer, String> int_to_char = new HashMap<Integer, String>();
    private String puzzle;
    private String charset;
    private int puzzleSize;
    private int sudokuSum;

    private int[] missingRow;
    private int[] missingCol;

    public FirstStrategySoduku(int puzzleSize, String map, String puzzle) {
        String[] maps = map.split(" ");
        cells = new int[puzzleSize][puzzleSize];
        this.charset = map;
        this.puzzle = puzzle;
        this.puzzleSize = puzzleSize;
    }

    public void solve() {
        missingCol = new int[this.puzzleSize];
        missingRow = new int[this.puzzleSize];
        int minColIndex = 1000; // Row with minimum missing elements
        int minRowIndex = 1000; // Col with minimum missing elements
        int min = 1000;         // count of minimum missing elements
        int totalMissing = 0;         // count of maximum missing elements
        boolean row = false;    // If its row or col

        for (int i = 0; i < this.puzzleSize; i++) {
            missingRow[i] = missingInRowCount(i);
            missingCol[i] = missingInColCount(i);

            if (missingCol[i] == this.puzzleSize) missingCol[i] = 0;
            totalMissing += missingCol[i];

            System.out.println(missingCol[i] + " " + missingRow[i] + " " + minColIndex + " " + minRowIndex);

            if (min >= missingCol[i] && minColIndex > i) {
                min = missingCol[i];
                minColIndex = i;
                row = false;
            }
            if (min >= missingRow[i] && minRowIndex > i) {
                min = missingRow[i];
                minRowIndex = i;
                row = true;
            }
        }

//        // Find the missing cell and probable value
//        System.out.println("Row " + minRowIndex + " : " + min);
//        System.out.println("Col " + minColIndex + " : " + min);

        if (totalMissing == 0) return;
        if (min > 1) {
            System.out.println(min);
            return;} // if the minimum number of missing items in a row or col is more than 1 it can't solve

        int sum = 0;
        int j = -1;
        for (int i = 0; i < this.puzzleSize; i++) {
            if (row) {
                if (this.cells[minRowIndex][i] >= 0) {
                    sum += this.cells[minRowIndex][i];
                } else {
                    j = i;
                }
            }
            if (!row) {
                if (this.cells[i][minColIndex] >= 0) {
                    sum += this.cells[i][minColIndex];
                } else {
                    j = i;
                }
            }
        }

        // Assign the probable value to the cell
        if (row) {
            int value = sudokuSum - sum;
            this.cells[minRowIndex][j] = value;
            System.out.println("Updated " + minColIndex + " " + j + " " + value);
        }
        if (!row) {
            int value = sudokuSum - sum;
            this.cells[j][minColIndex] = value;
            System.out.println("Updated " + j + " " + minColIndex + value);
        }

        this.solve();
    }

    public int missingInRowCount(int r) {
        // No of missing item in given row
        int count = 0;
        for (int i = 0; i < this.puzzleSize; i++) {
            if (this.cells[r][i] < 0) count++;
        }
        if (count == 0) {
            return this.puzzleSize;
        } else return count;
    }

    public int missingInColCount(int c) {
        // No of missing item in given col
        int count = 0;
        for (int i = 0; i < this.puzzleSize; i++) {
            if (this.cells[i][c] < 0) count++;
        }
        if (count == 0) {
            return this.puzzleSize;
        } else return count;
    }

    protected boolean buildSoduko() {
        // returns true if the sudoku is valid else false

        String[] maps = this.charset.split(" ");

        // Insert blank char into the hashmap
        this.char_to_int.put("-", -2);
        this.int_to_char.put(-2, "-");

        for (int i = 0; i < this.puzzleSize; i++) {
            this.int_to_char.put(i, maps[i]);
            this.char_to_int.put(maps[i], i);
            this.sudokuSum += i;
        }

        // Populate members of the cells
        String[] puzzleElements = this.puzzle.split(System.lineSeparator());
        if (puzzleElements.length != this.puzzleSize) {
            System.out.println("Invalid dimension of puzzle.");
            return false;
        }
        for (int i = 0; i < this.puzzleSize; i++) {
            String[] items = puzzleElements[i].split(" ");
            if (items.length != this.puzzleSize) {
                System.out.println("Invalid dimension of puzzle.");
                return false;
            }
            for (int j = 0; j < this.puzzleSize; j++) {
                if (char_to_int.containsKey(items[j]))
                    cells[i][j] = char_to_int.get(items[j]);
                else {
                    System.out.println("Contains invalid characters");
                    return false;
                }
            }
        }

        return true;
    }

    public void sanityCheck() {
        // Check if the puzzle you're going to solve is correct
        // Check if the puzzle that you solved is correct
        HashMap<String, Integer> cellCounter = new HashMap<String, Integer>();
        boolean sane = true;

        int item = -2;
        for (int i = 0; i < this.puzzleSize; i++) {
            ArrayList<Integer> colCharset = new ArrayList<Integer>();
            ArrayList<Integer> rowCharset = new ArrayList<Integer>();

            for (int j = 0; j < this.puzzleSize; j++) {
                item = cells[i][j]; // row
                if (rowCharset.contains(item) && item != -2) {
                    {
                        System.out.println("Contains repeated item in row : " + i);
                        sane = false;
                    }
                } else {
                    rowCharset.add(item);
                }

                item = cells[j][i]; // col
                if (colCharset.contains(item) && item != -2) {
                    {
                        System.out.println("Contains repeated item in column : " + j);
                        sane = false;
                    }
                } else {
                    colCharset.add(item);
                }
            }
        }
        if (sane)
            System.out.println("Valid Sudoku");
    }

    public String toString() {
        // Convert the solved sudoku to string to display on console or write to file
        String stringPuzzle = "";
        for (int i = 0; i < this.puzzleSize; i++) {
            for (int j = 0; j < this.puzzleSize; j++) {
                stringPuzzle = stringPuzzle.concat(int_to_char.get(this.cells[i][j]) + " ");
//                stringPuzzle = stringPuzzle.concat(String.valueOf(cells[i][j]) + " ");
            }
            stringPuzzle = stringPuzzle.concat(System.lineSeparator());
        }
        return stringPuzzle;
    }
}
