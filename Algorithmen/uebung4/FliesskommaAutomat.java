/******************************  FliesskommaAutomat.java  *********************/

import AlgoTools.IO;

/**
 * @version 19.11.16
 * 
 * Liest eine Zeichenfolge ein und gibt aus, ob diese eine gültige 
 * Fließkommazahl darstellt
 *
 * @author  mhiltenkamp
 */

public class FliesskommaAutomat {

   public static void main(String[] argv) {
	  // Anlegung des Automaten
	  int[][] automat = {{1, 3, 6}, {2, 3, 6}, {2, 2, 2}, {2, 3, 5},
		 {2, 4, 6}, {2, 4, 6}, {6, 6, 6}};

	  int zustand = 0;
	  // das erste Zeichen einlesen
	  char zeichen = IO.readChar("Bitte ein Zeichen eingeben >");

	  // mit Hilfe des Automaten in den neuen Zustand wechseln
	  while (zeichen != '\n'){
		 if( zeichen == 'V' || zeichen == 'v' )
			zustand = automat[zustand][0];
		 else if( zeichen == 'Z' || zeichen == 'z' )
			zustand = automat[zustand][1];
		 else if( zeichen == 'K' || zeichen == 'k' )
			zustand = automat[zustand][2];
		 else
			IO.println("ungueltige Eingabe");
		 // neues Zeichen einlesen
		 zeichen = IO.readChar("Bitte ein Zeichen eingeben >");
	  }

	  if ( zustand == 3 || zustand == 4 )
		 IO.println("Die Eingabe ist eine gueltige Fliesskommazahl");
	  else
		 IO.println("Die Eingabe ist keine gueltige Fliesskommazahl");
   }
}

