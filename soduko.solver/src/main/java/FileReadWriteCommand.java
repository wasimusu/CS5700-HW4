public class FileReadWriteCommand extends FileReadCommand {
    private String outputFilename;

    FileReadWriteCommand(String inputFilename, String outputFilename) {
        super(inputFilename);
        this.outputFilename = outputFilename;
        this.fileRead = new FileRead(inputFilename);
    }

    public Sudoku execute() throws Exception {
        Sudoku sudoku = this.fileRead.readSudoku();
        SudokuStrategy solveSudoku = new SudokuStrategy(sudoku.getSudokuSize(), sudoku.getCharset(), sudoku.getSudoku());
        solveSudoku.solve();
        FileWrite fileWrite = new FileWrite(this.outputFilename, solveSudoku.getSummary());
        fileWrite.writePuzzle();
        return sudoku;
    }
}
