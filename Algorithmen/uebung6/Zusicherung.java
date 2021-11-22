/**************************  Zusicherung.java  *******************************/
import AlgoTools.IO;

/**
 * Beweis der Korrektheit mit Hilfe der Zusicherungsmethode
 */

public class Zusicherung {

  public static void main(String[] args) {

    int i = 0, h = 1, z = 0, n;

    do {
      n = IO.readInt("n= ");
    } while (n < 0);

    /* z = i * i und h = 2 * i + 1 und i <= n ist Schleifeninvariante */

    while (i < n) {
    //* z = i * i und h = 2 * i + 1 und i < n und n >= 0

      z += h;
	  // i < n und h = 2 * i + 1 und z = i(i+2) + 1 
      h += 2;
	  // i < n und h = 2 * i + 3 und z = i(i+2) +1
      i++;
	  // i <= n und h = 2 * i + 1 und z = i*i
    }
    // i = n und h = 2 * i + 1 und z = i * i und n < 0
    IO.println(z, 6);		// i^2
  }
}
