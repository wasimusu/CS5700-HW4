import java.util.ArrayList;
import java.util.HashMap;

public abstract class Sudoku {
    protected int[][] cells;
    private HashMap<String, Integer> char_to_int = new HashMap<String, Integer>();
    private HashMap<Integer, String> int_to_char = new HashMap<Integer, String>();
    private String puzzle;
    private String charset;
    protected int puzzleSize;

    protected int size; // square root of puzzle size

    protected HashMap<Integer, Integer> minimumCells = new HashMap<Integer, Integer>();

    public void solve(String map, String puzzle) {
    }

    public Sudoku(int puzzleSize, String map, String puzzle) {
        cells = new int[puzzleSize][puzzleSize];
        this.charset = map;
        this.puzzle = puzzle;
        this.puzzleSize = puzzleSize;

        this.minimumCells.put(4, 4);
        this.minimumCells.put(9, 17);
        this.minimumCells.put(16, 16);
        this.minimumCells.put(25, 25);

        this.size = (int) Math.sqrt((double) puzzleSize);
    }

    protected boolean buildSoduko() {
        // returns true if the sudoku is valid else false
        int missingItemCount = puzzle.length() - puzzle.replace("-", "").length();
        System.out.println(this.puzzleSize);
        System.out.println(this.minimumCells.get(this.puzzleSize));
        if (missingItemCount < this.minimumCells.get(this.puzzleSize)) {
            System.out.println("Less number of missing elements than permitted.");
            return false;
        }
        String[] maps = this.charset.split(" ");

        // Insert blank char into the hashmap
        this.char_to_int.put("-", -2);
        this.int_to_char.put(-2, "-");

        for (int i = 1; i <= this.puzzleSize; i++) {
            this.int_to_char.put(i, maps[i - 1]);
            this.char_to_int.put(maps[i - 1], i);
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
