package io;

/**
 * Durchsucht die eingegebene Datei mit Hilfe der Klasse PatternReader nach 
 * einem eingegebenen regulaeren Ausdruck und gibt Zeilen, die den regulaeren
 * Ausdruck beinhalten mit Zeilennummer und Anzahl der Vorkommnisse aus
 * @version 06.12.17
 * @author Mark Hiltenkamp
 */
public class SearchLines{
    
    public static void main(String[] args){
        String regex = null;

        try{
            regex = args[0];
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Keinen regulaeren Ausdruck eingegeben");
            // Brich das Programm ab, falls kein regulaerer Ausdruck eingegeben
            // wurde
            System.exit(-1);
        }

        try(PatternReader patR = new PatternReader(
                    new java.io.InputStreamReader(System.in), regex)){
            String line = patR.readLine();
            while(line != null){
                int matches = patR.getAmountOfMatches();
                // Das Pattern wurde in der Zeile gefunden
                if(matches != 0){
                    System.out.println("[" + patR.getLineNumber() + 
                            "]\t[" + matches + 
                            (matches > 1 ? " Matches]\t:\t" : " Match]\t:\t") +
                            line);
                }
                line = patR.readLine();
            }
        }catch(java.io.IOException e2){
            System.out.println("IOException aufgetreten\nIst die Datei ein" +
                    " Verzeichnis?");
            System.exit(-1);
        }
    }
}
