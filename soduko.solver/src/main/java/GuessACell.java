import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class GuessACell extends MinimumPossibilityCell {

    // We need a snapshot of the sudoku to reverse our guessing decision
    // Guess framework and data containers
    private int guessRow = -1;
    private int guessCol = -1;
    private int firstGuessRow = -1;
    private int firstGuessCol = -1;
    private int guessCount = 0;
    private int[][] snapshot;
    private HashSet<String> guessPositions = new HashSet<String>();
    private HashMap<String, int[][]> snapshots = new HashMap<String, int[][]>();

    public GuessACell(int sudokuSize, String map, String sudoku) {
        super(sudokuSize, map, sudoku);
    }

    public void solve() {
        this.minimalOption(); // fill all the positions that can be deterministically filled
        int missingCells = this.getTotalMissingCells();
        boolean sane = this.sanityCheck();
        if (missingCells == 0 && sane) return;
        else {
            if (!sane) this.restoreToSaneState();
            if (guessCount < 100) {
                guessACell();
                solve();
            } else {
                System.out.println("Stopping without solving");
            }
        }
    }

    public void restoreToSaneState() {
        String coordinate = String.valueOf(firstGuessRow) + "#" + String.valueOf(firstGuessCol);
        // This is the right way of copying array values in java. This copies value not reference
        for (int i = 0; i < this.sudokuSize; i++) {
            this.cells[i] = this.snapshot[i].clone();
        }

        System.out.println("After Restoring to a sane state " + coordinate);
        System.out.println(this.toString());
    }

    public void guessPositions() {
        // Find positions with minimum valid values so that guessing is easy
        int minValidCount = 1000;
        for (int i = 0; i < this.sudokuSize; i++) {
            for (int j = 0; j < this.sudokuSize; j++) {
                if (this.cells[i][j] == this.BLANK) {
                    int quadRow = Math.floorDiv(i, this.blockSize);
                    int quadCol = Math.floorDiv(j, this.blockSize);

                    int missingValueSize = this.getMissingInBlockCount(quadRow, quadCol);
                    int[] expectedValues = this.missingInBlock(quadRow, quadCol, missingValueSize);

                    int validValueCount = 0;
                    boolean isValid;
                    for (int value : expectedValues) {
                        isValid = this.isValidForCell(i, j, value);
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
        guessCount++;

        this.guessPositions(); // Get the cell positions
        if (guessCount == 1) {
            this.firstGuessCol = guessCol;
            this.firstGuessRow = guessRow;
        }

        int quadRow = Math.floorDiv(guessRow, this.blockSize);
        int quadCol = Math.floorDiv(guessCol, this.blockSize);

        int missingValueSize = this.getMissingInBlockCount(quadRow, quadCol);
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
            if (this.isValidForCell(guessRow, guessCol, expectedValues[random])) break;
        }

        this.cells[guessRow][guessCol] = expectedValues[random];
        boolean state = this.sanityCheck();
        if (!state) {
            int[] conflictPos = this.getConflictingCell();
            System.out.println(guessRow + "," + guessCol + "\t" + conflictPos[0] + "," + conflictPos[1] + " : " + expectedValues[random]);
            this.restoreToSaneState();  // restore to a sane state
            this.guessACell();  // and try guessing again
        }
        this.cells[guessRow][guessCol] = this.BLANK;  // undo the state for adding snapshot

        // Before you change anything just make a snapshot
        String coordinate = String.valueOf(guessRow) + "#" + String.valueOf(guessCol);
        if (!guessPositions.contains(coordinate)) {
            guessPositions.add(coordinate);
//            snapshots.put(coordinate, this.cells.clone());

            if (guessCount == 1) {
                snapshot = new int[this.sudokuSize][this.sudokuSize];
                // This is the right way of copying array values in java. This copies value not reference
                for (int i = 0; i < this.sudokuSize; i++) {
                    this.snapshot[i] = this.cells[i].clone();
                }
            }

            System.out.println("Adding snapshot for position " + coordinate);
            System.out.println(this.toString());
        }

        // Update the cell with guessed values
        this.cells[guessRow][guessCol] = expectedValues[random];
        System.out.println("Guess : " + guessRow + "," + guessCol + " : " + expectedValues[random]);
    }

}
