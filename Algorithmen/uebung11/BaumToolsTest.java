/******************************  BaumToolsTest.java  **************************/

import AlgoTools.IO;

/**
 * Test fuer die BaumTools
 * Benoetigt die Klasse Traverse und VerweisBaum aus der Vorlesung
 */

public class BaumToolsTest {

  public static void main(String[] argv) {

    //Aufgabe 2
    IO.println("Erzeuge Baum aus Traversierungen: ");
    IO.println("Inorder: 1649238, Postorder: 6194283");

    int[] in = {1,6,4,9,2,3,8};
    int[] post = {6,1,9,4,2,8,3};

    VerweisBaum erzeugt = BaumTools.inorderPostorderBau(in, post);
    
    IO.println("Erneute Traversierung fuehrt zu: ");
    IO.print("Inorder: ");
    Traverse.inorder(erzeugt);
    IO.print(" Postorder: ");
    Traverse.postorder(erzeugt);
    IO.println();
    IO.println();

    //Aufgabe 3
    //Erzeuge Baum

    VerweisBaum a = new VerweisBaum(new Character('A'));
    VerweisBaum b = new VerweisBaum(new Character('B'));
    VerweisBaum m = new VerweisBaum(a, new Character('*'), b);
    VerweisBaum f = new VerweisBaum(new Character('F'));
    VerweisBaum p = new VerweisBaum(f, new Character('+'), m);
    VerweisBaum x = new VerweisBaum(new Character('X'));
    VerweisBaum y = new VerweisBaum(new Character('Y'));
    VerweisBaum n = new VerweisBaum(x, new Character('-'), y);
    VerweisBaum d = new VerweisBaum(p, new Character('/'), n);

    BaumTools.printBaum(d);

    IO.println("Anzahl Knoten laut Methode (sollte 9 sein): " + BaumTools.anzahlKnoten(d));
    IO.println();

    IO.println("Baumtiefe laut Methode (sollte 4 sein): " + BaumTools.baumTiefe(d));
    IO.println();

    IO.println("istVollstaendig (sollte false sein): " + BaumTools.istVollstaendig(d));

    VerweisBaum a2 = new VerweisBaum(new Character('A'));
    VerweisBaum b2 = new VerweisBaum(new Character('B'));
    VerweisBaum m2 = new VerweisBaum(a2, new Character('*'), b2);

    BaumTools.printBaum(m2);

    IO.println("istVollstaendig (sollte true sein): " + BaumTools.istVollstaendig(m2));

    IO.println();

  }
}

