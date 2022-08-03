package sorter.impl;

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * Auxiliary functions
 */
final class Utils {

    /**
     * Return first occurence of the target in header array
     * @param strings string array
     * @param target searched string
     * @return return index of first occurence of target otherwise -1
     */
    public static int indexOfLabels(String[] strings, String target){
        for(int i = 0; i < strings.length; i++){
            if(strings[i].equals(target)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Create new PrintWriter
     * @param file file
     * @param chrst target charset
     * @return new PrintWriter with input parameters
     * @throws IOException when file is not exist
     */
    public static PrintWriter createPrintWriter(File file, Charset chrst) throws IOException {
        BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), chrst));
        return new PrintWriter(bf);
    }

    /**
     * Close all files
     * @param files input Map with files
     */
    public static void closeFiles(Map<String, PrintWriter> files){
        for (PrintWriter writer : files.values()){
            writer.close();
        }
    }
}
