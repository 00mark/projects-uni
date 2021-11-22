package io;

/**
 * Durchsucht Datei(en) nach einem regulaeren Ausdruck und ersetzt jedes
 * Vorkommen durch einen gewuenschten String
 * @version 06.20.17
 * @author Mark Hiltenkamp
 */
public class ReplaceString{

    private static String nl = System.getProperty("line.separator");
    private static boolean recur = false;
    
    public static void main(String[] args){
        String searchRegex = "";
        String replacement = "";
        String rootDir = "";
        int currentArg = 0;
        try{
            if(args[0].equals("-r")){
                recur = true;
                currentArg++;
            }
            searchRegex = args[currentArg];
            replacement = args[currentArg + 1];
            rootDir = args[currentArg + 2];
        }catch(ArrayIndexOutOfBoundsException e1){
            System.out.println("Ungueltige Eingabelaenge");
            usage();
            System.exit(1);
        }
        java.io.File root = new java.io.File(rootDir);
        try{
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(
                   searchRegex);
        }catch(java.util.regex.PatternSyntaxException e2){
            System.out.println("Der regulaere Ausdruck ist ungueltig");
            usage();
            System.exit(1);
        }
        try{
            replace(root, searchRegex, replacement);
        }catch(java.io.IOException e3){
            System.out.println("IOException");
            usage();
            System.exit(1);
        }
    }

    /**
     * Ersetzt in der eingegebenen Datei (und allen Unterdateien) den
     * regulaeren Ausdruck duch einen anderen String
     * @param f Die (Start-)Datei
     * @param regex Der regulaere Ausdruck
     * @param repl Der neue String
     */
    public static void replace(java.io.File f, String regex, String repl) throws
        java.io.IOException{
        if(f.isFile()){
        String content = "";
        // Schreibe den Inhalt der Datei in einen String
        content = convertToString(f);
        String[] contentSplit = content.split(nl);
        for(int i = 0; i < contentSplit.length; i++){
            // Ersetze in jeder den regulaeren Ausdruck durch den neuen String
            contentSplit[i] = contentSplit[i].replaceAll(
                regex, repl);            
        }
        // Schreib den veraenderten Inhalt in die Datei
        writeContent(f, contentSplit);
        }
        // Rekursion
        if(f.isDirectory() && recur){
            java.io.File[] subFiles = f.listFiles();
            for(java.io.File subFile : subFiles)
                replace(subFile, regex, repl);
        }
    }

    /**
     * Schreibt den Inhalt einer Datei in einen String
     * @param f Die Datei
     * @return Den String
     */
    public static String convertToString(java.io.File f) throws 
        java.io.IOException{
            String converted = "";
            java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.FileReader(f));
            String current = br.readLine(); 
            while(current != null){
                converted += current + nl;
                current = br.readLine();
            }
            br.close();
            return converted;
        }

    /**
     * Schreibt den eingegebenen Inhalt in die angegebenen Datei
     * @param f Die Datei
     * @param content der Inhalt
     */
    public static void writeContent(java.io.File f, String[] content) throws
        java.io.IOException{
            java.io.BufferedWriter bw = new java.io.BufferedWriter(new
                    java.io.FileWriter(f));
            for(String line : content){
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }

    /**
     * Gibt aus, wie das Programm verwendet werden soll
     */
    public static void usage(){
        System.out.println("USAGE : ");
        System.out.println("java io/ReplaceString [-r] Search Replacement " +
                "FileOrDirectory");
    }
}
