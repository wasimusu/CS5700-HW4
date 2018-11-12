public class ParseCommand {

    public ParseCommand(String cmd) throws Exception {
        String[] cmds = cmd.split(" ");
        Invoker invoker = new Invoker();
        if (cmd.equals("-h")) {
            HelpCommand helpCommand = new HelpCommand();
            invoker.invoke(helpCommand);
        }
        else if (cmds.length == 2) {
            String inputFilename = cmds[0];
            String outputFilename = cmds[1];
            FileReadWriteCommand fileReadWriteCommand = new FileReadWriteCommand(inputFilename, outputFilename);
            invoker.invoke(fileReadWriteCommand);
        } else {
            FileReadCommand fileReadCommand = new FileReadCommand(cmd);
            invoker.invoke(fileReadCommand);
        }
    }
}