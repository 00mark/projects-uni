package library;

/**
 * Ermoeglicht die Erstellung von Book Objekten, welche in einer Library
 * Instanz abgelegt werden koennen
 * @version 05.15.17
 * @author Mark Hiltenkamp
 */
public class Book extends LibraryItem{
    
    private String title, author;

    public Book(String title, String author){
        setBorrowed(true);
        this.title = title;
        this.author = author;
    }

    /**
     * Gibt den Titel des Buchs wieder 
     * @return den Titel
     */
    public String getTitle(){
        return title;
    }

    /**
     * Gibt den Namen des Authors des Buchs wieder
     * @return den Namen des Authors
     */
    public String getAuthor(){
        return author;
    }

    /**
     * Gibt die Bezeichung des Buchs wieder ( Titel und Author)
     * @return String mit Title und Author
     */
    public String getDescription(){
        return "Book : " + getTitle() + ", Author : " + getAuthor();
    }
}
