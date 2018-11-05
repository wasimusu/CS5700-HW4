import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class CommandTest {

    @Test
    public void addingCommands() throws Exception {
        HelpCommand helpCommand = new HelpCommand();
        System.out.println(helpCommand);
        Invoker invoker = new Invoker();
        invoker.invoke(helpCommand);

        String path = "src/main/resources/SamplePuzzles/Input";
//        String file = "Puzzle-4x4-0001.txt";  // valid
        String file = "Puzzle-4x4-0101.txt";  // valid - works

        String filename = Paths.get(path, file).toString();
        String outputFilename = Paths.get(path, "Output"+file).toString();
        FileReadCommand fileReadCommand = new FileReadCommand(filename);
        FileReadWriteCommand fileReadWriteCommand = new FileReadWriteCommand(filename, outputFilename);
//        invoker.invoke(fileReadCommand);
        invoker.invoke(fileReadWriteCommand);
    }

}