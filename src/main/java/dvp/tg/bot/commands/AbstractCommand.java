package dvp.tg.bot.commands;

import org.telegram.telegrambots.api.objects.Message;

import java.util.Arrays;

public abstract class AbstractCommand
{
    protected String command;

    protected String[] args;

    public abstract String execute();

    public AbstractCommand()
    {

    }

    /**
     * Обработка аргументов команды из сообщения
     * @param message
     * @throws Exception
     */
    public void setupByMessage(Message message) throws Exception {
        String text = message.getText();
        String[] args = text.split(" ");
        // разбить на команды и аргументы
        // команда с аргументами
        if (args[0].startsWith("/") && args.length > 1)
        {
            this.command = args[0];
            this.args = Arrays.copyOfRange(args, 1, args.length);
        }
        else
        {
            throw new Exception("Неверный формат строки ("+text+")");
        }
    }

    public static String getMessageCommand(Message message)
    {
        String text = message.getText();
        if (text.startsWith("/") && text.contains(" "))
        {
            return text.substring(1, text.indexOf(" "));
        }

        return null;
    }
}
