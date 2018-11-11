public class CommandFactory {

    public void parseCommands(String cmd) throws Exception {
        String[] cmds = cmd.split(" ");
        Invoker invoker = new Invoker();
        if (cmd.equals("-h")) {
            HelpCommand helpCommand = new HelpCommand();
            invoker.invoke(helpCommand);
        }
        if (cmds.length == 2) {
            String inputFilename = cmds[0];
            String outputFilename = cmds[1];
            FileReadWriteCommand fileReadWriteCommand = new FileReadWriteCommand(inputFilename, outputFilename);
            invoker.invoke(fileReadWriteCommand);
        } else {
            String inputFilename = cmd;
            FileReadCommand fileReadCommand = new FileReadCommand(inputFilename);
            invoker.invoke(fileReadCommand);
        }
    }
}

