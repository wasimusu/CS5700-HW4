public class FileReadWriteCommand extends FileReadCommand {
    private String outputFilename;
    private FileWrite fileWrite;

    public FileReadWriteCommand(String inputFilename, String outputFilename) throws Exception {
        super(inputFilename);
        this.outputFilename = outputFilename;
        this.fileRead = new FileRead(inputFilename);
    }

    public Sudoku execute() throws Exception {
        Sudoku sudoku = this.fileRead.readSudoku();
        this.fileWrite = new FileWrite(this.outputFilename, sudoku.getSummary());
        return sudoku;
    }

}
