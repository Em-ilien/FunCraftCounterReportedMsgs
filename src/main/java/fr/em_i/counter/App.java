package fr.em_i.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class App {

    public static String listFilesForFolder(final File folder, final String contain) {
        StringBuilder stb = new StringBuilder();

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.getName().contains(contain)) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry, contain);
                } else {
                    try {
                        InputStream fileStream = new FileInputStream("logs/" + fileEntry.getName());
                        InputStream gzipStream = new GZIPInputStream(fileStream);
                        Reader decoder = new InputStreamReader(gzipStream, "UTF-8");
                        BufferedReader buffered = new BufferedReader(decoder);
                        stb.append(buffered.lines().collect(Collectors.joining()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                   
                }
            }
        }
        return stb.toString();
    }

    public static void main( String[] args ) {
        final File folder = new File("logs");
        String content = listFilesForFolder(folder, args[0]);

        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stb.append(args[i]+" ");
        }
        String occurence = stb.toString();
        occurence = occurence.substring(0, occurence.length()-1);
        occurence = occurence.split("-occurence=")[1];
        
        int count = 0, fromIndex = 0;

        while ((fromIndex = content.indexOf(occurence, fromIndex)) != -1 ) {
            count++;
            fromIndex++;
        }
        
        System.out.println("Occurences totales : " + count);
    }
}
