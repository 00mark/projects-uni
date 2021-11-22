/******************************  ElfSort.java  ********************************/

import AlgoTools.IO;

/**
 * @version 31.12.16
 *  
 * @author  mhiltenkamp
 */

public class ElfSort {
   /**
    * Prueft, ob das eingegebenen Array sortiert werden kann
	* @param pakete Das Array mit den einzelnen Paketnummern
	* @param ziffer Die Stelle, nach der sortiert werden soll.
			   Muss = 4 sein, da es der erste Aufruf ist
	* @throws RuntimeException Bei Ziffer =/= 4 oder einer Paketnummer
			   groesser 99999, bzw. kleiner 0
	* @return Die Methode, die das Array sortiert
	*/
   public static int[] sort(int[] pakete, int ziffer){
	  if(ziffer != 4)
		 throw new RuntimeException("Ungueltige Ziffer");
	  for(int i = 0; i < pakete.length; i++)
		 if(pakete[i] < 0 || pakete[i] > 99999)
			throw new RuntimeException("Unguelige Paketnummer");
	  return sortieren(pakete, ziffer);
   }
   /**
    * Sortiert das eingebenen Array 
	* @param pakete Das Array mit den einzelnen Paketnummern
	* @param ziffer Die Stelle, nach der sortiert werden soll
	* @return Das sortierte Array
	*/
   private static int[] sortieren(int[] pakete, int ziffer){ 
	  // Bei Ziffer < 0 wurden alle Stellen der Pakete abgehandelt
	  if(ziffer < 0)
		 return pakete;
	  int stelle = 0;
	  // Das neue Array Wird angelegt und die Pakete werden auf die+
	  // einzelnen Stellen aufgeteilt
	  int[][] a = new int[10][1];
	  for(int i = 0; i < pakete.length ; i++){
		 // Zehntausenderstelle
		 if(ziffer == 4)
			stelle = pakete[i] / 10000;
		 // Tausenderstelle
		 else if(ziffer == 3)
			stelle = (pakete[i] / 1000) % 10;
		 // Hunderterstelle
		 else if(ziffer == 2)
			stelle = (pakete[i] / 100) % 10;
		 // Zehnerstelle
		 else if(ziffer == 1)
			stelle = (pakete[i] / 10) % 10;
		 // Einerstelle
		 else if(ziffer == 0)
			stelle = pakete[i] % 10;
		 // Das Array an der jeweiligen Stelle wird um 1 erweitert und+
		 // um das aktuelle Paket erweitert
		 a[stelle] = legeInFach(a[stelle], pakete[i]);		 
	  }
	  int[] b = new int[pakete.length];
	  int index = 0;
	  for(int i = 0; i < 10; i++){
		 // Die Stellen werden sortiert
		 int[] c = sortieren(a[i], ziffer-1);
		 for(int j = 1; j < c.length; j++){
			// Und in das neue Array geschrieben
			b[index] = c[j];
			index++;
		 }
	  }
	  return b;
   }
   /**
    * Nimmt ein Array und erweitert es um ein Element
    * @param ablagefach Das Array, das erweitert werden soll
	* @param paketnummer Das Element, um das das Array erweiter werden soll
    * @return Das neue Array
	*/
   private static int[] legeInFach(int[] ablagefach, int paketnr){
	  // Lege ein neues, um ein Element groesseres, Array an
	  int[] a = new int[ablagefach.length+1];
	  int i;
	  for(i=0; i < ablagefach.length; i++)
		 // Schreibe die Elemente des alten Arrays in das Neue
		 a[i] = ablagefach[i];
	  // Schreibe das neue Element in das Array
	  a[i] = paketnr;
	  return a;
   }
   /**
    * Gibt ein integer Array aus
	* @param a Das Array
	*/
   public static void arrayAusgeben(int[] a){
	  for(int i = 0; i < a.length; i++)
		 IO.print(a[i]+"\t");
	  IO.println();
   }
}
