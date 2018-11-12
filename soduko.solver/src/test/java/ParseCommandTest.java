import org.junit.Test;

import static org.junit.Assert.*;

public class ParseCommandTest {
    @Test
    public void testConstructor() throws Exception{
        ParseCommand cmd1 = new ParseCommand("-h");
        ParseCommand cmd2 = new ParseCommand("Puzzle-4x4-0001.txt");
        ParseCommand cmd3 = new ParseCommand("Puzzle-4x4-0001.txt Puzzle-4x4-0002.txt");
    }
}