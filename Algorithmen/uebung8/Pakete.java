/******************************  Pakete.java  *********************************/

import AlgoTools.IO;

/**
 * @version 31.12.16
 * Liest Geschenknummern ein und sortiert diese
 * @author  mhiltenkamp
 */

public class Pakete {

   public static void main(String[] argv) {
	  int[] a;
	  boolean korrekt;
	  do{
		 korrekt = true;
		 a = IO.readInts("Bitte die Geschenknummern eingeben\n> ");
		 for(int i = 0; i < a.length; i++){
			// Ist die Geschenknummer gueltig?
			if(a[i] < 0 || a[i] > 99999){
			   korrekt = false;
			   break;
			}
		 }
	  }while(!korrekt);
	  IO.println("Das vorherige Array :");
	  ElfSort.arrayAusgeben(a);
	  IO.println("Das sortierte Array :");
	  ElfSort.arrayAusgeben(ElfSort.sort(a, 4));
   }
}

