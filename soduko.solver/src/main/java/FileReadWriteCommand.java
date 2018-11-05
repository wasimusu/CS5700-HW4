import java.io.*;

public class FileReadWriteCommand extends FileReadCommand {
    private String outputFilename;

    public FileReadWriteCommand(String inputFilename, String outputFilename) {
        super(inputFilename);
        this.outputFilename = outputFilename;
    }

    public void execute() {
    }

    public void writePuzzle(Sudoku sudoku) throws Exception {
        File file = new File(this.outputFilename);
        BufferedWriter writer = new BufferedWriter( new FileWriter(file));
        writer.write(sudoku.toString());
    }

}
