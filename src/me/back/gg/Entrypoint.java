package me.back.gg;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import me.back.gg.utils.User;
import me.back.gg.utils.types.DictionaryType;
import me.back.gg.utils.ITWordGenerator;
import me.back.gg.utils.Utils;
import me.back.gg.utils.types.Language;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;


public class Entrypoint {

    public static String button;
    public static String body;

    public static String cookie;
    private static int dicType;
    private static String langType;

    public static void main(String[] args) throws IOException {
        // Disables some logging shit.
        //
        Logger.getLogger("org.apache.http.wire").setLevel( Level.FINEST);
        Logger.getLogger("org.apache.http.headers").setLevel(Level.FINEST);
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "ERROR");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "ERROR");
        Scanner sc = new Scanner(System.in);

        System.out.println("[ TikTok name checker ]");
        System.out.println("\tdeveloped by @Backq");
        System.out.println("\tDiscord: Back#7506\n\n");

        // Init 'Cookie.txt' file
        // Here you need to set the cookies, I already explained how to do it on GitHub.
        //
        Utils.initFile();

        // Obviously.
        //
        if(cookie == null) {
            System.out.println("[!!] Fill 'Cookie.txt', you can see how into the repository.");
            return;
        }

        Path currentRelativePath = Paths.get("");
        String currentDirectory = currentRelativePath.toAbsolutePath().toString();

        // Choose a mode.
        //
        System.out.print("[*] 0 - Generate Names\n[*] 1 - TikTok Availability Check\n");
        int mode = sc.nextInt();

        if (mode == 0) {
            // Dictionary Type.
            //
            System.out.print("[*] Choose the Dictionary Type.\n" +
                    "\t0 - Complete Dictionary\n" +
                    "\t1 - Children's Dictionary\n" +
                    "\t2 - Food Words\n" +
                    "\t3 - Animals\n" +
                    "\t4 - Colors\n" +
                    "\t5 - Human Body\n" +
                    "\t6 - Education\n" +
                    "\t7 - Family\n" +
                    "\t8 - Geometric Figures\n" +
                    "\t9 - Communication Tools\n" +
                    "\t10 - Numbers\n" +
                    "\t11 - Numbers ( 0 to 9 )\n" +
                    "\t12 - Professions\n" +
                    "\t13 - Transport\n");
            int dictionaryTpe = sc.nextInt();
            switch(dictionaryTpe) {
                case 0:
                    dicType = DictionaryType.Complete_Dictionary.getID();
                    break;
                case 1:
                    dicType = DictionaryType.Children_Dictionary.getID();
                    break;
                case 2:
                    dicType = DictionaryType.Food.getID();
                    break;
                case 3:
                    dicType = DictionaryType.Animals.getID();
                    break;
                case 4:
                    dicType = DictionaryType.Colors.getID();
                    break;
                case 5:
                    dicType = DictionaryType.Human_Body.getID();
                    break;
                case 6:
                    dicType = DictionaryType.Education.getID();
                    break;
                case 7:
                    dicType = DictionaryType.Family.getID();
                    break;
                case 8:
                    dicType = DictionaryType.Geometric_Figures.getID();
                    break;
                case 9:
                    dicType = DictionaryType.Communication_Tools.getID();
                    break;
                case 10:
                    dicType = DictionaryType.Numbers.getID();
                    break;
                case 11:
                    dicType = DictionaryType.Numbers_0_9.getID();
                    break;
                case 12:
                    dicType = DictionaryType.Professions.getID();
                    break;
                case 13:
                    dicType = DictionaryType.Transport.getID();
                    break;
            }

            // Language Type.
            //
            System.out.print("[*] Choose the Language Type.\n" +
                    "\t0 - Italian\n" +
                    "\t1 - English\n" +
                    "\t2 - Spanish\n" +
                    "\t3 - French\n");
            int languageType = sc.nextInt();
            switch(languageType) {
                case 0:
                    langType = Language.Italian.getLanguage();
                    break;
                case 1:
                    langType = Language.English.getLanguage();
                    break;
                case 2:
                    langType = Language.Spanish.getLanguage();
                    break;
                case 3:
                    langType = Language.French.getLanguage();
                    break;

            }

            System.out.println("[+] Dictionary: " + dicType);
            System.out.println("[+] Language: " + langType);

            System.out.println("\n\t[+] Generating names...\n\t[!!] Names will be saved to: " + currentDirectory + ITWordGenerator.fileName + "\n");
            while(true) {
                ITWordGenerator.getWords(10, dicType, langType);
            }
        }

        //
        // Start mode 1.
        //

        if (mode == 1) {
            // Just creates the progression file before start
            //
            File progressionFile = new File(currentDirectory + "\\savedProgression.txt");

            // Reads our .txt name
            //
            System.out.print("[*] Write the .txt (including .txt) name to scan\n");
            String fileName = "\\"+sc.next();
            Utils.readList(currentDirectory + fileName);

            System.out.print("\n[*] Write your language ( English - Italian )\n");
            String language = sc.next();

           /* if(!language.equalsIgnoreCase("English") || !language.equalsIgnoreCase("Italian")) {
                System.out.println("Choose the correct one ( English or Italian )");
                return;
            }*/

            switch(language) {
                case "English":
                    button = "Follow</button>";
                    body = "Cannot find this account";
                    break;

                case "Italian":
                    button = "Segui</button>";
                    body = "Impossibile trovare questo account";
                    break;
            }

            System.out.println("[+] Language: " + language);

            // Here is where the 'Scrape' starts.
            // This is a basic check, so It doesn't check if the user is banned or not.
            // Idc about doing it in a proper way.
            //
            for(String names : User.username) {
                try {
                    HttpResponse<String> response = Unirest.get("https://Tiktok.com/@" + names)
                            .header("accept-language", "it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7")
                            .header("cookie", cookie)
                            .asString();

                    if (response.getBody().contains(button) /* || response.getBody().contains("Follow</button>")*/) {
                        System.out.println(names + " NOT AVAILABLE");
                        User.unavailable.add(names);
                    }

                    else if (!response.getBody().contains(button) || response.getBody().contains(body) /* || !response.getBody().contains("Follow</button>") || response.getBody().contains("Cannot find this account") */) {
                        System.out.println(names + " AVAILABLE");

                        // Appends the available name just for save the progression.
                        //
                        FileUtils.writeStringToFile(progressionFile, names+"\n", true);

                        //User.available.add(names); /* just because we want to store it for fun */
                        //System.out.println("[arraylist-debug] " + User.available);
                    }

                } catch (Exception exx) {exx.printStackTrace();}

            }

            System.out.println("[+] Scan finished.\n\t[*] Names saved in '" + progressionFile.getAbsolutePath() + "'.\nYou can close the program now.");
        }
    }


}
