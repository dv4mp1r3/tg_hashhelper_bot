
import dvp.hashdetection.Scanner;
import dvp.tg.bot.HashHelper;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

public class Program {
    public static void main(String[] args) {
        try {

            /*System.getProperties().put( "proxySet", "true" );
            System.getProperties().put( "socksProxyHost", "127.0.0.1" );
            System.getProperties().put( "socksProxyPort", "9150" );*/
            ApiContextInitializer.init();
            Scanner s = new Scanner("prototypes.json");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            HashHelper hashHelperBot = new HashHelper("config.ini", s);
            telegramBotsApi.registerBot(hashHelperBot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
