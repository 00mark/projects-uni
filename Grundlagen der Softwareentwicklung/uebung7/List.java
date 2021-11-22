package util;
import java.io.File;

/**
 * Bildet das Dateisystem ab einer bestimmten Datei ab
 * @version 06.08.17
 * @author Mark Hiltenkamp
 */
public class List implements Visitable{

    // Wurzelverzeichnis
    private File root;
    private static List l = null;
    // Rekursiv
    private static boolean recur = false;
    private static final String DIRREGEX = "/|(/?[^/]+)+/?";
    private static final String CURRENTDIRREGEX = "[^/]+";
    private static final byte EXIT = -1;
    private static final byte CONTINUE = 0;
    private static final byte SKIP = 1;
    // Aktuelles Verzeichnis
    private static String currentDir = System.getProperty("user.dir");

    public List(String rootDir){
        root = new File(rootDir); 
    }

    /**
     * Gibt das lokale Wurzelverzeichnis wieder
     * @return das Wurzelverzeichnis
     */
    public File getRoot(){
        return root;
    }

    /**
     * Liest optional das Wurzelverzeichnis ein und prueft, ob die
     * Dateien rekrusiv abgebildet werden sollen
     * @param args[0] -r, falls rekursiv, sonst File, oder null
     * @param args[1] File, falls args[0] kein File war, oder null
     */
    public static void main(String[] args){
        try{
            // Rekursion
            if(args[0].equals("-r"))
                recur = true;
            // Keine Rekursion
            else{
                // args[0] ist ein gueltiger File
                if(args[0].matches(DIRREGEX)){
                    if(args[0].matches(CURRENTDIRREGEX))
                        l = new List(currentDir + "/" +  args[0]);
                    else
                        l = new List(args[0]);
                }
                else{
                    // args[0] ist nicht null, aber auch nicht -r oder File
                    usage();
                    System.exit(0);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e1){
            // Nichts eingegeben => Aktuelles Verzeichnis = Wurzelverzeichnis
            l = new List(currentDir);
        }
        if(recur){
            try{
                if(args[1].matches(DIRREGEX)){
                    if(args[1].matches(CURRENTDIRREGEX))
                        l = new List(currentDir + "/" + args[1]);
                    else
                        l = new List(args[1]);
                }
                else{
                    usage();
                    System.exit(0);
                }
            }catch(ArrayIndexOutOfBoundsException e2){
                l = new List(currentDir);
            }
        }
        if(l.getRoot().exists())
            l.accept(new ListVisitor());
        else{
            System.out.println("File existiert nicht");
            usage();
        }
    }

    /**
     * Wird von der accept Methode aufgerufen und gibt das Dateisystem 
     * ab dem Wurzelverzeichnis, falls erwuenscht rekursiv, formatiert aus.
     * Ruft visit an jedem File Objekt auf, kann Verzeichnisse ueberspringen
     * und die Methode vorzeitig beenden
     * @param v der Visitor, dessen Methode visit an jedem File Objekt
     *          aufgerufen wird
     * @param f das momentane Verzeichnis
     * @param tabs Einrueckungen, die vorgenommen werden sollen
     * @param blanks Array von Einrueckungen, bei denen nichts geprintet
     *               werden soll
     * @throws IllegalArgumentException, falls vor dem Betreten eines 
     *                                   Ordners weder -1(Methode beenden), noch
     *                                   0(Methode normal fortsetzen), oder
     *                                   1(Verzeichnis auslassen) von der 
     *                                   Methode preDir zurueckgegeben wird
     */
    private static void ListContent(Visitor v, File f, int tabs, int[] blanks){
        File[] files = f.listFiles();
        boolean printed;
        // Das erste Element wird ohne Formatierung wiedergegeben
        if(tabs == 0){
            if(v.visit(f))
                System.out.println(f.getName());
            else
                System.exit(0);
        }
        // Rekursionsanker
        if(files == null)
            ;
        else{
            // Soll abgebrochen, das Verzeichnis ausgelassen, oder normal
            // weitergemacht werden?
            switch(((ListVisitor)v).preDir(f)){
                case EXIT     : System.exit(0);
                case SKIP     : break;
                case CONTINUE :{
                    for(File file : files){
                         if(v.visit(file)){
                            // Ruecke die Ausgabe ein
                            for(int t = 0; t < tabs; t++){
                                printed = false;
                                for(int b : blanks){
                                    if(b == t){
                                        System.out.print("  ");
                                        printed = true;
                                    }
                                }
                                if(!printed)
                                    System.out.print("\u2551 ");
                            }
                            // Ist file das letzte File Objekt des Ordners?
                            if(file == files[files.length-1]){
                                System.out.println("\u255A\u2550" + 
                                        file.getName());
                                int[] moreBlanks = java.util.Arrays.copyOf(
                                        blanks, blanks.length+1);
                                moreBlanks[blanks.length] = tabs;
                                blanks = moreBlanks;
                                // Soll abgebrochen, oder weitergemacht werden?
                                if(((ListVisitor)v).postDir())
                                    ;
                                else
                                    System.exit(0);
                            }
                            else
                                // file ist nicht das letzte File Objekt
                                System.out.println("\u2560\u2550" + 
                                        file.getName());
                            // Geh in die Unterordner, falls rekursiv
                            if(recur)
                                ListContent(v, file, tabs+1, blanks);
                        }
                        else
                            System.exit(0);
                    }
                }; break;
                default        : throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Gibt aus, wie das Programm verwendet werden soll
     */
    public static void usage(){
        System.out.println("USAGE:");
        System.out.println("java util/List [-r] [DateiOderVerzeichnisname]");
    }

    /**
     * Ruft visit des eingegebenen Visitors an jedem File Objekt auf
     * @param v Visitor muss eine ListVisitor Instanz sein
     * @throws IllegalArgumentException, falls v kein ListVisitor ist
     */
    public void accept(Visitor v){
        if(!(v instanceof ListVisitor))
            throw new IllegalArgumentException("ungueltiger Visitor");
        int[] a = new int[0];
        ListContent(v, l.getRoot(), 0, a); 
    }
}
