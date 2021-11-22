/**
 * @version 04.20.17
 * @author Mark Hiltenkamp
 * Ermoeglicht die Erstellung von Company Instanzen
 */
public class Company{

    //Name der Firma
    private String n;

    public Company(String name){
        setName(name);

    }       
    /**
     * Destruktor, der die Nachricht ausgibt, dass die Firma insolvent ist
     */
    protected void finalize(){
        Ticker t = Ticker.getInstance();
        t.print(n + " is insolvent");
    }

    /**
     * Gibt den Namen der Firma wieder
     * @return den Namen der Firma
     */
    public String getName(){
        return n;
    }

    /**
     * Aendert den Namen der Firma
     * @param name der neue Name der Firma
     */
    public void setName(String name){
        n = name;
    }

    /**
     * Gibt den neuen stock price der Firma aus
     * @param d Der neue stock price der Firma
     * @throws RuntimeException bei stock price < 0
     */
    public void changeStockPrice(double d){
        if(d < 0)
            throw new RuntimeException("Ungueltige Eingabe");
        Ticker t = Ticker.getInstance();
        t.print(n + " " + d);
    }

}
