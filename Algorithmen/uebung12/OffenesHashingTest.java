/******************************  OffenesHashingTest.java  *********************/

import AlgoTools.IO;
import java.util.Iterator;

/**
 * Testet die Klasse Firma und die Klasse OffeneHashtabelle.
 */

public class OffenesHashingTest {

  public static void main(String[] argv) {

     Firma a = new Firma("Daimler", 702000000l, 812526315);
     Firma b = new Firma("Daimler", 702000000l, 812526315);
     Firma c = new Firma("Volkswagen", 6835000000l, 115235681);
     Firma d = new Firma("RWE", 3308000000l, 815080634);
     Firma e = new Firma("Siemens", 3589000000l, 129274202);

     //Teste Comparable, equals() und hashCode()
     IO.println("Vergleiche zwei gleiche Firmen mit == :");
     IO.println("A: " + a);
     IO.println("B: " + b);
     if(a == b) {
       IO.println("A == B (sollte nicht passieren!)");
     }
     else {
       IO.println("A != B (sollte passieren)");
     }
     IO.println();

     IO.println("Vergleiche zwei gleiche Firmen mit compareTo() :");
     IO.println("A: " + a);
     IO.println("B: " + b);
     IO.println("a.compareTo(b) liefert (sollte 0 sein): " + a.compareTo(b));
     IO.println("b.compareTo(a) liefert (sollte 0 sein): " + b.compareTo(a));
     IO.println();

     IO.println("Vergleiche zwei unterschiedliche Firmen mit compareTo() :");
     IO.println("A: " + a);
     IO.println("B: " + d);
     IO.println("a.compareTo(b) liefert (sollte <0 sein): " + a.compareTo(d));
     IO.println("b.compareTo(a) liefert (sollte >0 sein): " + d.compareTo(a));
     IO.println();

     IO.println("Vergleiche zwei unterschiedliche Firmen mit equals() :");
     IO.println("A: " + a);
     IO.println("B: " + c);
     if(a.equals(c)) {
       IO.println("a.equals(b) == true (sollte nicht passieren!)");
     }
     else {
       IO.println("a.equals(b) == false (sollte passieren)");
     }
     IO.println();

     IO.println("Vergleiche zwei gleiche Firmen mit equals() :");
     IO.println("A: " + a);
     IO.println("B: " + b);
     if(a.equals(b)) {
       IO.println("a.equals(b) == true (sollte passieren)");
     }
     else {
       IO.println("a.equals(b) == false (sollte nicht passieren!)");
     }
     IO.println();


     //Teste die Hashtabelle
     OffeneHashtabelle h = new OffeneHashtabelle();
     IO.println("Teste offenes Hashing...");
     IO.println("Ist die Hashtabelle nun leer? (sollte JA sein): " + (h.empty() ? "JA" : "NEIN"));
     IO.println("Fuege ein: " + a);
     IO.println("Einfuegen hat geklappt (sollte JA sein): " + (h.insert(a) ? "JA" : "NEIN"));
     IO.println("Fuege ein: " + b);
     IO.println("Einfuegen hat geklappt (sollte NEIN sein, wegen doppeltem Vorkommnis): " + (h.insert(b) ? "JA" : "NEIN"));
     IO.println("Fuege ein: " + c);
     IO.println("Einfuegen hat geklappt (sollte JA sein): " + (h.insert(c) ? "JA" : "NEIN"));
     IO.println("Fuege ein: " + d);
     IO.println("Einfuegen hat geklappt (sollte JA sein): " + (h.insert(d) ? "JA" : "NEIN"));
     IO.println("Pruefe ob " + c + " enthalten ist (sollte JA sein): " + (h.lookup(c) == null ? "NEIN" : "JA"));
     IO.println("Versuche " + e + " zu loeschen (sollte NEIN sein): " + (h.delete(e) ? "JA" : "NEIN"));
     IO.println("Versuche " + a + " zu loeschen (sollte JA sein): " + (h.delete(a) ? "JA" : "NEIN"));
     IO.println("Ist die Hashtabelle nun leer? (sollte NEIN sein): " + (h.empty() ? "JA" : "NEIN"));
     IO.println();
  }
}

