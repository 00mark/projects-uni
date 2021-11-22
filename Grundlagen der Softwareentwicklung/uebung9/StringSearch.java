package io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Enumeration;
import java.util.LinkedList;

/**
 * Sucht nach einem regulaeren Ausdruck in einer Datei, oder einem ganzen
 * Verzeichnis und gibt den Dateinamen von Dateien mit Treffen aus, sowie
 * die Zeilennummer und Anzahl der Treffer, als auch die jeweilige Zeile
 * @version 06.27.17
 * @author Mark Hiltenkamp
 */
public class StringSearch{

    private static String nl = System.getProperty("line.separator");
    // Beinhaltet File Objekte mit Treffern als keys und die Zeilen mit
    // Treffern, sowie die Zeilennummer und Anzahl der Treffer als values
    private static ConcurrentHashMap<File, String> cHashMap = new 
        ConcurrentHashMap<>();
    // Beinhaltet alle angelegten Threads
    private static LinkedList<Thread> l = new LinkedList<>();

    /**
     * Liest ein Kommandozeilen Parameter ein, faengt Exceptions ab und
     * fuehrt das Programm aus
     * @param args : args[0] : muss entweder "-r" fuer Rekursion, oder ein 
     *                         gueltiger regulaerer Ausdruck sein
     *               args[1] : Falls args[0] == "-r" muss args[1] ein gueltiger
     *                         regulaerer Ausdruck sein, sonst ein gueltiger
     *                         Pfad zu einer Datei
     *               args[2] : Falls args[0] "-r" ist, muss hier ein gueltiger
     *                         Pfad zu einer Datei eingegeben werden
     */ 
    public static void main(String[] args){
        boolean recur = false;
        int currentParam = 0;
        Pattern regPattern = null;
        FileSystem fs = null;
        try{
            // Rekursion?
            if(args[0].equals("-r")){
                    recur = true;
                    currentParam++;
            }
            try{
                String regex = args[currentParam];
                regPattern = Pattern.compile(regex); 
            }catch(java.util.regex.PatternSyntaxException e1){
                System.out.println("Ungueltiger regulaerer Ausdruck");
                System.exit(1);
            }
            File f = new File(args[currentParam+1]);
            if(f.exists()){
                fs = new FileSystem(f);
            }else{
                System.out.println("Der eingegebenen Pfad ist ungueltig");
                System.exit(1);
            }
            SearchLineVisitor slv = new SearchLineVisitor();
            slv.setPattern(regPattern);
            slv.setRecursion(recur);
            fs.accept(slv);
            try{
                // Warte auf die Beendigung der Threads
                joinAll();
            }catch(InterruptedException e){
                System.out.println("Der Thread wurde unterbrochen");
            }
            printResults();
        }catch(ArrayIndexOutOfBoundsException e2){
            System.out.println("Unvollstaendige Eingabeparameter");
        }
    }

    /**
     * Iteriert durch die Liste der Threads und warte auf die Beendigung dieser
     * @throws InterruptedException, falls einer der Threads unterbrochen wurde
     */
    public static void joinAll() throws InterruptedException{
        for(Thread t : l)
                t.join();
    }

    /**
     * Gibt den Dateinamen von Dateien mit Treffern aus, sowie die Zeilen
     * mit Treffen, die Zeilennummer und die Anzahl der Treffer pro Zeile
     */
    public static void printResults(){
        // Alle Dateien mit Treffern
        Enumeration<File> e = cHashMap.keys();
        while(e.hasMoreElements()){
            File current = e.nextElement();
            // Die jeweiligen Zeilen mit Treffern
            String content = cHashMap.get(current);
            System.out.println(current.getAbsolutePath());
            System.out.println(content);
        }
    }

    /**
     * Visitor, der an jeder Datei des Filesystems aufgerufen wird und eine
     * neuen Thread erstellt, welcher die Datei nach einem regulaeren Ausdruck
     * durchsucht
     * @version 06.27.17
     * @author Mark Hiltenkamp
     */
    public static class SearchLineVisitor implements FileVisitor {

        private Pattern p = null;
        private SearchLineRunnable r;
        private boolean recur;

        /**
         * Wird nach jedem Verzeichnis des Filesystems aufgerufen
         * @param dir Das Verzeichnis
         */
        public FileVisitResult postVisitDirectory(File dir) {
            return FileVisitResult.CONTINUE;
        }

        /**
         * Wird vor jedem Verzeichnis des Filesystems aufgerufen. Falls rekursiv
         * wird das Verzeichnis betreten, sonst nicht
         * @param dir Das Verzeichnis
         */
        public FileVisitResult preVisitDirectory(File dir) {
            if(recur)
                return FileVisitResult.CONTINUE;
            return FileVisitResult.SKIP_SUBTREE;
        }

        /**
         * Wird aufgerufen, falls der Besucht der Datei fehlgeschlagen ist
         * @param file Die Datei
         */
        public FileVisitResult visitFailed(File file) {
            return FileVisitResult.CONTINUE;
        }

        /**
         * Wird an jeder Datei des Filesystems aufgerufen und startet einen
         * Thread, der die Datei mit Hilfe eines Patterns durchsucht
         * @param file Die Datei
         */
        public FileVisitResult visitFile(File file) {
            r = new SearchLineRunnable(file, p);
            Thread t = new Thread(r);
            t.start();
            l.add(t);
            return FileVisitResult.CONTINUE;
        }

        /**
         * Weisst dem Pattern ein neues Objekt zu
         * @param p das neue Pattern
         */
        public void setPattern(Pattern p){
            this.p = p;
        }

        /**
         * Legt fest, ob rekursiv durch das Filesystem gegangen werden soll
         * @param recur, falls true => Rekursion, sonst keine Rekursion
         */
        public void setRecursion(boolean recur){
            this.recur = recur;
        }
    }

    /**
     * Bietet die Moeglichkeit ein neues Runnable zu erzeugen, welches in der 
     * run Methode die eingegebene Datei nach nach dem eingegebenen Pattern
     * durchsucht
     * @version 06.27.17
     * @author Mark Hiltenkamp
     */
    public static class SearchLineRunnable implements Runnable{

        private File f;
        private Pattern p;

        public SearchLineRunnable(File f, Pattern p){
            this.f = f;
            this.p = p;
        }

        /**
         * Erzeugt einen neuen SearchLineReader, der die eingegebene Datei
         * nach dem eingegebenen Pattern durchsucht. Falls das Pattern
         * gefunden wurde, wird das File Objekt als key und die Zeilen mit 
         * Treffern, sowie der Zeilennummer und Anzahl der Treffer als value in
         * eine HashMap geschrieben
         */
        public void run(){    
            String lines = "";
            try(SearchLineReader slr = new SearchLineReader(new FileReader(f)
                        , p)){
                String currentLine = slr.readLine();
                // Gehe alle Zeilen der Datei durch
                while(currentLine != null){
                    // Gibt es das Pattern in der aktuellen Zeilen?
                    if(slr.getAmountOfMatches() != 0)
                        lines += "-> [" + slr.getLineNumber() + "]  \t[" + 
                            slr.getAmountOfMatches() + (slr.getAmountOfMatches()
                            > 1 ? " Matches" : " Match") + "]\t" + currentLine +
                            nl;
                    currentLine = slr.readLine();
                }
            }catch(IOException e){
                System.out.println("IOException");
            }
            // Gab es Treffer?
            if(!lines.equals(""))
                cHashMap.put(f, lines);
        }
    }
}
