import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileWrite{
    private String outputFilename;
    private String text;

    public FileWrite(String outputFilename, String text) {
        this.outputFilename = outputFilename;
        this.text = text;
    }

    public void execute() throws Exception{
//        MinimumPossibilityCell sudoku = this.readSudoku();
        this.writePuzzle();
    }

    public void writePuzzle() throws Exception {
        File file = new File(this.outputFilename);
        BufferedWriter writer = new BufferedWriter( new FileWriter(file));
        writer.write(text);

        // dipose the resources handed to writer
        writer.close();
    }
}
