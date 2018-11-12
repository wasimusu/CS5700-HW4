public class OnlyChoice extends Sudoku {
    private int sudokuSum;

    public OnlyChoice(int sudokuSize, String map, String sudoku) {
        super(sudokuSize, map, sudoku);
        for(int i = 1; i<=this.sudokuSize; i++) sudokuSum += i;
    }

    public void solve() {
        int[] missingCol = new int[this.sudokuSize];
        int[] missingRow = new int[this.sudokuSize];
        int minColIndex = 1000; // Row with minimum missing elements
        int minRowIndex = 1000; // Col with minimum missing elements
        int min = 1000;         // count of minimum missing elements
        boolean row = false;    // If its row or col

        if (this.currentlyMissing == 0) return; // If we do not have missing elements, no worth processing further

        for (int i = 0; i < this.sudokuSize; i++) {
            System.out.println(i + " : \t" + getMissingInRowCount(i) + ", " + getMissingInColCount(i));
            missingRow[i] = getMissingInRowCount(i);
            missingCol[i] = getMissingInColCount(i);

            // Find the minimum column
            if (min >= missingCol[i]) {
                min = missingCol[i];
                minColIndex = i;
                row = false;
            }

            // Find the minimum row
            if (min >= missingRow[i]) {
                min = missingRow[i];
                minRowIndex = i;
                row = true;
            }
        }

        if (min > 1) {
            System.out.println("Can not be solved using this method. Giving up because " + min + " cells are missing in a row or col\n");
            return;
        } // if the minimum number of missing items in a row or col is more than 1 it can't solve

        int sum = 0;
        int j = -1;
        for (int i = 0; i < this.sudokuSize; i++) {
            if (row) {
                if (this.cells[minRowIndex][i] != this.BLANK) {
                    sum += this.cells[minRowIndex][i];
//                    System.out.println("Row : " + this.cells[minRowIndex][i]);
                } else {
                    j = i;
                }
            }
            if (!row) {
                if (this.cells[i][minColIndex] != this.BLANK) {
                    sum += this.cells[i][minColIndex];
//                    System.out.println("Cols : " + this.cells[i][minColIndex]);
                } else {
                    j = i;
                }
            }
        }

//        System.out.println(" j " + j + " " + this.sudokuSum + " : " + sum);
        // Assign the probable value to the cell
        if (row) {
            int value = this.sudokuSum - sum;
            this.cells[minRowIndex][j] = value;
        }
        if (!row) {
            int value = this.sudokuSum - sum;
            this.cells[j][minColIndex] = value;
        }

        // Quit if we are not making any progess
        if (currentlyMissing == previouslyMissing) return;
        // Update stats
        previouslyMissing = currentlyMissing;
        currentlyMissing = this.getTotalMissingCells();

        this.solve();
    }
}
