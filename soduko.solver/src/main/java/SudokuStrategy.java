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
        GuessACell sudoku = new GuessACell(this.sudokuSize, this.map, this.sudoku);
        OnlyChoice onlyChoice = new OnlyChoice(this.sudokuSize, this.map, this.sudoku);
        MinimumPossibilityCell minimumPossibilityCell = new MinimumPossibilityCell(this.sudokuSize, this.map, this.sudoku);

        onlyChoice.solve();
        minimumPossibilityCell.solve();

        sudoku.solveSudoku();
        System.out.println(sudoku.toString());
        String moreSummary = "Number of guesses tried : " + sudoku.getGuessCount();
        return "";
    }
}