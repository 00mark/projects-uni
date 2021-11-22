/**
 * @version 20.04.17
 * @author Mark Hiltenkamp
 * Ticker Klasse, die nach dem Singleton Prinzip die Erstellung eines 
 * Ticker Objektes ermoeglicht
 */
public class Ticker{
    // Die Ticker Instanz
    private static Ticker ticker;

    private Ticker(){
    }
    
    /**
     * Erstellt eine neue Ticker Instanz, falls noch keine vorhanden ist
     * und gibt diese wieder
     * @return die (neue) Ticker Instanz
     */
    public static Ticker getInstance(){
        if(ticker == null)
            ticker = new Ticker();
        return ticker;
    }
    
    /**
     * Ersetzt Zeilenumbrueche des eingegebenen Textes durch Blanks, 
     * fuegt "+++" an den Anfang des Textes an und gibt diesen aus
     * @param text Der auszugebende Text
     */
    public void print(String text){
        System.out.print("+++" + text.replaceAll("\n", " "));
    }
}

