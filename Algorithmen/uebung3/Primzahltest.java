/******************************  Primzahltest.java  ***************************/

import AlgoTools.IO;

/**
 * @version 08.11.16
 * 
 * Gibt aus, ob die eingegebene Zahlen eine Primzahl ist. 
 *
 * @author  mhiltenkamp
 */

public class Primzahltest {

     public static void main(String[] argv) {
		    
			int n, z;
			boolean prim = true;
			
			// korrekte Zahl einlesen
			do{
			   n = IO.readInt("Bitte eine positive ganze Zahl eingeben >");
			}while( n <= 0 );
			
			// Sonderfall 1 -> keine Primzahl
			if ( n == 1 )
			   prim = false;
			
			// Sonderfall 2 -> Primzahl ("",da prim=true)
			if ( n == 2 );
			
			// Pruefen, ob nur ungerade Zahlen ueberprueft werden müssen
			else if ( n % 2 != 0 ){
			   // z=3 und z=z+2, da keine geraden Zahlen
			   // z*z<=n, da nur ganze Teiler bis z*z=n moeglich 
			   for ( z = 3; z*z <= n; z = z+2 ){
				  if ( n % z == 0 ){
					 prim = false;
					 break;
				  }
			   }
			} 
			// Sonst gerade Zahl -> keine Primzahl
			else
			   prim = false;

			if ( prim )
			   IO.println(n+" ist eine Primzahl");
			else
			   IO.println(n+" ist keine Primzahl");
	 }
}

