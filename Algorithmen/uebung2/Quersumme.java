/******************************  Quersumme.java  ******************************/

import AlgoTools.IO;

/**
 * @version 03.11.16
 *
 * Gibt die Quersumme der eingelesenen Zahl aus (123 = 6)
 *
 * @author  mhiltenkamp
 */

public class Quersumme {

  public static void main(String[] argv) {
    
    int n;
    // korrektes n einlesen
    do{
    n = IO.readInt(" Bitte einen positiven ganzzahligen Integer eingeben >");
    }while (n <= 0);
    
    // Wert von n in m abspeichern und Quersumme mit 0 initialisieren
    int m = n;
    int quer = 0;
    
    // jeweils Quersumme um letzten Zähler von n erhöhen und n um+
    // eine Stelle verkürzen 
    while (n > 0){
      quer = quer + n % 10;
      n = n / 10;
    }
    IO.println("Die Quersummer von "+m+" ist "+quer);
  }
}

