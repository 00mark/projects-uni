import java.util.Random;
/**
 * @version 04.20.17
 * @author Mark Hiltenkamp
 * Testet die Company Klasse, indem mehrere Company Instanzen erzeugt werden,
 * dann teilweise neue stock prices gesetzt werden und teilweise Insanzen auf
 * null gesetzt werden
 */
public class CompanyTest{

    public static void main(String[] args){

        // Zufallszahl zur Aenderung des stock prices
        Random r = new Random();
        // Anzahl der zu erzeugenden Company Instanzen
        int length = 15;
        Company[] cmpAry = new Company[length];

        for(int i = 0; i < length; i++)
            cmpAry[i] = new Company("Company" + i);

        for(int i = 0; i < length/3; i++)
            cmpAry[i] = null;
        // Erster Aufruf des Garbage Collectors
        System.gc();

        for(int i = length/3; i < length * 2/3; i++)
            cmpAry[i].changeStockPrice(Math.round(r.nextDouble() * 100.0));

        for(int i = length * 2/3; i < length; i++){
            cmpAry[i] = null;
        }
        // Zweiter Aufruf des Garbage Collectors
        System.gc();
        System.out.println();
    }   
}
