import java.io.*;

public class FileReadWriteCommand extends FileReadCommand {
    private String outputFilename;
    private FileWrite fileWrite;

    public FileReadWriteCommand(String inputFilename, String outputFilename) throws Exception{
        super(inputFilename);
        this.outputFilename = outputFilename;
        this.fileRead = new FileRead(inputFilename);
    }

    public Sudoku execute() throws Exception{
//        MinimumPossibilityCell sudoku = this.readSudoku();
        Sudoku sudoku = this.fileRead.readSudoku();
        this.fileWrite = new FileWrite(this.outputFilename, sudoku.getSummary());
        return sudoku;
    }

//    public void writePuzzle(Sudoku sudoku) throws Exception {
//        File file = new File(this.outputFilename);
//        BufferedWriter writer = new BufferedWriter( new FileWriter(file));
//        writer.write(sudoku.getSummary());
//
//        // dipose the resources handed to writer
//        writer.close();
//    }

}
