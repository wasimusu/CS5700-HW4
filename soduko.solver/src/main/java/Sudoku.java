import java.util.ArrayList;
import java.util.HashMap;

public class Sudoku {
    protected int[][] cells;
    private HashMap<String, Integer> char_to_int = new HashMap<String, Integer>();
    private HashMap<Integer, String> int_to_char = new HashMap<Integer, String>();
    private String sudoku;
    private String charset;
    protected int sudokuSize;
    protected int size; // square root of sudoku size

    protected HashMap<Integer, Integer> minimumCells = new HashMap<Integer, Integer>();

    public void solve(String map, String sudoku) {
    }

    public Sudoku(int sudokuSize, String map, String sudoku) {
        cells = new int[sudokuSize][sudokuSize];
        this.charset = map;
        this.sudoku = sudoku;
        this.sudokuSize = sudokuSize;

        this.minimumCells.put(4, 4);
        this.minimumCells.put(9, 17);
        this.minimumCells.put(16, 16);
        this.minimumCells.put(25, 25);

        this.size = (int) Math.sqrt((double) sudokuSize);
    }

    protected boolean buildSoduko() {
        // returns true if the sudoku is valid else false
        int missingItemCount = sudoku.length() - sudoku.replace("-", "").length();
        System.out.println(this.sudokuSize);
        System.out.println(this.minimumCells.get(this.sudokuSize));
        if (missingItemCount < this.minimumCells.get(this.sudokuSize)) {
            System.out.println("Less number of missing elements than permitted.");
            return false;
        }
        String[] maps = this.charset.split(" ");

        // Insert blank char into the hashmap
        this.char_to_int.put("-", -2);
        this.int_to_char.put(-2, "-");

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

        return true;
    }

    public boolean sanityCheck() {
        // Check if the sudoku you're going to solve is correct
        // Check if the sudoku that you solved is correct
        HashMap<String, Integer> cellCounter = new HashMap<String, Integer>();
        boolean sane = true;

        int item = -2;
        for (int i = 0; i < this.sudokuSize; i++) {
            ArrayList<Integer> colCharset = new ArrayList<Integer>();
            ArrayList<Integer> rowCharset = new ArrayList<Integer>();

            for (int j = 0; j < this.sudokuSize; j++) {
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

        return sane;
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
}
