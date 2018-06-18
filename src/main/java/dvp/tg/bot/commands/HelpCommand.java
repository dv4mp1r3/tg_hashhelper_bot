package dvp.tg.bot.commands;

public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super();
    }

    @Override
    public String execute() {
        String helpStr = "Пользуй меня следующим образом:\n"
                +"hashtype hash - определение алгоритма хеширования по значению\n"
                ;
        return helpStr;
    }
}
