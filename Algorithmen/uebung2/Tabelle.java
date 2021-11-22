/******************************  Tabelle.java  ********************************/

import AlgoTools.IO;

/**
 * @version 03.11.16
 *
 * Gibt eine Tabelle der Größe k*k aus mit zeile % spalte als Elemente 
 *
 * @author  mhiltenkamp
 */

public class Tabelle {

  public static void main(String[] argv) {
      // Variablen für Tabellengröße, Zeile(2, da 2xfor), Spalte anlegen
      int k, z, zeile, spalte;
      
      // korrekte Tabellengröße einlesen
      do{
        k = IO.readInt("Größe der Tabelle ? (zahlen von 1 bis 15 erlaubt) >");
      }while ( 1 > k || k > 15);
      
      // Zeilen und Spalten der Tabelle ausgeben
      for ( zeile = 0; zeile <= k; zeile++ ){
        for ( spalte = 0; spalte <= k; spalte++ ){
          if ( zeile == 0 && spalte == 0 )
            IO.print ("   |");
          else if ( zeile == 0 )
            IO.print ( spalte, 4 );
          else if ( spalte == 0 )
            IO.print ( zeile+"|", 4);
          else
            IO.print ( zeile % spalte, 4 );
        }
        // Zusätzliche "-" unter der ersten Zeile einfügen
        if ( zeile == 0 ){
          IO.println();
          IO.print ("---+");
          for ( z = 0; z < k; z++ )
            IO.print ("----");
        }
        // "In die nächste Zeile wechseln"
        IO.println();
      }
  }
}

