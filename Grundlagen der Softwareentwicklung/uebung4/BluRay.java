package library;

/**
 * Ermoeglicht die erstellung von BluRay Objekten, welche in Library Instanzen
 * abgelegt werden koennen
 * @version 05.15.17
 * @author Mark Hiltenkamp
 */
public class BluRay extends library.LibraryItem{
    
    private String title, director;

    public BluRay(String title, String director){
        setBorrowed(true);
        this.title = title;
        this.director = director;
    }

    /**
     * Gibt den Titel der BluRay wieder
     * @return den Titel
     */
    public String getTitle(){
        return title;
    }

    /**
     * Gibt den Namen des Directors der BluRay wieder
     * @return den Namen des Directors 
     */
    public String getDirector(){
        return director;
    }

    /**
     * Gibt die Beschreibung der BlueRay wieder (Titel und Director)
     * @return die Beschreibung 
     */
    public String getDescription(){
        return "BluRay : " + getTitle()+ ", Director : " + getDirector();
    }
}
