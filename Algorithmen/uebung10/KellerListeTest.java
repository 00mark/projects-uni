import AlgoTools.IO;

/**
 * Testet die Klasse KellerListe
 */
public class KellerListeTest {

  public static void main(String[] args){
    Liste kl = new KellerListe();
    Liste l = new VerweisListe();

    IO.println("Fuege drei Elemente ein, resette Liste und gebe aus.");
    kl.insert(3);
    l.insert(3);

    kl.insert(2);
    l.insert(2);

    kl.insert(1);
    l.insert(1);

    vergleichsAusgabe(l, kl);

    IO.println("Advance ein mal und loesche dann.");
    l.reset();
    l.advance();
    l.delete();

    kl.reset();
    kl.advance();
    kl.delete();

    vergleichsAusgabe(l, kl);

    IO.println("Loesche restliche Elemente und gebe aus.");
    l.reset();
    l.delete();
    l.delete();

    kl.reset();
    kl.delete();
    kl.delete();

    vergleichsAusgabe(l, kl);

    IO.println("empty() liefert: " + (kl.empty() ? "TRUE" : "FALSE") + "(sollte TRUE sein)");
  }

  /**
   * Hilfsmethode um zwei Listen optisch per Ausgabe zu vergleichen.
   */
  public static void vergleichsAusgabe(Liste soll, Liste ist){
    IO.print("Sollte sein: ");
    druckeListe(soll);
    IO.print("Ist: ");
    druckeListe(ist);
    IO.println();
  }

  /**
   * Hilfsmethode zum Drucken einer Liste auf der Konsole.
   */
  public static void druckeListe(Liste l){
    l.reset();
    IO.print("[");
    if(!l.endpos()){
      IO.print(l.elem());
      l.advance();
    }

    while(!l.endpos()){
      IO.print(", ");
      IO.print(l.elem());
      l.advance();
    }
    IO.println("]");
  }
}
