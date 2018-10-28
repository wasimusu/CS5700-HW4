import java.util.ArrayList;

public class Invoker {
    private ArrayList<Command> commands;

    public void invoke(Command command) {
        // Add to the list of commands that are to be executed
        commands.add(command);
    }
}
