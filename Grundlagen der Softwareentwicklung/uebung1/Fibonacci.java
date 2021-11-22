/**
 * Ermoeglicht die Erstellung eines Fibonacci-Objektes, welches es
 * moeglich macht, die jeweils naechste Fibonacci-Zahl zu errechnen
 * @author Mark Hiltenkamp
 * @version 21:36, 04/10/17
 */
public class Fibonacci{
    // Vorherige Fibonacci-Zahl
    private int prev = 0;
    // Aktuelle Fibonacci-Zahl
    private int current = 1;
    /**
     * Gibt die naechste Fibonacci-Zahl(angefangen mit f(2))
     * wieder und setzt die aktuelle und vorherige Fibonacci-Zahl neu
     * @return Die naechste Fibonacci-Zahl
     */
    public int next(){
        // f(n) = f(n-2) + f(n-1)
        int tmp = prev + current;
        prev = current;
        current = tmp;
        return current;
    }
}
