/******************************  BuchDaten.java  ****************************/

import AlgoTools.IO;

/**
 * @version 31.12.16
 * 
 * Einfacher Algorithmus zur Erfassung von Buchdaten mit Hilfe von
 * char-Arrays
 * 
 * @author	mhiltenkamp
 */

public class BuchDaten{

   /**
	* Erzeugt Buchdaten-Array aus uebergebenen Attributen und
	* haengt diese der als char[][][] Array uebergebenen Liste an.
	*
	* @param titel Vorname des anzulegenden Buchdatums
	* @param autor Nachname des anzulegenden Buchdatums
	* @param erscheinungsjahr Alter des anzulegenden Buchdatums
	* @param liste Array mit bereits vorhandenen Buchdaten
	* @return Kopie der Buchdatenliste ergaenzt um das neue Datum
	* @throws RuntimeException falls erscheinungsjahr kleiner 0 oder groesser 2012
	*/
   public static char[][][] addBuch(char[] titel, char[] autor,
		 int erscheinungsjahr, char[][][] liste){
	  if(erscheinungsjahr < 0 || erscheinungsjahr > 2012)
		 throw new RuntimeException("Ungueltiges Erscheinungsjahr");
	  // Neues Array anlegen und Elemente kopieren
	  char[][][] buch = new char[liste.length+1][3][4];
	  for(int i = 0; i < liste.length; i++)
		 for(int j = 0; j < liste[i].length; j++)
			buch[i][j] = liste[i][j];
	  // Neue Elemente in das Array schreiben
	  buch[liste.length][0] = titel;
	  buch[liste.length][1] = autor;
	  for(int i = 0; i < 4; i++){
		 switch(erscheinungsjahr % 10){
			case 0 : buch[liste.length][2][3-i] = '0'; break;
			case 1 : buch[liste.length][2][3-i] = '1'; break;
			case 2 : buch[liste.length][2][3-i] = '2'; break;
			case 3 : buch[liste.length][2][3-i] = '3'; break;
			case 4 : buch[liste.length][2][3-i] = '4'; break;
			case 5 : buch[liste.length][2][3-i] = '5'; break;
			case 6 : buch[liste.length][2][3-i] = '6'; break;
			case 7 : buch[liste.length][2][3-i] = '7'; break;
			case 8 : buch[liste.length][2][3-i] = '8'; break;
			case 9 : buch[liste.length][2][3-i] = '9'; break;
		 }
		 erscheinungsjahr = erscheinungsjahr / 10;
	  }
	  return buch;
   }

   /**
	* Liesst iteriert Buchendaten ein und gibt die Daten anschliessend
	* auf der Konsole aus.
	*/
   public static void main(String[] argv) {

	  // Diese Methode soll NICHT geändert werden.

	  char[][][] liste = new char[0][][];

	  do{

		 IO.println("Bitte geben Sie Buchdaten ein.");
		 char[] titel=IO.readChars("Bitte den Titel eingeben: ");
		 char[] autor=IO.readChars("Bitte den Autor eingeben: ");
		 int erscheinungsjahr=IO.readInt("Bitte das Erscheinungsjahr eingeben: ");

		 liste=addBuch(titel,autor,erscheinungsjahr,liste);

	  }while(IO.readInt("Moechten Sie weitere Buecher eingeben?"
			   +" Abbruch mit 0: ")!=0);


	  for(int i=0; i<liste.length; i++){
		 IO.println("Buch Nr. "+(i+1));
		 IO.println("Titel: ");
		 for(int j=0; j<liste[i][0].length; j++){
			IO.print(liste[i][0][j]);
		 }
		 IO.println();
		 IO.println("Autor: ");
		 for(int j=0; j<liste[i][1].length; j++){
			IO.print(liste[i][1][j]);
		 }
		 IO.println();
		 IO.println("Erscheinungsjahr: ");
		 for(int j=0; j<liste[i][2].length; j++){
			IO.print(liste[i][2][j]);
		 }
		 IO.println();
	  }
   }
}
