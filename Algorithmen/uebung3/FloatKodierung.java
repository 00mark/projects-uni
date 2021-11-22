/******************************  FloatKodierung.java  *************************/

import AlgoTools.IO;

/**
 * @version 13.11.16
 *  
 * Gibt die Float-Kodierung der eingegebenen Zahl aus
 *
 * @author  mhiltenkamp
 */

public class FloatKodierung {

     public static void main(String[] argv) {
		    float zahl, m = 1, mReduziert = 0;
			int e = 0;
			boolean negativ = false;
			
			// korrekte Zahl einlesen
			do{
			   zahl = IO.readFloat("Bitte eine Gleitkommazahl eingeben >");
			}while ( -1 < zahl && zahl < 1 );
			
			// Bei negativer Zahl Betrag als Zahl abspeichern und negativ+
			// auf wahr setzen
			if ( zahl < 0 ){
			   zahl = -zahl;
			   negativ = true;
			}
			
			// Mantisse und Exponenten errechnen, indem die moeglichen+
			// Werte der 2er-Potenz abgegangen werden und geprüft wird,+
			// ob die Zahl dividiert durch den Wert der 2er-Potenz+
			// zwischen 1 und 2 liegt
			for ( float eWert = 1; eWert <= zahl; eWert = eWert*2 ){
			   if ( zahl / eWert < 2 && zahl / eWert >= 1 ){
				  m = zahl / eWert;
				  mReduziert = m - 1;
				  break;
				  }
			   e++;
			}
			
			// Grundgeruest der Auflistungen ausgeben
			IO.println("\tVorzeichen\t|\tExponent\t|\tReduzierte Mantisse");
			IO.println("\t    +-+     \t|      +--------+\t|    +-----------------------+");
			
			// Wenn die Zahl negativ ist eine 1 ausgeben, sonst eine 0
			if ( negativ )
			   IO.print("\t    |1|\t\t|");
			else
			   IO.print("\t    |0|\t\t|");
			
			// Den Exponenten als Bitfolge darstellen. Dazu werden die+
			// 2er-Potenzen, angefangen mit 64, rueckwaerts abgegangen
			IO.print("      |0");
			for ( int potenz = 64; potenz >= 1; potenz = potenz/2){
			   if ( e - potenz >= 0 ){
				  IO.print(1);
				  e = e - potenz;
			   }
			   else
				  IO.print(0);
			}
			IO.print("|\t|    |");
			
			// Die reduzierte Mantisse als Bitfolge darstellen. dazu+
			// werden die 23 Stellen der Bitfolge abgegangen
			for ( int stelle = 0; stelle < 23; stelle++ ){
			   mReduziert = mReduziert * 2;
			   if ( mReduziert >= 1 ){
				  IO.print(1);
				  mReduziert--;
			   }
			   else
				  IO.print(0);
			}
			IO.println("|");
			IO.println("\t    +-+     \t|      +--------+\t|    +-----------------------+");
     }	
}
