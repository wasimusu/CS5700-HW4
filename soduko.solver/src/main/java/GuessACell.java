import java.util.ArrayList;
import java.util.HashMap;

public class GuessACell extends MinimumPossibilityCell {

    // We need a snapshot of the sudoku to reverse our guessing decision
    // Guess framework and data containers
    private int guessRow = -1;
    private int guessCol = -1;


    private int guessCount = 0;
    private ArrayList<String> guessPositions = new ArrayList<>(); // positions for which we have guesses
    private HashMap<String, int[][]> snapshots = new HashMap<String, int[][]>(); // coordinate and their snapshot
    private HashMap<String, Integer> coordinates = new HashMap<>();  // coordinate and their index for index lookup
    private HashMap<String, Integer> guessIndex = new HashMap<>(); // coorindate and their guess index

    public int getGuessCount() {
        return guessCount;
    }

    public GuessACell(int sudokuSize, String map, String sudoku) {
        super(sudokuSize, map, sudoku);
    }

    @Override
    public void solve() {
        this.minimalOption(); // fill all the positions that can be deterministically filled
        int missingCells = this.getTotalMissingCells();
        boolean sane = this.sanityCheck();
        if (missingCells == 0 && sane) return;
        else {
            if (!sane) this.restoreToSaneState();
            if (guessCount < 99) {
                guessACell();
                solve();
            } else {
                System.out.println("Stopping without solving");
            }
        }
//        System.out.println("No of guesses " + this.guessCount + "\tItem Missing : " + this.getTotalMissingCells());
    }

    public void restoreToSaneState() {
//        String co = coordinates.
        String coordinate = String.valueOf(guessRow) + "#" + String.valueOf(guessCol);
        // This is the right way of copying array values in java. This copies value not reference
        for (int i = 0; i < this.sudokuSize; i++) {
            this.cells[i] = this.snapshots.get(coordinate)[i].clone();
        }
//        System.out.println("After Restoring to a sane state " + coordinate);
//        System.out.println(this.toString());
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
        int quadRow = Math.floorDiv(guessRow, this.blockSize);
        int quadCol = Math.floorDiv(guessCol, this.blockSize);
        String coordinate = String.valueOf(guessRow) + "#" + String.valueOf(guessCol);

        // Compute the expected values for this cell
        int missingValueSize = this.getMissingInBlockCount(quadRow, quadCol);
        int[] expectedValues = this.missingInBlock(quadRow, quadCol, missingValueSize);

        int prevIndex = -1;
        if (guessIndex.containsKey(coordinate)) prevIndex = guessIndex.get(coordinate);
        int nextIndex = prevIndex;
//        System.out.println("Started with index " + prevIndex + " / " + expectedValues.length + "\tfor " + coordinate);
        for (int i = prevIndex + 1; i < expectedValues.length; i++) {
            if (this.isValidForCell(guessRow, guessCol, expectedValues[i])) {
                nextIndex = i;
                guessIndex.put(coordinate, i);
//                System.out.println("Valid index " + nextIndex + "/" + expectedValues.length + " for " + coordinate + " : " + expectedValues[nextIndex]);
                break;
            }
        }

        // did not find any worthy guess. we want to restore the state to the one level up guess
        if (nextIndex == prevIndex) {
//            System.out.println("Did not find any valid new values for " + coordinate);
            guessIndex.remove(coordinate); // stores coordinate and index
            coordinates.remove(coordinate); // stores coordinate and index
            guessPositions.remove(coordinate); // stores coordinate only
            snapshots.remove(coordinate); // remove the snapshot

            int oldPosIndex = guessPositions.size() - 1;
            if (oldPosIndex == -1) return;  // If we are out of snapshots

            String[] oldPositions = guessPositions.get(oldPosIndex).split("#");
            guessRow = Integer.valueOf(oldPositions[0]);
            guessCol = Integer.valueOf(oldPositions[1]);
            this.restoreToSaneState();
            return;
        }

        boolean state = this.sanityCheck();
        if (!state) {
            int[] conflictPos = this.getConflictingCell();
//            System.out.println(guessRow + "," + guessCol + "\t" + conflictPos[0] + "," + conflictPos[1] + " : " + expectedValues[random]);
            this.restoreToSaneState();  // restore to a sane state
            this.guessACell();  // and try guessing again
            this.cells[guessRow][guessCol] = this.BLANK;  // undo the state for adding snapshot
        }

        // Before you change anything just make a snapshot
        if (!guessPositions.contains(coordinate)) guessPositions.add(coordinate);
        if (guessCount != 0) {
            int[][] snapshot = new int[this.sudokuSize][this.sudokuSize];
            // This is the right way of copying array values in java. This copies value not reference
            for (int i = 0; i < this.sudokuSize; i++) {
                snapshot[i] = this.cells[i].clone();
            }
            snapshots.put(coordinate, snapshot.clone());
            coordinates.put(coordinate, coordinates.size() + 1);
//            System.out.println("Size of coordinates : " + coordinates.size());
        }

        // Update the cell with guessed values
        this.cells[guessRow][guessCol] = expectedValues[nextIndex];
//        System.out.println(this.toString());
        // System.out.println("Guess : " + guessRow + "," + guessCol + " : " + expectedValues[nextIndex]);
    }
}
