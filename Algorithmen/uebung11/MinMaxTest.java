/**************************** MinMaxTest.java *********************************/

import AlgoTools.IO;

/**
 * Testet die Implementierung der MinMaxListe
 */
public class MinMaxTest {

  public static void main (String [] argv) {

    MinMaxListe l = new MinMaxListe();

    int[] testWerte = {23, 18, 42, 41, 1, 27};

    // Alle TestWerte nacheinander einfügen und immer Min und Max ausgeben
    for(int i = 0; i < testWerte.length; i++) {
      l.insert(new Integer(testWerte[i]));
      l.advance();

      IO.println("Es wurde " + testWerte[i] + " eingefügt!");
      IO.println("  Min: " + l.getMin());
      IO.println("  Max: " + l.getMax());
    }

    // Ausgabe zur besseren Lesbarkeit
    IO.println("-----------------------------------------------------");

    // Alle TestWerte nacheinander aus der Liste löschen und wieder ausgeben
    for(int i = 0; i < testWerte.length; i++) {
      Integer wert = new Integer(testWerte[i]);

      // In Liste weitergehen, bis wir unsere Zahl gefunden haben
      l.reset();
      while(!wert.equals(l.elem())) {
        l.advance();
        if(l.endpos()) {
          l.reset();
        }
      }

      // Zahl löschen und Ausgabe der neuen Werte
      Object entfernt = l.elem();
      l.delete();

      IO.println("Es wurde " + entfernt + " gelöscht!");
      IO.println("  Min: " + l.getMin());
      IO.println("  Max: " + l.getMax());
    }

  }
}
