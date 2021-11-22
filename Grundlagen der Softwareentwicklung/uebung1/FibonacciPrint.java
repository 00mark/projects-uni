import static java.lang.System.*;
/**
 * Legt eine Fibonacci-Instanz an, und printet die ersten n Werte
 * @author Mark Hiltenkamp
 * @version 21:41, 04/10/17
 */
public class FibonacciPrint{
    /**
     * Gibt die ersten n Fibonacci-Zahlen mit Hilfe der Klasse
     * Fibonacci wieder
     * @param argv das erste Element des Arrays entspricht n
     * @throws RuntimeException Falls n negativ, oder argv leer ist
     */
    public static void main(String[] argv){

        if(argv.length == 0 || Integer.parseInt(argv[0]) < 0)
            throw new RuntimeException("Ungueltige Eingabe");
        // Neue Fibonacci-Instanz anlegen 
        Fibonacci f = new Fibonacci();

        out.println("| n |  f(n)  |");
        out.println("+---+--------+");
        
        for(int i = 0; i <= Integer.parseInt(argv[0]); i++)
            if(i < 2)
                out.printf("|%3d|%8d|%n", i, i);
            else
                out.printf("|%3d|%8d|%n", i, f.next());
    }
}

