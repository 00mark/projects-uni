/**
 * @version 04.25.17
 * @author Mark Hiltenkamp
 * Liest eine einfache Bruchrechnungsoperation ein und wertet diese aus
 */
public class Calculator{
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
            // Pruefe, ob gueltige Brueche vorliegen
            if((!args[0].matches("-?\\d+/-?[1-9]\\d*") && !args[0].matches("-?\\d+")) ||
               (!args[2].matches("-?\\d+/-?[1-9]\\d*") && !args[2].matches("-?\\d+"))){
                System.out.println("Ungueltiger Bruch");
                usage();
            }
            else{
                // Wandele die Strings in Brueche um
                Fraction f1 = Fraction.parseFraction(args[0]);
                String s = args[1];
                Fraction f2 = Fraction.parseFraction(args[2]);

                switch(s){
                    case "+" : System.out.println(f1.add(f2)); break;
                    case "-" : System.out.println(f1.substract(f2)); break;
                    case "*" : System.out.println(f1.multiply(f2)); break;
                    case "/" : System.out.println(f1.divide(f2)); break;
                    default  : System.out.println("Ungueltige Operation"); usage();
                }
            }
        }
    }

    /**
     * Gibt an, wie das Programm bedient werden soll
     */
    public static void usage(){
        System.out.println("Usage : Fraction Operation Fraction");
        System.out.println("        Fraction : Integer[/Integer\\{0}]");
        System.out.println("        Operation : '+'|'-'|'*'|'/'");
    }
}
