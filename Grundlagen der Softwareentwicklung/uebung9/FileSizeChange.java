package io;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Benutzt einen SizeTimerTask, um die Groesse einer Datei (oder mehrerer
 * Dateien) auszugeben wenn sich diese aendert
 * @version 06.24.17
 * @author Mark Hiltenkamp
 */
public class FileSizeChange{

    private static File f;

    /**
     * Versucht einen ShutdownThread und einen SizeTimerTask zu initialisieren
     * @param args[0] gueltiger Pfad zu einer Datei bzw einem Verzeichnis
     * @throws ArrayIndexOutOfBoundsException, wenn kein Kommandozeilen-Argument
     *                                         eingegeben wurde
     */
    public static void main(String[] args){
        try{
            f = new File(args[0]);
            if(f.exists()){
                Thread t = new ShutdownThread();
                Runtime.getRuntime().addShutdownHook(t);
                Timer timer = new Timer();
                timer.schedule(new SizeTimerTask(), 1000, 1000);
            }else
                System.out.println("Die Datei kann nicht gefunden werden");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Keine Datei eingegeben");
            System.exit(1);
        }
    }

    /**
     * Prueft, ob sich die Groesser einer, oder mehrerer Dateien veraendert
     * @version 06.24.17
     * @author Mark Hiltenkamp
     */
    private static class SizeTimerTask extends TimerTask{

        // Aktuelle Groesse der Datei(en). Anfaenglich -1, damit nach Aufruf 
        // immer einmal die Groesse geprintet wird
        private long currentSize = -1;

        /**
         * Gibt Die Groesse der Datei(en) aus, falls diese sich veraendert hat
         */
        public void run(){
            if(currentSize != calculateSize(f)){
                currentSize = calculateSize(f);
                System.out.println(currentSize + " Byte");
            }
        }

        /**
         * Berechnet die Gesamtgroesse einer Datei, bzw aller Dateien in
         * einem Verzeichnis
         * @param f Die Datei, bzw das Verzeichnis
         * @return Die Gesamtgroesse
         */
        public static long calculateSize(File f){
            long size = f.length();
            if(!f.isDirectory())
                return size;
            else{
                for(File file : f.listFiles())
                    size += calculateSize(file);
                return size;
            }
        }
    }

    /**
     * Erstellt einen Thread, welcher bei ausfuehrung "EXIT" printet
     * @version 06.24.17
     * @author Mark Hiltenkamp
     */
    private static class ShutdownThread extends Thread{

        /**
         * Gibt "EXIT" aus
         */
        public void run(){
            System.out.println("EXIT");
        }
    }
}
