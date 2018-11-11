import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileReadCommand implements Command {
    protected String inputFilename;

    public FileReadCommand(String filename) {
        this.inputFilename = filename;
    }

    public void execute() throws Exception {
        this.readSudoku();
    }

    public GuessACell readSudoku() throws Exception {
        File file = new File(this.inputFilename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        String puzzle = "";
        int sizePuzzle = Integer.valueOf(reader.readLine());
        String charMap = reader.readLine();
        while ((line = reader.readLine()) != null)
            puzzle = puzzle.concat(line + System.lineSeparator());

        return new GuessACell(sizePuzzle, charMap, puzzle);
    }
}
