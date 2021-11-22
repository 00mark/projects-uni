/*******************************  Menge.java **********************************/

/** Interface zum Verwalten einer Menge von Comparable-Objekten.     
 */

public interface Menge {

    public boolean empty();                 // testet, ob Menge leer ist

    public Comparable lookup(Comparable x); // Sucht x in Menge
                                            // liefert Comparable-Object,
                                            // falls gefunden
                                            // liefert null sonst

    public boolean insert(Comparable x);    // versucht x in Menge einzufuegen
                                            // liefert true, falls erfolgreich 
                                            // liefert false sonst   

    public boolean delete(Comparable x);    // versucht x aus Menge zu entfernen
                                            // liefert true, falls erfolgreich
                                            // liefert false sonst
}

