import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileReadCommand implements Command {
    private String inputFilename;
    private String outputFilename;

    public FileReadCommand(String filename){
        this.inputFilename = filename;
    }

    public void execute() {
    }

    public void readPuzzle() throws Exception {
        File file = new File(this.inputFilename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        String puzzle = "";
        int sizePuzzle = Integer.valueOf(reader.readLine());
        String charMap = reader.readLine();
        while ((line = reader.readLine()) != null)
            puzzle = puzzle.concat(line+System.lineSeparator());

        FirstStrategySoduku sodukuSolver = new FirstStrategySoduku(sizePuzzle, charMap, puzzle);
        sodukuSolver.buildSoduko();
        sodukuSolver.sanityCheck();
    }

    public static void main(String[] args) throws Exception {
        String filename = "src/main/resources/SamplePuzzles/Input/Puzzle-4x4-0001.txt";
//        String filename = "Puzzle-4x4-0001.txt";
        FileReadCommand fileReadCommand = new FileReadCommand(filename);
        fileReadCommand.readPuzzle();
    }

}
