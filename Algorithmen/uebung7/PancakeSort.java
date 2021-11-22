/******************************  PancakeSort.java  ****************************/

import AlgoTools.IO;

/**
 * @version 13.12.16
 *  
 * @author  mhiltenkamp
 */

public class PancakeSort {
   /**
	* Dreht die Reihenfolge der ersten <tt>count</tt> Element in
	* <tt>array</tt> um.
	*
	* @param array das zu sortierende Array
	* @param count Anzahl zu flippender Elemente
	*
	* @throws RuntimeException wenn <tt>count</tt> > <tt>array.length</tt>
	*/
   public static void flip (int[] array, int count) {
	  if (count > array.length)
		 throw new RuntimeException ("Flippen dieser Anzahl nicht moeglich");
	  int i, j;
	  // Elemente vertauschen
	  for (i = count-1, j = 0; i > j; i--, j++){
		 int tmp = array[j];
		 array[j] = array[i];
		 array[i] = tmp;
	  }
   }

   /**
	* Gibt ein Array auf dem Terminal aus.
	*
	* Beispiel: Ein Array mit den Zahlen 1, 2 und 3 als Inhalt
	* wird ausgegeben als: 1 2 3
	*
	* @param array Das auszugebene Array
	*/
   public static void printArray (int[] array) {
	  for (int i = 0; i < array.length; i++)
		 IO.print(array[i], 5);
	  IO.println();
   }

   /**
	* Sortiert das gegebene <tt>array</tt> mit dem PancakeSort Verfahren.
	*
	* @param array zu sortierendes Array
	*/
   public static void sort (int[] array) {
	  int max = 0;
	  int i, j;
	  for (i = 0; i < array.length-1; i++){
		 for (j = 1; j+i < array.length; j++){
			// Ueberpruefen, ob ein groesseres Element gefunden wurde
			if (array[j] > array[max])
			   max = j;
		 }
		 // Erst das groesste Element an die erste Stelle bringen
		 flip (array, max+1);
		 // Dann das groesste Element an die letzte Stelle bringen
		 flip (array, j);
		 max = 0;
	  }
   }

   public static void main (String[] args) {

	  int[] a;

	  do{
		 a = IO.readInts ("Elemente des Arrays?\n> ");
	  }while (a.length < 1);
	  
	  IO.print("Aus\t");
	  printArray (a);
	  IO.print("Wird\t");
	  sort (a);
	  printArray (a);

   }
}
