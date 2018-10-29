import java.util.ArrayList;
import java.util.HashMap;

public class FirstStrategySoduku {
    private int[][] cells;
    private HashMap<String, Integer> char_to_int = new HashMap<String, Integer>();
    private HashMap<Integer, String> int_to_char = new HashMap<Integer, String>();
    private String puzzle;
    private String charset;
    private int puzzleSize;

    public FirstStrategySoduku(int puzzleSize, String map, String puzzle) {
        String[] maps = map.split(" ");
        cells = new int[puzzleSize][puzzleSize];
        this.charset = map;
        this.puzzle = puzzle;
        this.puzzleSize = puzzleSize;
    }

    protected void buildSoduko() {
        String[] maps = this.charset.split(" ");

        // Insert blank char into the hashmap
        this.char_to_int.put("-", -2);
        this.int_to_char.put(-2, "-");

        for (int i = 0; i < this.puzzleSize; i++) {
            this.int_to_char.put(i, maps[i]);
            this.char_to_int.put(maps[i], i);
        }

        // Populate members of the cells
        String[] puzzleElements = this.puzzle.split(System.lineSeparator());
        if (puzzleElements.length != this.puzzleSize) {
            System.out.println("Invalid dimension of puzzle.");
            return;
        }
        for (int i = 0; i < this.puzzleSize; i++) {
            String[] items = puzzleElements[i].split(" ");
            if (items.length != this.puzzleSize) {
                System.out.println("Invalid dimension of puzzle.");
                return;
            }
            for (int j = 0; j < this.puzzleSize; j++) {
                if (char_to_int.containsKey(items[j]))
                    cells[i][j] = char_to_int.get(items[j]);
                else {
                    System.out.println("Contains invalid characters");
                    break;
                }
            }
        }
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

    public void verify() {
        // Verify that the puzzle you solved is correct
    }

}
