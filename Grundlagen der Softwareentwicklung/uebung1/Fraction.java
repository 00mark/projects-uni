/**
 * Ermoeglicht die Erstellung von Fraction Instanzen und bietet
 * verschiedene Methoden um Rechenoperationen durchzufuehren
 * @author Mark Hiltenkamp
 * @version 13:21, 04/11/17
 */
public class Fraction{

    // Zaehler und Nenner
    private int numerator, denominator;

    /**
     * Erstellt eine Fraction Instanz bestehend aus Nenner und Zaehler
     * @param n Zaehler des Bruches
     * @param d Nenner des Bruches
     */
    public Fraction(int n, int d){
        int teiler = ggt(n, d);
        setNumerator(n / teiler);
        setDenominator(d / teiler);
    }

    /**
     * Erstellt eine Fraction Instanz bestehend aus Zaehler
     * (Nenner wird auf 1 gesetzt
     * @param n Zaehler des Bruches
     */
    public Fraction(int n){
        this(n, 1);
    }

    /**
     * Gibt den Wert des Zaehlers wieder
     * @return Den Wert des Zaehlers
     */
    public int getNumerator(){
        return this.numerator;
    }

    /**
     * Gibt den Wert des Nenners wieder
     * @return Den Wert des Nenners
     */
    public int getDenominator(){
        return this.denominator;
    }

    /**
     * Aendert den Wert des Zaehlers
     * @param n der neue Zaehler
     */
    private void setNumerator(int n){
        this.numerator = n;
    }

    /**
     * Aendert den Wert des Nenners
     * @param d der neue Nenner (muss ungleich 0 sein)
     * @throws RuntimeException bei d = 0
     */
    private void setDenominator(int d){
        if(d == 0)
            throw new RuntimeException("Division durch 0!");
        this.denominator = d;
    }
    
    /**
     * Multipliziert die Fraction Instanz mit dem eingegebenen Faktor
     * @param factor Faktor mit dem die Fraction Instanz multipliziert werden soll
     * @return Die neue Fraction Instanz
     */
    public Fraction multiply(int factor){
        return new Fraction(getNumerator() * factor, getDenominator());
    }

    /**
     * Multipliziert die Fraction Instanz mit dem eingegebenen Bruch
     * @param factor Bruch mit dem die Fraction Instanz multipliziert werden soll
     * @return Die neue Fraction Instanz
     */
    public Fraction multiply(Fraction factor){
        return new Fraction(getNumerator() * factor.getNumerator(),
                            getDenominator() * factor.getDenominator());
    }
    
    /**
     * Dividiert die Fraction Instanz durch den eingegebenen Bruch
     * @param divisor Bruch durch den die Fraction Instanz geteilt werden soll
     * @return Die neue Fraction Instanz
     */
    public Fraction divide(Fraction divisor){
        if(divisor.getNumerator() == 0)
            throw new RuntimeException("Division durch 0!");
        return new Fraction(getNumerator() * divisor.getDenominator(),
                            getDenominator() * divisor.getNumerator());
    }
    
    /**
     * Multipliziert die Fraction Instanz mit den eingegebenen Bruechen
     * @param factors Brueche, die mit der Fraction Instanz multipliziert werden sollen
     * @return Die neue Fraction Instanz
     */
    public Fraction multiply(Fraction... factors){
        int n, d;
        n = getNumerator();
        d = getDenominator();
        // Geh die einzelnen Brueche durch und multipliziere jeweils Nennen mit Nenner
        // und Zaehler mit Zaehler
        for(Fraction f : factors){
           n *= f.getNumerator(); 
           d *= f.getDenominator();
        }
        return new Fraction(n, d);
    }

    /**
     * Gibt die Fraction Instanz in ueblicher Bruch-Schreibweise aus
     * @return Den erstellten Bruch String
     */
    public String toString(){
        // Gib 0 wieder, falls der Zaehler 0 ist
        if(getNumerator() == 0)
            return "0";
        else{
            // Gib nur den Zaehler wieder, falls der Nenner 1 ist
            if(getDenominator() == 1)
                return Integer.toString(getNumerator());
            else
                // Schreibe das Minus Zeichen vor den Bruch, falls der Nenner negativ ist
                if(getDenominator() < 0)
                    return "-" + getNumerator() + "/" + -getDenominator();
                else
                    return getNumerator() + "/" + getDenominator();
        }
    }

    /**
     * Ermittelt den groessten gemeinsamen Teiler zweier Zahlen
     * (wird benoetigt, um einen Bruch zu kuerzen
     * @param a Erster Integer
     * @param b Zweiter Integer
     * @return Den ggt von a und b
     */
    public static int ggt(int a, int b){
        if(b == 0)
            return a;
        else return ggt(b, a%b);
    }
}
