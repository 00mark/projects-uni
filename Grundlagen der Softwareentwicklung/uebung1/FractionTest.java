import static java.lang.System.*;
import java.util.Scanner;
/**
 * TestKlasse um die Funktion der Fraction Klasse zu testen
 * @author Mark Hiltenkamp
 * @version 13:48 04/11/17
 */
public class FractionTest{

    /**
     * Legt mehrere Fraction Instanzen an und fuehrt die 
     * verfuegbaren Methoden an diesen aus
     */
    public static void main(String[] argv){

        // Scanner anlegen um die Test-Zahlen einzulesen    
        Scanner s = new Scanner(System.in);

        out.print("Erste Zahl \n> ");
        int i1 = s.nextInt();
        out.print("Zweite Zahl \n> ");
        int i2 = s.nextInt();
        out.print("Zaehler des ersten Bruches \n> ");
        int i3 = s.nextInt();
        out.print("Nenner des ersten Bruches (!= 0)\n> ");
        int i4 = s.nextInt();
        out.print("Zaehler des zweiten Bruches (!= 0(wird zur Division verwendet))\n> ");
        int i5 = s.nextInt();
        out.print("Nenner des zweiten Bruches (!= 0)\n> ");
        int i6 = s.nextInt();

        // Erzeugung der Fraction Instanzen mit Hilfe der eingelesenen Zahlen
        Fraction f1 = new Fraction(i1);
        Fraction f2 = new Fraction(i2);
        Fraction f3 = new Fraction(i3, i4);
        Fraction f4 = new Fraction(i5, i6);
        
        // Multiplikation mit einer Konstanten
        Fraction f5 = f1.multiply(7);
        out.println(f1 + " * 7 = " + f5 +
                " (Sollte " + i1 * 7 + " Sein)");

        // Multiplikation zweier Brueche
        Fraction f6 = f2.multiply(f3);
        out.println(f2 + " * " + f3 + " = " + f6 + 
                " (Sollte " + i2 * ((double)i3 / i4) + " Sein)");

        // Division zweier Brueche (Zweiter Bruch muss ungleich 0 sein)
        Fraction f7 = f3.divide(f4);
        out.println(f3 + " / " + f4 + " = " + f7 + 
                " (Sollte " + ((double)i3 / i4) / ((double)i5 / i6)  + " Sein)");

        // Multiplikation mehrerer Brueche
        Fraction f8 = f1.multiply(f2, f3, f4);
        out.println(f1 + " * " + f2 + " * " + f3 + " * " + f4 + " = " + f8 + 
                " (Sollte " + i1 * i2 * ((double)i3 / i4) * ((double)i5 / i6) + " Sein)");
    }
}
