package io;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Ermoeglicht die Erstellung eines PatternReader Streams, welcher dazu in 
 * der Lage ist die Zeilennummer wiederzugeben und nach einem regulaeren
 * Ausdruck zu suchen
 * @version 06.12.17
 * @author Mark Hiltenkamp
 */
public class PatternReader extends java.io.BufferedReader{
    
    private int lineNumber;
    private String lastLine;
    private Pattern targetPattern;

    public PatternReader(java.io.Reader in, String regex){
        super(in);
        lineNumber = 0;
        lastLine = null;
        targetPattern = Pattern.compile(regex);
    }

    /**
     * Liest eine Zeile und gibt diese wieder
     * @return die Zeile als String
     */
    public String readLine() throws java.io.IOException{
        // Erhoehe die Zeilennummer
        lineNumber++;
        lastLine = super.readLine();
        return lastLine;
    }

    /**
     * Gibt die Zeilennummer der zuletzt gelesenen Zeile wieder
     * @return die letzte Zeilennummer
     */
    public int getLineNumber(){
        return lineNumber;
    }

    /**
     * Gibt die Anzahl der Vorkommnisse des regulaeren Ausdrucks in der 
     * zuletzt gelesenen Zeile wieder
     * @return die Anzahl der Vorkommnisse
     */
    public int getAmountOfMatches(){
        Matcher m = targetPattern.matcher(lastLine);    
        int matches = 0;
        while(true){
            // Falls der Regulaere Ausdruck nicht gefunden wird, brich ab
            if(!m.find())
                break;
            matches++;
        }
        return matches;
    }
}
