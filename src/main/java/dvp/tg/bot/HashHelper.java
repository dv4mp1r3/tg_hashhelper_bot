package dvp.tg.bot;

import dvp.hashdetection.Scanner;
import dvp.tg.bot.commands.AbstractCommand;
import dvp.tg.bot.commands.HashTypeCommand;
import dvp.tg.bot.commands.HelpCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.util.*;


public class HashHelper extends TelegramLongPollingBot {

    protected String apiToken;
    protected String botName;
    protected Long userId;
    protected Long fromChatId;

    protected HashMap<String, AbstractCommand> commands;

    /**
     *
     * @param iniFile инстанс объекта с загруженным конфигом
     * @param scanner инстанс сканера с загруженым конфигом
     */
    public HashHelper(JIniFile iniFile, Scanner scanner) {

        super();
        String sectionMain = "main";
        String sectionMisc = "misc";
        String defaultEmpty = "";
        this.botName = iniFile.ReadString(sectionMain, "name", defaultEmpty);
        this.apiToken = iniFile.ReadString(sectionMain, "token", defaultEmpty);
        this.userId = new Long(iniFile.ReadString(sectionMisc, "userId", defaultEmpty));
        //this.fromChatId = new Long(iniFile.ReadString(sectionMisc, "fromChatId", defaultEmpty));

        this.commands = new HashMap<String, AbstractCommand>();
        this.commands.put("help", new HelpCommand());
        this.commands.put("hashtype", new HashTypeCommand(scanner));
    }


    /**
     *
     * @param update
     */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message msg = update.getMessage();
                if (msg.hasText()) {
                    handleIncomingMessage(msg);
                }
            }

        } catch (Exception e) {
            try {
                this.sendResponse("onUpdateReceived exception: " + e.getMessage(), this.userId);
            } catch (TelegramApiException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Обработка входящего соединения
     * @param message
     * @throws TelegramApiException
     */
    private void handleIncomingMessage(Message message) throws TelegramApiException {
        Long chatId = message.getChatId();
        String command = AbstractCommand.getMessageCommand(message);
        if (command != null)
        {
            try {
                this.commands.get(command).setupByMessage(message);
                String response = this.commands.get(command).execute();
                this.sendResponse(response, chatId);

            } catch (Exception e) {
                //отправить отчет об исключении
                this.sendResponse("handleIncomingMessage: "+e.getMessage(), this.userId);
            }
        }
        else
        {
            this.sendResponse(this.commands.get("help").execute(), chatId);
        }
    }

    protected void sendResponse(String text, Long chatId) throws TelegramApiException {
        SendMessage answer = new SendMessage();
        answer.setChatId(chatId);
        answer.setText(text);
        this.sendMessage(answer);
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return this.apiToken;
    }
}
