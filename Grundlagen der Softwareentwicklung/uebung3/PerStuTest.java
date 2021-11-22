/**
 * Testet die equals() und hashCode() Methoden der Klasse Person und Student
 * @version 05.04.2017
 * @author Mark Hiltenkamp
 */
public class PerStuTest{

    public static void main(String[] args){
        
        Person p1 = new Person("P");
        Person p2 = new Student("P", 1);
        Person p3 = new Person("P");
        Person p4 = new Person("P");
        Student s1 = new Student("S", 1);
        Student s2 = new Student("S", 1);
        Student s3 = new Student("S", 1);
        
        int pHash1, pHash2, sHash1, sHash2;
        pHash1 = p1.hashCode();
        sHash1 = s1.hashCode();

        pHash2 = p1.hashCode();
        sHash2 = s1.hashCode();

        System.out.println("Gleiche Instanz, zwei Aufrufe, gleicher hashcode?");
        System.out.println("Person\n> " + (pHash1 == pHash2));
        System.out.println("Student\n> " + (sHash1 == sHash2) + "\n");

        if(p1.equals(p2)){
            /* true => Fehlerhafte Implementation der equals() Methode der
             *         Person Klasse. p1.equals(p2) sollte false ausgeben.
             *         Das kann erreicht werden, indem in der equals Methode
             *         der Person Klasse Object.getClass() anstatt instanceof
             *         verwendet wird.
             *         Vorher  : if(o instanceof Person){...}
             *         Nachher : if(o.getClass().getName().equals("Person")){...}
             */
            System.out.println("equals == true, gleicher hashcode?");
            System.out.println("> " + (p1.hashCode() == p2.hashCode()) + "\n");
            /* Durch die fehlerhafte Implementierung der equals() Methode der
             * Person Klasse entsteht hier ein Folgefehler. Der hashcode 
             * von zwei angeblich gleichen Objekten ist unterschiedlich
            */
        }
        System.out.println("Equals reflexive?");
        System.out.println("Person\n> " + p1.equals(p1));
        System.out.println("Student\n> " + s1.equals(s1) + "\n");

        System.out.println("Equals symmetric?");
        System.out.println("Person\n> " + (p1.equals(p2) && p2.equals(p1)));
        /* false => Sollte true liefern. Liegt auch hier an der fehlerhaften
         *          equals() Methode der Person Klasse. Die vorher gennante 
         *          Aenderung loest das Problem
         */
        System.out.println("Student\n> " + (s1.equals(s2) && s2.equals(s1)) 
                            + "\n");

        System.out.println("Equals transitive?");
        System.out.println("Person\n> " + ((p1.equals(p3) && p3.equals(p4))
                            && p1.equals(p4)));
        System.out.println("Student\n> " + ((s1.equals(s2) && s2.equals(s3))
                            && s1.equals(s3)) + "\n");

        System.out.println("Equals consistent?");
        System.out.println("Person\n> " + multInvoc(1000, p1, p3));
        System.out.println("Student\n> " + multInvoc(1000, s1, s3) + "\n");

        System.out.println("Equals mit null?");
        System.out.println("Person\n> " + p1.equals(null));
        System.out.println("Student\n> " + s1.equals(null));
    }
    
    /**
     * Vergleicht zwei Objekte mit Hilfe der Methode equals() beliebig oft
     * @param i Anzahl der Vergleiche
     * @param x Das erste Objekt
     * @param y Das zweite Objekt
     * @return true, falls alle Vergleiche true waren, sonst false
     */
    public static boolean multInvoc(int i, Object x, Object y){
        if(i == 0)
            return x.equals(y);
        return x.equals(y) && multInvoc(i-1, x, y);
    }
}
