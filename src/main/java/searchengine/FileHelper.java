package searchengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Denislav on 4/19/2019.
 */
public class FileHelper {

    /**
     * The class that passes the file and creates the list of websites and returns them
     * @param filename String
     * @return List<searchengine.Website>
     */
    public static List<Website> parseFile(String filename) {
        List<Website> sites = new ArrayList<Website>();
        String url = null, title = null;
        List<String> listOfWords = null;

        System.out.println("Parsing file...");

        try {
            Scanner sc = new Scanner(new File(filename), "UTF-8");
            while (sc.hasNext()) {
                String line = sc.nextLine();

                // If line is null or empty - continue to next line
                if (line == null || line.isEmpty()) { continue; }

                // Creating of a webpage
                if (line.startsWith("*PAGE:")) {
                    // create previous website from data gathered

                    // Only create a website if the url, title, listOfWords and listOfWords is equal or greater than 1.
                    if (url != null && title != null) {

                        if (listOfWords != null && listOfWords.size() >= 1) {
                            sites.add(new Website(url, title, listOfWords));
                        } else {
                            DebugHelper.log("The website has no words and cannot be build");
                        }
                    }

                    // new website starts
                    url = line.substring(6);
                    title = null;
                    listOfWords = null;

                } else if (title == null) {
                    title = line;
                } else {
                    // and that's a word!
                    if (listOfWords == null) {
                        listOfWords = new ArrayList<String>();
                        listOfWords.add(line);
                    } else {
                        listOfWords.add(line);
                    }

                }
            }
            if (url != null) {
                sites.add(new Website(url, title, listOfWords));
            }
            // End of creating a webpage

        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load the given file");
        }

        return sites;

    }

}
