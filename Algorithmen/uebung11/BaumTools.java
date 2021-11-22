/******************************  BaumTools.java  ******************************/

import AlgoTools.IO;

/**
 * Utility-Klasse mit einigen Algorithemn fuer Baeume
 */
public class BaumTools {

   /**
	* Druckt einen Baum auf der Konsole ebenenweise aus.
	* Nutzt baumTiefe(Baum), printEbene(Baum,int,int) und spaces(int).
	* @param b der zu druckende Baum
	*/
   public static void printBaum(Baum b) {

	  //Berechne die Tiefe des Baumes
	  int resttiefe = baumTiefe(b);

	  //Gehe die Ebenen des Baumes durch
	  for(int i = 0; i < resttiefe; i++) {
		 //Drucke die Ebene, beruecksichtige Anzahl der Leerzeichen
		 //fuer korrekte Einrueckung
		 printEbene(b, i, spaces(resttiefe - i));
		 IO.println();
		 IO.println();
	  }

   }

   /**
	* Druckt eine Ebene eines Baumes aehnlich der Breitensuche
	* aus. 0 ist dabei die Wurzel. Vor und nach jedem Element
	* werden spaces Leerzeichen eingefuegt.
	* @param b der auszugebende Baum
	* @param ebene die gewuenschte Ebene beginnend bei 0
	* @param spaces Anzahl von Leerzeichen vor und nach jedem Element
	*/
   public static void printEbene(Baum b, int ebene, int spaces) {

	  //Wenn 0 erreicht, drucke Element mit Leerzeichen
	  if(ebene == 0) {

		 IO.print("", spaces);
		 if(b != null) {
			IO.print(b.value());
		 }
		 else { //Wenn Nullzeiger uebergeben wurde, ein Leerzeichen drucken
			IO.print(" ");
		 }
		 IO.print(" ", spaces + 1);

	  } else {

		 //Steige rekursiv ab, betrachte Soehne als Teilbaeume und verringere
		 //dabei die zu druckende Ebene. In endende Aeste wird mit dem Nullzeiger
		 //gelaufen.
		 if(b != null && !b.left().empty()) {
			printEbene(b.left(), ebene - 1, spaces);
		 }
		 else {
			printEbene(null, ebene - 1, spaces);
		 }

		 if(b != null && !b.right().empty()) {
			printEbene(b.right(), ebene - 1, spaces);
		 }
		 else {
			printEbene(null, ebene - 1, spaces);
		 }

	  }

   }

   /**
	* Berechnet die Anzahl an benoetigten Leerzeichen fuer
	* eine korrekte Einrueckung, also 0.5 * (2^(ebene) - 2).
	* @param ebene die Ebene, Blaetter sind Ebene 1, darueber aufsteigend
	* @return Anzahl der Leerzeichen zur Elementtrennung
	*/
   private static int spaces(int ebene) {

	  if(ebene == 1) {
		 return 0;
	  }
	  else {
		 //verdoppele die Leerzeichen gegenueber der Ebene darunter
		 //und fuege ein weiteres Leerzeichen hinzu
		 return 2 * spaces(ebene - 1) + 1;
	  }

   }
   /**
    * Gibt die Tiefe des Baums aus
	* @param b Baum binaerer Baum
	* @return Anzahl der Ebenen des Baums
	*/
   public static int baumTiefe(Baum b){
	  // Gib 0 wieder, falls der Baum leer ist
	  if(b.empty())
		 return 0;
	  // Ueberpruefe, welcher Pfad mehr Ebenen hat und verfolge diesen
	  if(baumTiefe(b.left()) > baumTiefe(b.right()))
		 return 1 + baumTiefe(b.left());
	  else
		 return 1 + baumTiefe(b.right());
   }
   /**
    * Erstelle einen binaeren Baum aus in- und postorder Traversierung
	* @param inorder int[] integer Array der inorder Traversierung
	* @param postorder int[] integer Array der postorder Traversierung
	* @return den erzeugten Baum
	*/
   public static VerweisBaum inorderPostorderBau(int[] inorder,
		 int[] postorder){
	  // Wenn leere Arrays gegeben wurden, gib einen leeren Baum wieder
	  if(inorder.length == 0)
		 return new VerweisBaum();
	  // Die Wurzel ist das letzte Element der postorder Traversierung
	  int wurzel = postorder[postorder.length -1];
	  int[] linksPost, linksIn, rechtsPost, rechtsIn;
	  int i, l, r;
	  // Wenn nur eine Wurzel gegebene wurde(Arrays mit einem Element)
	  // gib einen Baum mit einer Wurzel wieder 
	  if(inorder.length == 1)
		 return new VerweisBaum((Integer)wurzel);
	  // Finde die Wurzel in der inorder Traversierung
	  for(i = 0; inorder[i] != wurzel; i++);
	  // Lege die Laengen der Arrays entsprechend der Position der Wurzel
	  // in der inorder Traversierung fest
	  linksIn = new int[i];
	  linksPost = new int[i];
	  rechtsIn = new int[inorder.length - (i+1)];
	  rechtsPost = new int[inorder.length - (i+1)];
	  // Fuelle die Arrays mit den passenden Elementen
	  for(l = 0; l < i; l++){
		 linksPost[l] = postorder[l];
		 linksIn[l] = inorder[l];
	  }
	  for(r = 0; postorder[i+r] != wurzel; r++){
		 rechtsPost[r] = postorder[i+r];
		 rechtsIn[r] = inorder[i+r+1];
	  }
	  // Gib einen neuen VerweisBaum aus linkem Teilbaum(rekursiv erzeutgt),
	  // Wurzel und rechtem Teilbaum(rekursiv erzeugt) wieder
	  return new VerweisBaum(inorderPostorderBau(linksIn, linksPost), 
			(Integer)wurzel, inorderPostorderBau(rechtsIn, rechtsPost));
   }
   /**
    * Gibt die Anzahl der Knoten des binaeren Baums wieder
	* @param b Baum binaerer Baum
	* @return int Anzahl der Knoten
	*/
   public static int anzahlKnoten(Baum b){
	  if(b.empty())
		 return 0;
	  // Wenn der Baum nicht leer ist
	  else
		 // Summiere Knoten des linken Teilbaums, Knoten des rechten 
		 // Teilbaums und 1
		 return 1 + anzahlKnoten(b.left()) + anzahlKnoten(b.right());
   }
   /**
    * Gibt aus, ob der eingegebenen binaere Baum vollstaendig ist
	* @param b Baum binaerer Baum
	* @return true, falls der vollstaendig ist, sonst false
	*/
   public static boolean istVollstaendig(Baum b){
	  // Ein leerer Baum ist vollstaendig
	  if(b.empty())
		 return true;
	  // Bei einem vollstaendigen Baum hat jeder Weg in einer Ebene die
	  // gleiche Tiefe
	  return (baumTiefe(b.left()) == baumTiefe(b.right())) &&
			 (istVollstaendig(b.left()) && istVollstaendig(b.right()));
  }

}
