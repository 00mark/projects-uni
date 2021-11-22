package library;

/**
 * Abstrakte Klasse LibraryItem. Die abgeleiteten Klassen koennen in einem 
 * Library Objekt "abgelegt" werden 
 * @version 05.15.17
 * @author Mark Hiltenkamp
 */
public abstract class LibraryItem{
    
    private boolean isBorrowed;

    public LibraryItem(){
    }

    /**
     * Gibt wieder, ob ein LibraryItem Objekt ausgeliehen ist
     * @return true, falls ausgeliehen, sonst false
     */
    public boolean isBorrowed(){
        return isBorrowed;
    }

    /**
     * Aendert den "ausgeliehen" Zustand des LibraryItem Objektes
     * @param isBorrowed boolean, true => ausgeliehen, sonst nicht ausgeliehen 
     */
    public void setBorrowed(boolean isBorrowed){
        this.isBorrowed = isBorrowed;
    }

    public abstract String getDescription();
}
