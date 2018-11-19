import org.junit.Test;

import static org.junit.Assert.*;

public class FileWriteTest extends BlocksTest{

    @Test
    // Tested by visual inspection
    public void writePuzzle() throws Exception{
        String text = "I am glad you are here";
        FileWrite fileWrite = new FileWrite("testfile.txt", text);
        fileWrite.execute();
    }
}