package util;
import java.io.File;

/**
 * Ermoeglicht die Erstellung eines ListVisitors, wessen Methode visit
 * an den File Objekten einer List Instanz aufgerufen werden kann
 * @version 06.09.17
 * @author Mark Hiltenkamp
 */
public class ListVisitor<E> implements Visitor<E>{
    
    private static final int MAXVISIT = 100000;
    private static int visited = 0;
    /**
     * Wird an den File Objekten einer List Instanz aufgerufen
     * @param elem die jeweilige File Instanz
     * @return true, falls die File Instanz gelesen werden kann, sonst false
     */
    public boolean visit(E elem){
        //if(((File)elem).canRead()){
         //   visited++;
          //  return true; 
        //}
        //return false;
        return true;
    }

    /**
     * Wird an Verzeichnissen aufgerufen und entscheidet darueber, ob diese 
     * ausgelassen oder verfolgt werden, bzw das Programm abgebrochen werden
     * soll
     * @param f das jeweilige Verzeichnis
     */
    public byte preDir(File f){
        // Lass das Verzeichnis aus, falls es versteckt ist
        //if(f.isHidden())
         //   return 1;
        // Brich das Programm ab, falls die Laenge des Namens der File 
        // Instanz > 100 ist
        //if(f.getName().length() > 100)
         //   return -1;
        return 0;
    }

    /**
     * Wird ausgefuehrt, nachdem ein Verzeichnis durchlaufen wurde und
     * entscheidet, ob abgebrochen werden soll
     */
    public boolean postDir(){
        //if(visited > MAXVISIT)
         //   return false;
        return true; 
    }
}
