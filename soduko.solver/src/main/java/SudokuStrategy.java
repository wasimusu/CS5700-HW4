public class SudokuStrategy {
    private int sudokuSize;
    private String map;
    private String sudoku;

    public SudokuStrategy(int sudokuSize, String map, String sudoku) {
        this.sudokuSize = sudokuSize;
        this.sudoku = sudoku;
        this.map = map;
    }

    public String solve() {
        // Find the number of missing blanks
        // Use all the strategies
        // Append that to the text file

        // And write to file
        // return summary to be written
        Sudoku sudoku = new Sudoku(this.sudokuSize, this.map, this.sudoku);

        String moreSummary = "";
        int i = 0;
        while (i < 2) {
            int missingCells = sudoku.getTotalMissingCells();
            if (missingCells <= sudoku.getSudokuSize()) {
                OnlyChoice onlyChoice = new OnlyChoice(this.sudokuSize, this.map, this.sudoku);
                sudoku = onlyChoice.solveSudoku();
                System.out.println("Done with only choice\n" + onlyChoice.toString());
                if (sudoku.getTotalMissingCells() == 0) break;
            } else {
                System.out.println("Starting with backtracking");
//                MinimumPossibilityCell minimumPossibilityCell = new MinimumPossibilityCell(this.sudokuSize, this.map, this.sudoku);
                GuessACell guessACell = new GuessACell(this.sudokuSize, this.map, this.sudoku);
                sudoku = guessACell.solveSudoku();
                moreSummary = "Number of guesses tried : " + guessACell.getGuessCount();
                if (sudoku.getTotalMissingCells() == 0) break;

            }
            i++;
        }

//        sudoku.solveSudoku();
//        System.out.println(sudoku.toString());
        return moreSummary;
    }
}