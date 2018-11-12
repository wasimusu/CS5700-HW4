public class HelpCommand implements Command {
    // What do I feed to the Help command? Nothing.

    public HelpCommand(){

    }

    public GuessACell execute() {
        System.out.println("<input file name> : reads puzzle from the specified input file \nand writes the output to the console (or displays it in a GUI) ");
        System.out.println("\n<input file name> <output file name> : reads puzzle from the \nspecified input file and writes the output the specify output file");
        return new GuessACell(4, "", "");
    }
}
