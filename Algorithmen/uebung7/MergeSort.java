/******************************  MergeSort.java  ******************************/

import AlgoTools.IO;

/**
 * @version 09.12.16
 * 
 * Liest ein int Array ein und sortiert es mithilfe des MergeSort Algorithmus
 *
 * @author  mhiltenkamp
 */

public class MergeSort {

   private static int schritte = 0;

   /**
	* Gibt das Array aus
	* @param a 1D integer Array
	*/
   public static void arrayAusgeben (int[]a){
	  for (int i = 0; i < a.length; i++)
		 IO.print(a[i]+"\t");
	  IO.println();
   }

   /**
	* Verbindet zwei sortierte Arrays zu einem sortierten Array
	* @param a erstes 1D integer Array
	* @param b zweites 1D integer Array
	* @return das neue Array
	*/
   public static int[] merge (int[]a, int[]b) {   // mischt a und b
	  // liefert Ergebnis zurueck

	  int i=0, j=0, k=0;                           // Laufindizes
	  int[] c = new int[a.length + b.length];      // Platz fuer Folge c besorgen

	  while ((i<a.length) && (j<b.length)) {       // mischen, bis ein Array leer
		 schritte++;
		 if (a[i] < b[j])                           // jeweils das kleinere Element
			c[k++] = a[i++];                       // wird nach c uebernommen
		 else
			c[k++] = b[j++];
	  }

	  while (i<a.length){ c[k++] = a[i++];         // ggf.: Rest von Folge a
		 schritte++;
	  }
	  while (j<b.length){ c[k++] = b[j++];         // ggf.: Rest von Folge b
		 schritte++;
	  }
	  return c;                                    // Ergebnis abliefern
   }

   /**
	* Prueft ob ein Array sortiert ist
	* @param a 1D integer Array
	* @return ob das Array sortiert ist
	*/
   public static boolean sortiert (int[] a){
	  boolean sort = true;
	  for (int i = 0; i < a.length-1; i++){
		 // Nicht sortiert, falls kleineres Element auf groesseres folgt
		 if (a[i] > a[i+1]){
			sort = false;
			return sort;
		 }
	  }
	  return sort;
   }

   /**
	* Teilt das Array in zwei Haelften auf und fuehrt den MergeSort
	* Algorithmus durch
	* @param a 1D integer Array
	* @return das sortierte Array
	*/
   public static int[] sortRekursiv (int[] a){
	  /*// Gibt das Array wieder, falls es sortiert ist
	  if (sortiert (a))*/

	  // Gibt das Array wieder, falls es hoechstens ein Element besitzt
	  if (a.length < 2)
		 return a;

	  else{
		 // Teilt das Array in zwei Haelften auf
		 // Erste Haelfte groesser bei ungerader Anzahl von Elementen
		 int[] b = new int[(a.length+1)/2];
		 int[] c = new int[a.length/2];
		 for (int i = 0; i < a.length; i++){
			if (i < (a.length+1)/2)
			   b[i] = a[i];
			else
			   c[i - (a.length+1)/2] = a[i];
		 }
		 // Sortiert die Haelften und verbindt sie zu einem sortierten Array
		 return merge (sortRekursiv (b), sortRekursiv (c));
	  }
   }

   public static void main(String[] argv) {

	  int[] a;

	  do{
		 a = IO.readInts("Bitte eine Zahlenfolge eingeben\n> ");
	  }while (a.length < 1);

	  IO.print ("Von\t");
	  // Gibt das Array aus
	  arrayAusgeben (a);
	  IO.print ("Nach\t");
	  // Gibt das sortierte Array aus
	  arrayAusgeben (sortRekursiv (a));
	  IO.println("In "+schritte+" Schritten");
   }
}
