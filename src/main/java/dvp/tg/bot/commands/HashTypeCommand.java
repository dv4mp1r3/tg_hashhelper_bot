package dvp.tg.bot.commands;

import dvp.hashdetection.Scanner;
import java.util.ArrayList;

public class HashTypeCommand extends AbstractCommand {

    Scanner scanner;

    public HashTypeCommand(Scanner scanner) {
        super();
        this.scanner = scanner;
    }

    @Override
    public String execute() {
        String result = "";
        int count = this.scanner.find(this.args[0]);
        if (count > 0)
        {
            ArrayList<String> arrayList = this.scanner.getParsedHashes();
            for (String name: arrayList)
            {
                result += name + "\n";
            }
        }
        else
        {
            result += "Соответствие не найдено для хеша "+this.args[0];
        }

        return result;
    }
}
