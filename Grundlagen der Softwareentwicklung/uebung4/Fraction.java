/**
 * Ermoeglicht die Erstellung von Fraction Instanzen und bietet
 * verschiedene Methoden um Rechenoperationen durchzufuehren
 * @author Mark Hiltenkamp
 * @version 05/11/17
 */
public class Fraction extends Number{

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
        if(d < 0){
            setNumerator(-getNumerator());
            this.denominator = -d;
        }
        else
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
     * Addiert zwei Fraction Instanzen
     * @param addend Fraction Instanz, die mit dieser Fraction Instanz addiert wird
     * @return Die neue Fraction Instanz
     */
    public Fraction add(Fraction addend){
        // Bringe die Brueche auf den gleichen Nenner
        int[] cd = findCommonDenominator(getDenominator(), addend.getDenominator());
        return new Fraction(getNumerator() * cd[0] + addend.getNumerator() * cd[1],
                getDenominator() * cd[0]);
    }

    /**
     * Subtrahiert eine Fraction Instanz von Dieser
     * @param subtrahend Fraction Instanz, die von Dieser subtrahiert wird
     * @return Die neue Fraction Instanz
     */
    public Fraction substract(Fraction subtrahend){
        int[] cd = findCommonDenominator(getDenominator(), subtrahend.getDenominator());
        return new Fraction(getNumerator() * cd[0] - subtrahend.getNumerator() * cd[1],
                getDenominator() *cd[0]);
    }

    /**
     * Gibt die Fraction Instanz in ueblicher Bruch-Schreibweise aus
     * @return Den erstellten Bruch String
     */
    public String toString(){
    /*    // Gib 0 wieder, falls der Zaehler 0 ist
        if(getNumerator() == 0)
            return "0";
        else{
            // Gib nur den Zaehler wieder, falls der Nenner 1 ist
            if(getDenominator() == 1)
                return Integer.toString(getNumerator());
            else*/
                return getNumerator() + "/" + getDenominator();
       // }
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

    /**
     * Ermittelt den gemeinsamen Nenner zweier Brueche
     * @param d1 der Nenner des ersten Bruchs
     * @param d2 der Nenner des zweiten Bruchs
     * @return Ein Array mit den Zahlen, mit denen der erste und zweite
     *          Nennen multipliziert werden muessen, um auf den selben Wert zu kommen
     */
    public static int[] findCommonDenominator(int d1, int d2){
        // Falls d1 = d2 ist, wird mit 1 multipliziert
        int[] cd = {1, 1};
        if(d1 != d2){
            // Sonst muss die erste Zahl mit der Zweiten und die Zahl mit
            // der Ersten multipliziert werden
            cd[0] = d2;
            cd[1] = d1;
        }
        return cd;
    }

    /**
     * Erstellt eine Fraction Instanz aus dem eingegebenen String
     * @param frac Bruch der From "Zaehler/Nenner" oder "Zaehler" als String
     * @throws RuntimeException Falls die Eingabe nicht dem regulaeren Ausdruck
     *          entspricht
     * @return Die neue Fraction Instanz
     */
    public static Fraction parseFraction(String frac){
        // String der Form "Zaehler/Nenner"
        if(frac.matches("-?\\d+/-?[1-9]\\d*")){
            // Zaehler = alles vor dem "/", Nenner = alles nach dem "/"
            String[] bruch = frac.split("/");
            int n = Integer.parseInt(bruch[0]); 
            int d = Integer.parseInt(bruch[1]);
            return new Fraction(n, d); 
        }
        // String der From "Zaehler"
        if(frac.matches("-?\\d+")){
            // Zaehler = Eingabe, Nenner = 1
            int n = Integer.parseInt(frac);
            return new Fraction(n, 1);
        }
        throw new RuntimeException("Ungueltige Eingabe");
    }

    /**
     * Gibt die Fraction Instanz als double Wert zurueck
     * @return den double Wert
     */
    public double doubleValue(){
        return (double)getNumerator() / getDenominator();
    }

    /**
     * Gibt die Fraction Instanz als float Wert zurueck
     * @return den float Wert
     */
    public float floatValue(){
        return (float)getNumerator() / getDenominator();
    }

    /**
     * Gibt die Fraction Instanz als int Wert zurueck
     * @return den int Wert
     */
    public int intValue(){
        return (int)(getNumerator() / getDenominator());
    }

    /**
     * Gibt die Fraction Instanz als long Wert zurueck
     * @return den long Wert
     */
    public long longValue(){
        return (long)(getNumerator() / getDenominator());
    }
}
