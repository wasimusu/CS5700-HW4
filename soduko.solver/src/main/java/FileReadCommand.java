public class FileReadCommand implements Command {
    FileRead fileRead;

    public FileReadCommand(String filename) {
        fileRead = new FileRead(filename);
    }

    public Sudoku execute() throws Exception {
        return fileRead.readSudoku();
    }

}
