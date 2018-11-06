import java.util.ArrayList;
import java.util.Random;

public class GuessACell extends Sudoku {

    private int previouslyMissing = 10000000;
    private int currentlyMissing = 100000000;
    private int[][] snapshotSudoku;
    private boolean firstGuess = true;
    // We need a snapshot of the sudoku to reverse our guessing decision
    // Guess index

    // Guess framework and data containers
    private ArrayList<Integer> guesses;
    private int samePosGuessCount = 0;
    private int guessRow = -1;
    private int guessCol = -1;

    public GuessACell(int sudokuSize, String map, String sudoku) {
        super(sudokuSize, map, sudoku);
    }

    public void restoreToSaneState() {
        System.out.println("Restoring to a sane state");
        this.cells = this.snapshotSudoku;
    }

    public void guessACell() {
        // Find the cell with the minimum expected values in the sudoku
        // Guess it.
        // Keep track of it
        // Solve using minimal possibility again
        if (this.firstGuess) this.snapshotSudoku = this.cells;
        firstGuess = false;

        // And relish control back to solve()
        int minValidCount = 1000;
        for (int i = 0; i < this.sudokuSize; i++) {
            for (int j = 0; j < this.sudokuSize; j++) {
                if (this.cells[i][j] == this.BLANK) {
                    int quadRow = Math.floorDiv(i, this.size);
                    int quadCol = Math.floorDiv(j, this.size);

                    int missingValueSize = this.missingInPartCount(quadRow, quadCol);
                    int[] expectedValues = this.missingInPart(quadRow, quadCol, missingValueSize);

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
        int quadRow = Math.floorDiv(guessRow, this.size);
        int quadCol = Math.floorDiv(guessCol, this.size);

        int missingValueSize = this.missingInPartCount(quadRow, quadCol);
        int[] expectedValues = this.missingInPart(quadRow, quadCol, missingValueSize);

        Random rand = new Random();
        // nextInt as provided by Random is exclusive of the top value so you need to add 1
        // int randomNum = rand.nextInt((max - min) + 1) + min;
        int random = rand.nextInt(missingValueSize);
        this.cells[guessRow][guessCol] = expectedValues[random];
        System.out.println("Guess : " + guessRow + "," + guessCol + " : " + expectedValues[random]);

    }

    public void solve() {
        this.minimalOption();
        int missingCells = this.totalMissingCells();
        boolean sane = this.sanityCheck();
        if (missingCells == 0 && sane == true) return;
        else {
            if (!sane) this.restoreToSaneState();
            guessACell();
            solve();
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

    public void expectedValueForCell(int row, int col) {
        // determine the quad from the row, col
        // find the missing values for that quad
        // check which of the values are valid for each missing cell in that quad

        int quadRow = Math.floorDiv(row, this.size);
        int quadCol = Math.floorDiv(col, this.size);

        int missingValueSize = this.missingInPartCount(quadRow, quadCol);
        int[] expectedValues = this.missingInPart(quadRow, quadCol, missingValueSize);

        int validValueCount = 0;
        int validValue = -2;
        boolean isValid;
        for (int value : expectedValues) {
            isValid = this.validForCell(row, col, value);
            if (isValid) {
                validValueCount += 1;
                validValue = value;
            }
        }
        if (validValueCount == 1) {
            this.cells[row][col] = validValue;
//            System.out.println(row + ", " + col + " : " + validValueCount + " - " + validValue);
        }
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
