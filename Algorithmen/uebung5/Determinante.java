/******************************  Determinante.java  ***************************/

import AlgoTools.IO;

/**
 * @version 24.11.16
 * 
 * Rechnet die Determinante einer Matrix aus. Gibt die Matrix und ihre 
 * Determinante aus
 *
 * @author  mhiltenkamp
 */

public class Determinante {
   
   /**
    * Gibt eine zweidimensionalen Integer Matrix aus
	* @param matrix int zweidimensionale Matrix
	*/
   public static void matrixAusgeben( int[][] matrix ){
	  for( int i = 0; i < matrix.length; i++ ){
		 for( int j = 0; j < matrix[i].length; j++ )
			IO.print(matrix[i][j], 4);
		 IO.println();
	  }
   }

   /**
    * Verkuerzt die eingegebene Matrix um die Zeile zeile und Spalte spalte
	* @param matrix int zweidimensionale Matrix
	* @param  zeile int Zeile, die gekuerzt werden soll, groeßer -1
				    kleiner Zeilenanzahl der Matrix
	* @param  spalte int Spalte, die gekuerzt werden soll, groeßer -1
				    kleiner Spaltenanzahl Matrix
    * @return verkuerzte Matrix
    */
   public static int[][] matrix2dKuerzen( int[][] matrix, int zeile, int spalte ){
	  int[][] matrixNew = new int[matrix.length-1][matrix[0].length-1];
	  int i,j,n,m = 0;
	  for( i = 0,n = 0; i < matrix.length; i++, n++ ){
		 // Soll die Zeile ausgelassen werden?
		 if( i == zeile)
			i++;
		 for( j = 0,m = 0; i < matrix.length &&
			   j < matrix[i].length ; j++, m++ ){
			// Soll die Spalte ausgelassen werden?
			if( j == spalte )
			   m-- ;
			else
			   matrixNew[n][m] = matrix[i][j];
		 }
	  }
	  return matrixNew;
   }
   
   /** 
    * Testet, ob die Determinante der eingegebenen Matrix errechnet
    * werden kann
    * @param matrix int zweidimensionale Matrix
	* @return Methode zur Berechnung der Determinanten
	* @throws RuntimeException wenn matrix nicht quadratisch ist
    */
   public static int det( int[][] matrix ){
	  for( int i = 0; i < matrix.length; i++ ){
		 if( matrix.length != matrix[i].length )
			throw new RuntimeException("Matrix muss quadratisch sein!");
	  }
	  return detRek( matrix );
   }

   /**
    * Errechnet die Determinante der eingegebenen Matrix
	* @param matrix int zweidimensionale Matrix
	* @return Determinante der Matrix
	*/
   private static int detRek( int[][] matrix ){
	  int deter = 0;
	  // Wenn matrix 1x1 ist
	  if( matrix.length == 1 )
		 return matrix[0][0];
	  else{
		 // Wenn matrix mindestens 2x2 ist
		 for( int j = 0; j < matrix.length; j++ )
			deter += (int)RekursiveMethoden.potenz( -1.0, j ) *
			   matrix[0][j]*detRek( matrix2dKuerzen(matrix, 0, j) );
	  }
	  return deter;
   }

   public static void main(String[] argv) {

	  int zeilen, spalten;
	  int[][] matrix;
	  
	  // Laenge der Matrix einlesen
	  do{
		 zeilen = spalten = IO.readInt("Laenge der Matrix?\n>");
	  }while( zeilen < 1 );
	  
	  matrix = new int[zeilen][spalten];
	  for ( int i = 0; i < zeilen; i++ ){
		 do{
			matrix[i] = IO.readInts(i+1+". Zeile der Matrix?\n>");
		 // Matrix quadratisch?
		 }while( matrix[i].length != spalten );
	  }

	  IO.println("\nDie Matrix :");
	  matrixAusgeben( matrix );
	  IO.println("Hat die Determinante :\n\t"+det( matrix ));
   }
}

