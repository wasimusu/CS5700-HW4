public class MinimumPossibilityCell extends Sudoku {

    private int previouslyMissing = 10000000;
    private int currentlyMissing = 100000000;
    // We need a snapshot of the sudoku to reverse our guessing decision
    // Guess index

    public MinimumPossibilityCell(int sudokuSize, String map, String sudoku) {
        super(sudokuSize, map, sudoku);
    }

    public void solve() {
        // Loop through the cells
        // Find the possible values for missing cells
        // If it's one - just fill it
        // for each cell determine its quad to determine expected quad values

        // If there is no change between two iterations, return
        this.currentlyMissing = this.totalMissingCells();
        System.out.println("Empty Cells: " + this.currentlyMissing);
        if (this.currentlyMissing == this.previouslyMissing) return;
        this.previouslyMissing = this.currentlyMissing;

        // Find the part of the sudoku with minimum missing pieces
        // Find expected value for each cell
        for (int i = 0; i < this.sudokuSize; i++) {
            for (int j = 0; j < this.sudokuSize; j++) {
                if (this.cells[i][j] == this.BLANK) expectedValueForCell(i, j);
            }
        }
        System.out.println(this.toString());
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
