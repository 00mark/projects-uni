package comparison;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Vergleicht verschidene Collection Instanzen anhand der Methoden add,
 * remove und contains
 * @version 05.28.17
 * @author Mark Hiltenkamp
 */
public class CollectionComparison{

    private static final int ITERATIONS = 20000;

    public static void main(String[] args){

        LinkedList ll = new LinkedList();
        ArrayList al = new ArrayList();
        HashSet hs = new HashSet();

        String[] llResult = speedTest(ll);
        String[] alResult = speedTest(al);
        String[] hsResult = speedTest(hs);

        printResult(llResult, alResult, hsResult);
    }

    /**
     * Ruft die Methoden add, contains und remove der eingegebenen Collection
     * Instanz mehrmals auf, nimmt die Zeit, bildet den Durchschnitt und gibt
     * den Klassen-Namen der Instanz und die Durchschnittswerte wieder
     * @param c Die Collection Instanz
     * @return String-Array mit Namen und Durchschnittswerten
     */
    public static <T extends java.util.Collection>String[] speedTest(T c){
        String[] result = new String[4];
        double time, timeAvg = 0;

        result[0] = c.getClass().getName();

        // add
        for(int i = 0; i < ITERATIONS; i++){
            time = System.nanoTime();
            c.add(i);
            timeAvg += System.nanoTime() - time;
        }
        result[1] = Double.toString((timeAvg / ITERATIONS)) + " ns";
        timeAvg = 0;

        // contains
        for(int i = 0; i < ITERATIONS; i++){
            time = System.nanoTime();
            c.contains(i);
            timeAvg += System.nanoTime() - time;
        }
        result[2] = Double.toString((timeAvg / ITERATIONS)) + " ns";
        timeAvg = 0;

        // delete
        for(int i = 0; i < ITERATIONS; i++){
            time = System.nanoTime();
            c.remove(i);
            timeAvg += System.nanoTime() - time;
        }
        result[3] = Double.toString((timeAvg / ITERATIONS)) + " ns";

        return result;
    }

    /**
     * Gibt die Testresultate in Tabellenform aus
     * @param result Die Resultate der speedTests
     */
    public static void printResult(String[]... result){
        String[] content = {"Name", "Add", "Contains", "Remove"};
        for(int i = 0; i < result[0].length; i++){
            for(String[] col : result){
                System.out.print(col[i]);
                for(int j = 0; j < col[0].length() - col[i].length(); j++)
                    System.out.print(" ");
                System.out.print("\t|");
            }
            System.out.print(content[i] + "\n");
        }
    }
}
