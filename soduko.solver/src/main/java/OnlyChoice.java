public class OnlyChoice extends Sudoku {
    private int sudokuSum;

    OnlyChoice(int sudokuSize, String map, String sudoku) {
        super(sudokuSize, map, sudoku);
        for (int i = 1; i <= this.sudokuSize; i++) sudokuSum += i;
    }

    public Sudoku solve() {
        int minColIndex = 1000; // Row with minimum missing elements
        int minRowIndex = 1000; // Col with minimum missing elements

        if (this.currentlyMissing == 0)
            return new Sudoku(this.getSudokuSize(), this.getCharset(), this.toString()); // If we do not have missing elements, no worth processing further

        for (int i = 0; i < this.sudokuSize; i++) {
            for (int j = 0; j < this.sudokuSize; j++) {
                if (this.cells[i][j] != this.BLANK) {
                    if ((getMissingInRowCount(i) == 1) && getMissingInColCount(i) == 1) {
                        minColIndex = j;
                        minRowIndex = i;
                        break;
                    }
                }
            }
        }

        if (minColIndex != 1000 && minRowIndex != 1000) {
            int sum = 0;
            for (int j = 0; j < this.sudokuSize; j++) {
                if (this.cells[minRowIndex][j] != this.BLANK) sum += this.cells[minRowIndex][j];
            }
            this.cells[minRowIndex][minColIndex] = this.sudokuSum - sum;
        } else {
            System.out.println("Can not be solved using Only Choice. Giving up because more than one cells are missing in a row or col\n");
            return new Sudoku(this.getSudokuSize(), this.getCharset(), this.toString());
        } // if the minimum number of missing items in a row or col is more than 1 it can't solve

        // Quit if we are not making any progess
        if (currentlyMissing == previouslyMissing)
            return new Sudoku(this.getSudokuSize(), this.getCharset(), this.toString());

        // Update stats
        previouslyMissing = currentlyMissing;
        currentlyMissing = this.getTotalMissingCells();

        return this.solve();
    }
}
