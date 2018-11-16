public class SudokuStrategy {
    private int sudokuSize;
    private String map;
    private String sudoku;
    private StringBuilder summary;

    public SudokuStrategy(int sudokuSize, String map, String sudoku) {
        this.sudokuSize = sudokuSize;
        this.sudoku = sudoku;
        this.map = map;
        summary = new StringBuilder();
    }

    public String getSummary() {
        return summary.toString();
    }

    public String solve() {
        // Find the number of missing blanks
        // Use all the strategies
        // Append that to the text file
        Sudoku sudoku = new Sudoku(this.sudokuSize, this.map, this.sudoku);
        sudoku.buildSoduko();

        summary.append("\n\n" + sudoku.getSummary());

        int missingCells = 0;
        while (true) {
            missingCells = sudoku.getTotalMissingCells();
            System.out.println("Missing cells before only choice : " + missingCells);

            OnlyChoice onlyChoice = new OnlyChoice(this.sudokuSize, this.map, this.sudoku);
            sudoku = onlyChoice.solveSudoku();
            missingCells = onlyChoice.getTotalMissingCells();
            System.out.println("Missing cells : " + missingCells);
            summary.append("Strategies \t\t\tTime");
            summary.append("\nOnly choice \t\t: " + onlyChoice.getExecutionTime());
            if (sudoku.getTotalMissingCells() == 0) break;

            Blocks blocks = new Blocks(this.sudokuSize, this.map, this.sudoku);
            sudoku = blocks.solveSudoku();
            missingCells = blocks.getTotalMissingCells();
            summary.append("\nBlocks \t\t\t: " + blocks.getExecutionTime());
            if (sudoku.getTotalMissingCells() == 0) break;

            GuessACell guessACell = new GuessACell(this.sudokuSize, this.map, this.sudoku);
            sudoku = guessACell.solveSudoku();
            missingCells = guessACell.getTotalMissingCells();
            summary.append("\nGuessACell \t\t\t: " + guessACell.getExecutionTime());
            summary.append("\n\n\nNumber of guesses tried : " + guessACell.getGuessCount());
            if (sudoku.getTotalMissingCells() == 0) break;
        }

        sudoku.buildSoduko();
        summary.append("\n\nSolution :\n" + sudoku.toString());
        System.out.println("Summary : \n\n" + summary.toString());

        return summary.toString();
    }
}