/******************************  Labyrinth.java  ******************************/

/**
 * Liest ein Labyrinth ein und berechnet mittels einem Backtracking
 * Algorithmus einen Weg durch dieses.
 * 
 * @author Christian Heiden
 * @author Lukas Kalbertodt
 * @author Mark Hiltenkamp
 */

import AlgoTools.IO;

public class Labyrinth {

   /** Freies Feld */
   private static final char FREI  = ' ';
   /** Mauer */
   private static final char MAUER = '#';
   /** Startfeld */
   private static final char START = 'S';
   /** Zielfeld */
   private static final char ZIEL  = 'Z';
   /** Besuchtes Feld */
   private static final char PFAD  = '-';

   /**
	* Findet einen Weg durch das Labyrinth.
	* Findet den Startpunkt und ruft findeZielRek mit dem Startpunkt auf.
	* 
	* @param lab Das Labyrinth
	* @throws RuntimeException Wenn kein Startpunkt gefunden wurde
	* @return Ob ein Weg gefunden wurde
	*/
   private static boolean findeZiel(char[][] lab) {
	  for(int i = 0; i < lab.length; i++){
		 for(int j = 0; j < lab[i].length; j++){
			// Startfeld gefunden?
			if(lab[i][j] == START)
			   return findeZielRek(lab, j, i);
		 }
	  }
	  // Kein Startfeld gefunden
	  throw new RuntimeException("Kein Startpunkt gefunden");
   }


   /**
	* Findet rekursiv einen Weg durch das Labyrinth
	* 
	* @param lab Das Labyrinth
	* @param x X-Koordinate des Punktes bei dem man steht
	* @param y Y-Koordinate des Punktes bei dem man steht
	* @return Ob ein Weg gefunden wurde
	*/
   private static boolean findeZielRek(char[][] lab, int x, int y) {
	  // Wenn man sich auf dem Zielfeld befindet wurde ein Weg gefunden
	  if(lab[y][x] == ZIEL){
		 return true;
	  }
	  else if(lab[y][x] == START)
		 ;
	  // Wenn man sich auf einem freien Feld befindet, wird dies als+
	  // besucht markiert
	  else if(lab[y][x] == FREI)
		 lab[y][x] = PFAD;
	  // Wenn man sich auf einer Mauer oder einem Pfad befindet,+
	  // ist der aktuelle Weg fehlgeschlagen
	  else
		 return false;

	  // Pruefe erst, ob man nach unten, oben, links oder rechts gehen kann+
	  // (in dieser Reihenfolge). Dabei wird die naechste Richtung erst
	  // geprueft, wenn die vorherige fehlgeschlagen ist. Die entsprechenden+
	  // Wege werden dann verfolgt bis man am Ziel ist, oder sich+
	  // herausstellt, dass das jeweilige Labyrinth nich loesbar ist
	  if(!findeZielRek(lab, x, y-1) && !findeZielRek(lab, x, y+1) &&
			!findeZielRek(lab, x-1, y) && !findeZielRek(lab, x+1, y)){
		 // Wenn kein Weg moeglich ist, ist der aktuelle Weg fehlgeschlagen+
		 // und der Pfad wird wieder frei gemacht
		 if(lab[y][x] == PFAD)
			lab[y][x] = FREI;
		 return false;
	  }
	  return true;
   }



   /**
	* Zeigt das uebergebene Labyrinth auf dem Terminal an
	* 
	* @param lab Das anzuzeigende Labyrinth
	*/
   private static void druckeLabyrinth(char[][] lab) {
	  // Fuer alle Zeilen des Labyrinths
	  for(int i = 0; i < lab.length; i++){
		 IO.print("\t");
		 // Fuer alle Spalten des Labyrinths
		 for(int j = 0; j < lab[i].length; j++)
			// Gebe das jeweilige Element aus 
			IO.print(lab[i][j]);
		 IO.println();
	  }
   }


   /**
	* Liest das Labyrinth vom Terminal ein
	* @return Das eingelesene Labyrinth
	*/
   private static char[][] leseEingabe() {
	  int breite = 0, hoehe = 0;

	  // Lese Breite ein
	  do {
		 breite = IO.readInt("Breite des Labyrinths: ");
	  } while(breite <= 0);

	  // Lese Hoehe ein
	  do {
		 hoehe = IO.readInt("Hoehe des Labyrinths: ");
	  } while(hoehe <= 0);		

	  // Lege Array an, welches ausgegeben wird
	  char[][] out = new char[hoehe][breite];

	  // Fuer jede Zeile
	  for(int i = 0; i < hoehe; i++) {
		 char[] zeile;

		 // lies Zeile mit korrekter Anzahl von Buchstaben ein
		 do {
			zeile = IO.readChars("Zeile " + i + " des Labyrinths: ");
		 } while(zeile.length != breite);

		 out[i] = zeile;
	  }
	  return out;
   }

   /**
	* Liest Labyrinth ein und findet einen Weg von S nach Z
	*/
   public static void main(String[]args){

	  // Lese Labyrinth ein
	  char[][] lab = leseEingabe();

	  // Gebe aus
	  IO.println();
	  IO.println("----- Original-Labyrinth: -----");
	  druckeLabyrinth(lab);
	  IO.println();

	  boolean gefunden = findeZiel(lab);

	  if(gefunden) {
		 // Gebe aus
		 IO.println("-----   Gefundener Weg:   -----");
		 druckeLabyrinth(lab);
	  } else {
		 IO.println("Kein Weg zum Ziel gefunden!");
	  }
   }
}
