/******************************  Rule90.java  *********************************/

import AlgoTools.IO;

/**
 * @version 16.11.16
 * 
 * Simuliert den zellulären Automaten "Rule 90",
 * liest ein System und eine Anzahl von Schritten ein
 * gibt die Systeme der einzelnen Zeitschritte aus
 *
 * @author  mhiltenkamp
 */

public class Rule90 {

   public static void main(String[] argv) {

	  int [] system;
	  int schritte;
	  boolean [] systemBool, systemBoolNext;
	  boolean korrekt;

	  // korrektes System einlesen
	  do{
		 korrekt = true;
		 system = IO.readInts("System? (0=tot, >=1=lebendig) >");
		 for( int i = 0; i < system.length; i++ ){
			if ( system[i] < 0 ){
			   korrekt = false;
			   break;
			}
		 }
	  }while( !korrekt || system.length == 0 ); 

	  // korrekte Anzahl der Schritte einlesen
	  do{
		 schritte = IO.readInt("Anzahl der Schritte? >");
	  }while( schritte < 0 ); 

	  // Boolean-Arrays mit Hilfe des Integer-Arrays anlegen+
	  // 2, da eins für den aktuellen Status, eins für den nächsten
	  systemBool = new boolean[system.length];
	  systemBoolNext = new boolean[system.length];
	  for( int i = 0; i < systemBool.length; i++ ){
		 // false entspricht "tot"
		 if( system[i] == 0 ){
			systemBool[i] = false;
			systemBoolNext[i] = false;
		 }
		 // true entspricht "lebendig"
		 else{
			systemBool[i] = true;
			systemBoolNext[i] = true;
		 }
	  }
	  // jeweils den aktuellen Status für die Anzahl der eingegeben+
	  // Schritte ausgeben
	  for ( int i = 0; i <= schritte; i++ ){
		 IO.print("Zeitpunkt ");
		 IO.print(i, 3);
		 IO.print("\t||");
		 for ( int n = 0; n < system.length; n++){
			if ( systemBoolNext[n] )
			   IO.print(" @ |");
			else
			   IO.print(" . |");
			systemBool[n] = systemBoolNext[n];
		 }
		 // Werte des naechsten Zustands errechnen
		 for ( int n = 0; n < system.length; n++ ){
			// wenn der vorherige Wert true (=lebendig) ist und der+
			// naechste Wert false (=tot) ist (oder umgekehrt), dann+
			// wird der aktuelle Wert des naechsten Zustands+
			// auf true (=lebendig) gesetzt
			if (( systemBool[(system.length + (n -1)) % system.length] 
					 && !systemBool[(n+1) % system.length] )||( 
					 !systemBool[(system.length + (n -1)) % system.length] 
					 && systemBool[(n+1) % system.length] ))
			   systemBoolNext[n] = true;
			// sonst auf false (=tot)
			else
			   systemBoolNext[n] = false;
		 }
		 IO.println();
	  }
   }
}

