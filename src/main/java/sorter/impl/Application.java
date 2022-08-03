package sorter.impl;

import sorter.cmd.CommandLine;
import sorter.impl.matcher.Expression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.File;

import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.Set;


/**
 * Application Runtime
 */
final class Application {

    private final ApplicationOptions op;

    Application(ApplicationOptions options, CommandLine cli) {
        Objects.requireNonNull(options);
        Objects.requireNonNull(cli);

        this.op = options;
    }

    private Map<String, Expression> getExpressions(){
        Map<String, Expression> expressionMap = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(op.getFilters().toFile(), op.getCharset()))){
            String line = reader.readLine(); //skip file header
            while((line = reader.readLine()) != null){
                String[] splitedLine = line.split(op.getDelimiter());
                expressionMap.put(splitedLine[0], new Expression(splitedLine[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expressionMap;
    }

    private void write(Map<String, PrintWriter> files, String fileName, String head, String line) throws IOException {
        PrintWriter printWriter = files.get(fileName);
        if(printWriter == null){
            File newFile = new File(op.getOutput().toString(), fileName);
            boolean fileExist = newFile.exists();
            files.put(fileName, printWriter = Utils.createPrintWriter(newFile, op.getCharset()));
            if(!fileExist){
                printWriter.println(head);
            }
        }
        printWriter.println(line);
    }

    /**
     * Note:    This method represents the runtime logic.
     *          However, you should still use proper decomposition.
     *
     * Application runtime logic
     */
    void run() {
        Map<String, Expression> expressionMap = getExpressions();

        //Faster than (Open -> Seek -> Write -> Close) in every iteration
        Map<String, PrintWriter> files = new HashMap<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(op.getInput().toString(), op.getCharset()))){
            String header = reader.readLine();
            int indexOfLabels = Utils.indexOfLabels(header.split(op.getDelimiter()), op.getLabelColumn());
            String line;
            //Line by line reading from .csv
            while((line = reader.readLine()) != null){
                String[] splitedLine = line.split(op.getDelimiter());

                for(Map.Entry<String, Expression> entry : expressionMap.entrySet()){
                    if(entry.getValue().checkMatch(Set.of(splitedLine[indexOfLabels].split(" ")))){
                        write(files, entry.getKey() + ".csv", header, line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.closeFiles(files);
        }
    }
}

