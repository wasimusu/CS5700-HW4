public class OnlyChoice extends Sudoku {
    private int sudokuSum;

    public OnlyChoice(int sudokuSize, String map, String sudoku) {
        super(sudokuSize, map, sudoku);
    }

    public void solve() {
        int[] missingCol = new int[this.sudokuSize];
        int[] missingRow = new int[this.sudokuSize];
        int minColIndex = 1000; // Row with minimum missing elements
        int minRowIndex = 1000; // Col with minimum missing elements
        int min = 1000;         // count of minimum missing elements
        int totalMissing = 0;         // count of maximum missing elements
        boolean row = false;    // If its row or col

        for (int i = 0; i < this.sudokuSize; i++) {
            missingRow[i] = getMissingInRowCount(i);
            missingCol[i] = getMissingInColCount(i);

            if (min >= missingCol[i]) {
                min = missingCol[i];
                minColIndex = i;
                row = false;
            }

            if (min >= missingRow[i]) {
                min = missingRow[i];
                minRowIndex = i;
                row = true;
            }

            if (missingCol[i] == this.sudokuSize) missingCol[i] = 0;
            totalMissing += missingCol[i];
        }

        if (totalMissing == 0) return;
        if (min > 1) {
            System.out.println(min);
            return;
        } // if the minimum number of missing items in a row or col is more than 1 it can't solve

        int sum = 0;
        int j = -1;
        for (int i = 0; i < this.sudokuSize; i++) {
            if (row) {
                if (this.cells[minRowIndex][i] >= 0) {
                    sum += this.cells[minRowIndex][i];
                } else {
                    j = i;
                }
            }
            if (!row) {
                if (this.cells[i][minColIndex] >= 0) {
                    sum += this.cells[i][minColIndex];
                } else {
                    j = i;
                }
            }
        }

        // Assign the probable value to the cell
        if (row) {
            int value = this.sudokuSum - sum;
            this.cells[minRowIndex][j] = value;
        }
        if (!row) {
            int value = this.sudokuSum - sum;
            this.cells[j][minColIndex] = value;
        }

        this.solve();
    }
}
