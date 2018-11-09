import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class GuessACell extends MinimumPossibilityCell {

    private int previouslyMissing = 10000000;
    private int currentlyMissing = 10000000;
    private int[][] snapshotSudoku;
    private boolean firstGuess = true;

    // We need a snapshot of the sudoku to reverse our guessing decision
    // Guess index

    // Guess framework and data containers
    private ArrayList<Integer> guesses;
    private int samePosGuessCount = 0;
    private int guessRow = -1;
    private int guessCol = -1;
    private int guessCount = 0;

    private HashSet<String> guessPositions = new HashSet<String>();
    private HashMap<String, int[][]> snapshots = new HashMap<String, int[][]>();

    public GuessACell(int sudokuSize, String map, String sudoku) {
        super(sudokuSize, map, sudoku);
    }

    public void restoreToSaneState() {
        System.out.println("Restoring to a sane state");
        this.cells = this.snapshotSudoku;
        System.out.println(this.toString());
    }

    public void guessPositions(){
        int minValidCount = 1000;
        for (int i = 0; i < this.sudokuSize; i++) {
            for (int j = 0; j < this.sudokuSize; j++) {
                if (this.cells[i][j] == this.BLANK) {
                    int quadRow = Math.floorDiv(i, this.blockSize);
                    int quadCol = Math.floorDiv(j, this.blockSize);

                    int missingValueSize = this.missingInBlockCount(quadRow, quadCol);
                    int[] expectedValues = this.missingInBlock(quadRow, quadCol, missingValueSize);

                    int validValueCount = 0;
                    boolean isValid;
                    for (int value : expectedValues) {
                        isValid = this.validForCell(i, j, value);
                        if (isValid) {
                            validValueCount += 1;
                        }
                    }

                    // If this cell has the lowest valid options
                    if (minValidCount > validValueCount) {
                        minValidCount = validValueCount;
                        guessRow = i;
                        guessCol = j;
                    }
                }
            }
        }
    }

    public void guessACell() {
        // Find the cell with the minimum expected values in the sudoku
        // Guess it.
        // Keep track of it
        // Solve using minimal possibility again
        if (this.guessCount==1) {
            this.snapshotSudoku = this.cells.clone();
            System.out.println("Snapshot saved");
            System.out.print(this.toString());
        }
        guessCount++;

        System.out.println("Your guess count : " + guessCount);
        // And relish control back to solve()

        this.guessPositions();

        int quadRow = Math.floorDiv(guessRow, this.blockSize);
        int quadCol = Math.floorDiv(guessCol, this.blockSize);

        int missingValueSize = this.missingInBlockCount(quadRow, quadCol);
        int[] expectedValues = this.missingInBlock(quadRow, quadCol, missingValueSize);

        Random rand = new Random();
        // nextInt as provided by Random is exclusive of the top value so you need to add 1
        // int randomNum = rand.nextInt((max - min) + 1) + min;

        int random = -1;
        HashSet<Integer> alreadyGuess = new HashSet<Integer>();
        while (true) {
            random = rand.nextInt(missingValueSize);
            // If this is not the first time you're guessing this value, guess another number. I know it is wrong guess.
            if (alreadyGuess.size() == expectedValues.length) break; // You guessed enough, give up
            if (alreadyGuess.contains(random)) continue;

            alreadyGuess.add(random);
            System.out.println(expectedValues[random]);
            if (this.validForCell(guessRow, guessCol, expectedValues[random])) break;
        }
        this.cells[guessRow][guessCol] = expectedValues[random];
        System.out.println("Guess : " + guessRow + "," + guessCol + " : " + expectedValues[random]);
        String coordinate = String.valueOf(guessRow) + "#" + String.valueOf(guessCol);

        guessPositions.add(coordinate);
        snapshots.put(coordinate, this.cells);
        //        System.out.println(this.toString());
    }

    public void solve() {
        this.minimalOption();
        int missingCells = this.totalMissingCells();
        boolean sane = this.sanityCheck();
        if (missingCells == 0 && sane) return;
        else {
            if (!sane) this.restoreToSaneState();
            if (guessCount < 10) {
                guessACell();
                solve();
            }
        }
    }

    public void minimalOption() {
        // Loop through the cells
        // Find the possible values for missing cells
        // If it's one - just fill it
        // for each cell determine its quad to determine expected quad values

        // If there is no change between two iterations, return
        this.currentlyMissing = this.totalMissingCells();
        System.out.println("Empty Cells: " + this.currentlyMissing);
        if (this.currentlyMissing == this.previouslyMissing) {
            return;
        }
        this.previouslyMissing = this.currentlyMissing;

        // Find the part of the sudoku with minimum missing pieces
        // Find expected value for each cell
        for (int i = 0; i < this.sudokuSize; i++) {
            for (int j = 0; j < this.sudokuSize; j++) {
                if (this.cells[i][j] == this.BLANK) expectedValueForCell(i, j);
            }
        }
        minimalOption();
    }

    public boolean validForCell(int row, int col, int value) {
        // Check row and col to determine if a value is valid for a particular cell
        boolean valid = true;
        for (int i = 0; i < sudokuSize; i++) {
            if (value == this.cells[row][i]) valid = false;
            if (value == this.cells[i][col]) valid = false;
        }
        return valid;
    }

}
