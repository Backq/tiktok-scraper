package me.back.gg.utils;

import me.back.gg.Entrypoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static void readList(String name) throws IOException {
        File f = new File( name );
        if(!f.exists()) {
            System.out.println("File '" + f.getName() + "' doesn't exists");
            System.exit(0);
        }

        System.out.println("\t[+] Loading list (" + name + ")");
        try(BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()))) {
            for(String line; (line = br.readLine()) != null; ) {
                if(line.length() > 1) {
                    System.out.println(line);
                    User.username.add(line);
                }
            }
            System.out.println("\t[+] loaded " + User.username.size() + " username");
        }
    }

    public static void initFile() throws IOException {
        Path currentPath = Paths.get("");
        String currentDir = currentPath.toAbsolutePath().toString();

        File cookie = new File(currentDir + "\\cookie.txt");

        // If the file doesn't exists, creates one.
        //
        if (!cookie.exists()) {
            cookie.createNewFile();

            System.out.println("[+] Cookie file created, the program will close. Fill 'Cookie.txt' and restart the program.");
            System.out.println("[!] Cookie.txt Directory: " + cookie.getAbsolutePath());
            System.exit(0);
        } else {
            // Read the content and fill the string
            //
            try (BufferedReader br = new BufferedReader(new FileReader(cookie))) {
                for (String line; (line = br.readLine()) != null; ) {
                    Entrypoint.cookie = line;
                }
            }
        }
    }

}
