import java.io.*;

public class FileReadWriteCommand extends FileReadCommand {
    private String outputFilename;

    public FileReadWriteCommand(String inputFilename, String outputFilename) {
        super(inputFilename);
        this.outputFilename = outputFilename;
    }

    public void execute() throws Exception{
//        MinimumPossibilityCell sudoku = this.readSudoku();
        GuessACell sudoku = this.readSudoku();
        this.writePuzzle(sudoku);
    }

    public void writePuzzle(Sudoku sudoku) throws Exception {
        File file = new File(this.outputFilename);
        BufferedWriter writer = new BufferedWriter( new FileWriter(file));
        writer.write(sudoku.toString());

        // dipose the resources handed to writer
        writer.close();
    }

}
