public class FileReadCommand implements Command {
    FileRead fileRead;

    public FileReadCommand(String filename) throws Exception {
        fileRead = new FileRead(filename);
    }

    public Sudoku execute() throws Exception {
        return fileRead.readSudoku();
    }
}
