/**
 * @version 05.11.17
 * @author Mark Hiltenkamp
 * Liest eine einfache Bruchrechnungsoperation ein und wertet diese aus
 */
public class Calculator{

    private static final String NUMBERREGEX = "-?\\d+";
    private static final String FRACREGEX = "-?\\d+/-?[1-9]\\d*";
    private static final String DECIMALREGEX = "-?\\d+\\.\\d+";
    /**
     * Liest einen Bruch, eine Operation und einen weiteren Bruch von der
     * Kommandozeile ein, fuehrt die Operation durch und gibt das Ergebnis aus
     * @param args String Array mit Bruch als erstem Element, Operation als 
     *             zweitem Element und Bruch als drittem Element
     */
    public static void main(String[] args){
        // Pruefe, ob die Laenge der Eingabe stimmt
        if(args.length != 3){
            System.out.println("Ungueltige Eingabelaenge");
            usage();
        }
        else{
            // Pruefe, ob gueltige Brueche, oder Dezimalzahlen vorliegen
            if((!args[0].matches(NUMBERREGEX) && !args[0].matches(DECIMALREGEX) 
                        && !args[0].matches(FRACREGEX)) || 
                    (!args[2].matches(NUMBERREGEX) && !args[2].matches(DECIMALREGEX)
                     && !args[2].matches(FRACREGEX))){
                System.out.println("Keine gueltigen Zahlen, Dezimalzahlen, oder Brueche eingegeben");
                usage();
                     }
            else{
                // Wandele die Strings in Brueche um
                String s = args[1];
                if(args[0].matches(FRACREGEX) && args[2].matches(FRACREGEX)){
                    Fraction f1 = Fraction.parseFraction(args[0]);
                    Fraction f2 = Fraction.parseFraction(args[2]);
                    switch(s){
                        case "+" : System.out.println(f1.add(f2)); break;
                        case "-" : System.out.println(f1.substract(f2)); break;
                        case "*" : System.out.println(f1.multiply(f2)); break;
                        case "/" : System.out.println(f1.divide(f2)); break;
                        default  : System.out.println("Ungueltige Operation"); usage();
                    }
                }
                else{
                    double d1, d2;
                    if(args[0].matches(FRACREGEX)){
                        d1 = Fraction.parseFraction(args[0]).doubleValue();
                        d2 = Double.parseDouble(args[2]);
                    }
                    else{
                        d1 = Double.parseDouble(args[0]);
                        if(args[2].matches(FRACREGEX))
                            d2 = Fraction.parseFraction(args[2]).doubleValue();
                        else
                            d2 = Double.parseDouble(args[2]);
                    }
                    switch(s){
                        case "+" : System.out.println(d1 + d2); break;
                        case "-" : System.out.println(d1 - d2); break;
                        case "*" : System.out.println(d1 * d2); break;
                        case "/" : System.out.println(d1 / d2); break;
                        default  : System.out.println("Ungueltige Operation"); usage();
                    }
                }
            }
        }
    }

    /**
     * Gibt an, wie das Programm bedient werden soll
     */
    public static void usage(){
        System.out.println("Usage : Fraction|Decimal Operation Fraction|Decimal");
        System.out.println("        Fraction : Integer[/Integer\\{0}]");
        System.out.println("        Decimal : Integer[\".\"Integer]");
        System.out.println("        Operation : '+'|'-'|'*'|'/'");
    }
}
