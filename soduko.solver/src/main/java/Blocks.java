public class Blocks extends Sudoku {
    private int sudokuSum;
    private int[] missingRow;
    private int[] missingCol;

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

        int min = 1000;
        int row = 1000;
        int col = 1000;
        int a;
        int totalMissing = 0;
        // Find the part of the sudoku with minimum missing pieces
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                a = getMissingInBlockCount(i, j);
                if (min > a) {
                    min = a;
                    row = i;
                    col = j;
                }
                // Count the total number of missing items
                if (a == sudokuSize) a = 0;  // because if missing item is zero a larger number is sent by the function
                totalMissing += a;
            }
        }

//        System.out.println("Total missing : " + totalMissing);
        if (totalMissing == 0) return;

        System.out.println("Minimum missing quad : " + row + " , " + col + " : " + min + " values");
        // These are problematic areas resolve them soon
        int[] expectedValues = this.missingInBlock(row, col, min);

        for (int i = row * blockSize; i < row * blockSize + blockSize; i++) {
            for (int j = col * blockSize; j < col * blockSize + blockSize; j++) {
                if (this.cells[i][j] < 0) {
                    this.validValuesForCell(i, j, expectedValues);
                }
            }
        }

        // Quit if we are not making any progess
        if(currentlyMissing == previouslyMissing) return;
        // Update stats
        previouslyMissing = currentlyMissing;
        currentlyMissing = this.getTotalMissingCells();

        this.solve();
    }

    public void validValuesForCell(int row, int col, int[] expectedValues) {
        // Finds suitable place for expected value looking through row and col
        // Find all the elements of that row and check if one of expected value is in that row
        // Do above for column also
        int filledCount = 0; // how many missing items was filled
        System.out.println("Hey");
        boolean valid = true;
        int validCount = 0;
        int validValue = 0;
        for (int value : expectedValues) {
            for (int i = 0; i < sudokuSize; i++) {
                if (value == this.cells[row][i]) valid = false;
                if (value == this.cells[i][col]) valid = false;
            }
            if (valid) {
                validCount++;
                validValue = value;
            }
            System.out.println("Value : " + value + "\t(" + row + ", " + col + ")" + "\tCount " + validCount);
        }

        // It has to be at the end of for loop
        if (validCount == 1) {
            this.cells[row][col] = validValue;
            System.out.println(this.toString());
        } else {
            System.out.println("Did not apply any value");
        }
    }
}
