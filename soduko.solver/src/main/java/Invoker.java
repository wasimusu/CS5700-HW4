import java.util.ArrayList;

public class Invoker {
    private ArrayList<Command> historyOfCommands;

    public void invoke(Command command) {
        // Add to the list of commands that are to be executed
        historyOfCommands.add(command);
        command.execute();
    }
}
