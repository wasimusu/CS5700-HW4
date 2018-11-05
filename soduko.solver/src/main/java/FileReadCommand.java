import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileReadCommand implements Command {
    protected String inputFilename;

    public FileReadCommand(String filename) {
        this.inputFilename = filename;
    }

    public void execute() throws Exception{
        this.readPuzzle();
    }

    public FirstStrategySoduku readPuzzle() throws Exception {
        File file = new File(this.inputFilename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        String puzzle = "";
        int sizePuzzle = Integer.valueOf(reader.readLine());
        String charMap = reader.readLine();
        while ((line = reader.readLine()) != null)
            puzzle = puzzle.concat(line + System.lineSeparator());

        FirstStrategySoduku sudoku = new FirstStrategySoduku(sizePuzzle, charMap, puzzle);
        boolean valid = sudoku.buildSoduko();

        if (valid) {
            sudoku.solvePart();
            sudoku.sanityCheck();
            System.out.println(sudoku.toString());
        }
        // solve and display also
//        return new FirstStrategySoduku(sizePuzzle, charMap, puzzle);
        return sudoku;
    }
}
