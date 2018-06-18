package dvp.hashdetection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import static java.nio.file.Files.*;

public class Scanner
{
    protected String source;

    protected ArrayList<String> parsedHashes;

    JsonArray data;

    /**
     *
     * @param sourcePath путь к файлу с названиями алгоритмов хеширования и регулярками для сопоставления
     * @throws IOException
     */
    public Scanner(String sourcePath) throws IOException {
        this.parsedHashes = null;
        this.source = sourcePath;
        this.parsedHashes = new ArrayList<String>();
        this.parseJson();
    }

    private String loadJson() throws IOException {
        return new String(readAllBytes(Paths.get(this.source)), StandardCharsets.UTF_8);
    }

    protected void parseJson() throws IOException {
        JsonParser parser = new JsonParser();
        data = parser.parse(this.loadJson()).getAsJsonArray();
    }

    /**
     * Сопоставление хеша с известными алгоритмами по регулярке
     * @param hash
     * @return количество совпадений по регулярным выражениям
     * количество возможных алгоритмов может быть больше количества совпадений
     * (например, если для нескольких алгоритмов одна регулярка)
     */
    public int find(String hash)
    {
        int count = 0;
        this.parsedHashes.clear();
        for(JsonElement sample : this.data)
        {
            JsonObject sampleObject = sample.getAsJsonObject();
            String regex = sampleObject.get("regex").getAsString();
            Pattern p = null;
            try
            {
                p = Pattern.compile(regex);
            }
            catch (PatternSyntaxException e)
            {
                continue;
            }

            Matcher m = p.matcher(hash);
            if (m.find())
            {
                this.buildAnswer(sampleObject.getAsJsonArray("modes"));
                count += 1;
            }
        }

        return count;
    }

    public ArrayList<String> getParsedHashes()
    {
        return this.parsedHashes;
    }

    /**
     * Наполнение массива с возможными значениями
     * @param possibleHashes
     */
    protected void buildAnswer(JsonArray possibleHashes)
    {

        for (JsonElement possibleHash: possibleHashes)
        {
            JsonObject hashObject = possibleHash.getAsJsonObject();
            String hashName = hashObject.get("name").getAsString();
            this.parsedHashes.add(hashName);
        }

    }

}
