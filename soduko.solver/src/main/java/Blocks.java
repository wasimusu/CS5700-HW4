public class Blocks extends Sudoku {

    private int currentlyMissing = 100000;
    private int previouslyMissing = 1000000;

    public Blocks(int sudokuSize, String map, String sudoku) {
        super(sudokuSize, map, sudoku);
    }

    public void solve() {
        // solve a quard for 4*4 or nonet for 9*9
        // if we have a missing value for a cell
        // we find what is missing
        // we find valid values for that column
        // we find valid values for that row

        int minMissing = 1000;
        int row = 1000;
        int col = 1000;
        int missing;
        int totalMissing = this.getTotalMissingCells();
        if (totalMissing == 0) return;

        // Find the block of the sudoku with minimum missing pieces
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                missing = getMissingInBlockCount(i, j);
                if (minMissing > missing) {
                    minMissing = missing;
                    row = i;
                    col = j;
                }
            }
        }

        System.out.println("Minimum missing quad : " + row + " , " + col + " : " + minMissing + " values");
        for (int i = row * blockSize; i < row * blockSize + blockSize; i++) {
            for (int j = col * blockSize; j < col * blockSize + blockSize; j++) {
                if (this.cells[i][j] < 0) {
                    int[] expectedValues = this.missingInBlock(row, col, 0);
                    int validCount = 0;
                    int validValue = -1;
                    for (int value : expectedValues) {
                        if (this.isValidForCell(i, j, value)) {
                            validCount++;
                            validValue = value;
                        }
                    }
                    // If only single value was found to be valid, this is value that we seek
                    if (validCount == 1)
                        this.cells[i][j] = validValue;
                }
            }
        }

        // Quit if we are not making any progess
        if (currentlyMissing == previouslyMissing) return;
        // Update stats
        previouslyMissing = currentlyMissing;
        currentlyMissing = this.getTotalMissingCells();

        this.solve();
    }
}
