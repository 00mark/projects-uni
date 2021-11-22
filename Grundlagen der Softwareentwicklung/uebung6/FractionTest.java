package fraction;
/**
 * Testet die Funktionalitaeten der Fraction Klasse
 * @version 05.27.17
 * @author Mark Hiltenkamp
 */
public class FractionTest{

    public static void main(String[] args){

        Fraction f1 = Fraction.parseFraction("1/2");
        Fraction f2 = Fraction.parseFraction("2/4");
        Fraction f3 = Fraction.parseFraction(2);
        Fraction f4 = Fraction.parseFraction(4, 2);
        Fraction f5 = Fraction.parseFraction("17/13");
        Fraction f6 = Fraction.parseFraction(34, 26);

        System.out.print("Test String == String\n> ");
        System.out.println(f1 == f2 && f1 != f5);

        System.out.print("Test int == int\n> ");
        System.out.println(f3 == f4 && f3 != f6);
        
        System.out.print("Test String == int\n> ");
        System.out.println(f5 == f6 && f5 != f2);
    }
}

