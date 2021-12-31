package me.back.gg.utils;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import me.back.gg.utils.types.DictionaryType;
import me.back.gg.utils.types.Language;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ITWordGenerator {
    public static ArrayList<String> names = new ArrayList<String>();
    public static String index;
    public static String fileName = "\\generatedNames.txt";

    public static void getWords(int amount, int dictionary_type, String language_type) {
        Path currentRelativePath = Paths.get("");
        String currentDirectory = currentRelativePath.toAbsolutePath().toString();

        // 10 is the max.
        //
        if (amount > 10) {
            amount = 10;
        }

        // Fuckoff to the switch, let's do it in a noob way cuz yes.
        //
        if (language_type.equalsIgnoreCase(Language.Italian.getLanguage())) {
            index = "parole-casuali.php";
        } else if (language_type.equalsIgnoreCase(Language.English.getLanguage())) {
            index = "random-words.php";
        } else if (language_type.equalsIgnoreCase(Language.Spanish.getLanguage())) {
            index = "index.php";
        } else if (language_type.equalsIgnoreCase(Language.French.getLanguage())) {
            index = "mots-aleatoires.php";
        }

        try {
            HttpResponse<String> response = Unirest.get("https://www.palabrasaleatorias.com/"+ index + "?fs="+ amount +"&fs2="+ dictionary_type +"&Submit="+language_type)
                    .header("accept", " text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("accept-encoding", " gzip, deflate, br")
                    .header("accept-language", " it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("referer", " https://www.palabrasaleatorias.com/"+ index +"?fs="+ amount +"&fs2="+ dictionary_type +"&Submit=" + language_type)
                    .header("user-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36")
                    .asString();

            //System.out.println(response.getBody());

            String fullBody = response.getBody();
            Pattern pattern = Pattern.compile("var contenidoACopiar =(.*)");
            Matcher matcher = pattern.matcher(fullBody);

            // Bad code but idc, it does his work.
            //
            if (matcher.find()) {
                String found = matcher.group(1);
                String removeColon = found.replaceAll("\"", "");
                String removeSpecial = removeColon.replaceAll(";", "");
                //String finalParsed = removeSpecial.replaceAll("\\\\n", " ");

                String[] words = removeSpecial.split("\\\\n", 255);
                for (int i = 0; i < 10; i++) {
                    String parts = words[i].replace(" ", "");
                    names.add(parts);
                    File filez = new File(currentDirectory + fileName);
                    PrintWriter outz = new PrintWriter(new BufferedWriter(new FileWriter(filez.getName(), true)));
                    System.out.println(parts);
                    outz.println(parts);
                    outz.close();
                }
                //System.out.println("[arraylist debug] - " + names);
            }
        } catch (Exception aa) {}
    }
}
