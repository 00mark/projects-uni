/******************************  Matrizenmultiplikation.java  *****************/

import AlgoTools.IO;

/**
 * @version 20.11.16
 * 
 * Liest zwei Matrizen ein und gibt das Ergebnis der+
 * Matrizenmultiplikation aus
 *
 * @author  mhiltenkamp
 */

public class Matrizenmultiplikation {

   public static void main(String[] argv) {

	  int[][] matrixA, matrixB, matrixC;
	  int zeilenA, zeilenB, zeilenNrA, zeilenNrB, zeilenNrC, produktNr,
		  spaltenA, spaltenB, spaltenNrA, spaltenNrB, spaltenNrC;

	  // Einlesen der Zeilen- und Spaltenanzahl der ersten Matrix
	  do{
		 zeilenA = IO.readInt("Anzahl der Zeilen der 1. Matrix? >");
		 spaltenA = IO.readInt("Anzahl der Spalten der 1. Matrix? >");
	  }while( zeilenA < 1 || spaltenA < 1 );

	  // Einlesen der Zeilen- und Spaltenanzahl der zweiten Matrix+
	  // dabie muss die Anzahl der Zeilen der zweiten Matrix+
	  // gleich der Anzahl der Spalten der ersten Matrix sein
	  do{
		 zeilenB = IO.readInt("Anzahl der Zeilen der 2. Matrix? >");
		 spaltenB = IO.readInt("Anzahl der Spalten der 2. Matrix? >");
	  }while( zeilenB != spaltenA || spaltenB < 1 );

	  matrixA = new int[zeilenA][spaltenA];
	  matrixB = new int[zeilenB][spaltenB];

	  // Zeilen der ersten Matrix einlesen
	  for( zeilenNrA = 0; zeilenNrA < zeilenA; zeilenNrA++ ){
		 do{
			matrixA[zeilenNrA] = IO.readInts((zeilenNrA+1)+
				  ". Zeile der ersten Matrix? >");
		 }while( matrixA[zeilenNrA].length != spaltenA );
	  } 

	  // Zeilen der zweiten Matrix einlesen
	  for( zeilenNrB = 0; zeilenNrB < zeilenB; zeilenNrB++ ){
		 do{
			matrixB[zeilenNrB] = IO.readInts((zeilenNrB+1)+
				  ". Zeile der zweiten Matrix? >");
		 }while( matrixB[zeilenNrB].length != spaltenB );
	  }

	  // Ergebnismatrix anlegen und mit Nullen initialisieren
	  matrixC = new int[zeilenA][spaltenB];
	  for ( zeilenNrC = 0; zeilenNrC < zeilenA; zeilenNrC++ )
		 for ( spaltenNrC = 0; spaltenNrC < spaltenB; spaltenNrC++ )
			matrixC[zeilenNrC][spaltenNrC] = 0;

	  // Multiplikation durchfuehren
	  for ( zeilenNrC = 0; zeilenNrC < zeilenA; zeilenNrC++ ){
		 for ( spaltenNrC = 0; spaltenNrC < spaltenB; spaltenNrC++ ){
			// die einzelnen Produkte errechnen, summieren und an der+
			// der jeweiligen Stelle in der Ergebnismatrix speichen
			for ( produktNr = 0; produktNr < spaltenA; produktNr++ )
			   matrixC[zeilenNrC][spaltenNrC] += matrixA[zeilenNrC][produktNr] * matrixB[produktNr][spaltenNrC];
			IO.print(matrixC[zeilenNrC][spaltenNrC]+" ", 5);
		 }
		 IO.println();
	  }
   }
}

