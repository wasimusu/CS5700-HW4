import org.junit.Test;

import java.nio.file.Paths;

public class InvokerTest {

    @Test
    public void invoke() throws Exception {
        String inputPath = "src/main/resources/SamplePuzzles/Input";

        String[] files = {
                "Puzzle-4x4-0201.txt", // twins is not solved by blocks
                "Puzzle-4x4-0904.txt", // twins - any guess is correct guess
                "Puzzle-16x16-0902.txt", // valid - solved
                "Puzzle-16x16-0901.txt",// valid - too many correct guess required
                "Puzzle-9x9-0101.txt", // works most of the times -- only one correct guess is required
                "Puzzle-9x9-0401.txt", // works most of the times -- only one correct guess is required
        };

        Invoker invoker = new Invoker();
        for (String file : files) {
            String filename = Paths.get(inputPath, file).toString();
            FileReadCommand fileReadCommand = new FileReadCommand(filename);
            invoker.invoke(fileReadCommand);
        }

    }
}
