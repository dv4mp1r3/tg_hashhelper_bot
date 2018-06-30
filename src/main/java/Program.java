
import dvp.hashdetection.Scanner;
import dvp.tg.bot.HashHelper;
import dvp.tg.bot.JIniFile;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;

public class Program {
    public static void main(String[] args) {
        try {
            JIniFile iniFile = new JIniFile("config.ini");
            String sectionProxy = "proxy";
            String defaultEmpty = "";
            String proxyHost = iniFile.ReadString(sectionProxy, "host", defaultEmpty);
            String proxyPort = iniFile.ReadString(sectionProxy, "port", defaultEmpty);
            if (!proxyHost.isEmpty() && !proxyPort.isEmpty())
            {
                System.getProperties().put( "proxySet", "true" );
                System.getProperties().put( "socksProxyHost", proxyHost );
                System.getProperties().put( "socksProxyPort", proxyPort );
            }
            ApiContextInitializer.init();
            Scanner s = new Scanner("prototypes.json");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            HashHelper hashHelperBot = new HashHelper(iniFile, s);
            telegramBotsApi.registerBot(hashHelperBot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
