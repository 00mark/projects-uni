package library;
/**
 * Testet die Klassen des library Paketes
 * @version 05.15.17
 * @author Mark Hiltenkamp
 */
public class LibraryTest{

    public static void main(String[] args){

        // Test Library Konstruktor
        Library lib = new Library();
        // Test Book Konstruktor
        Book bo = new Book("BookA", "AuthorA");
        // Test BluRay Konstruktor
        BluRay bl = new BluRay("BluRayA", "DirectorA");

        // Test Book Methoden
        System.out.print("Book Methoden liefern das erwartete Ergebnis\n> ");
        System.out.println(bo.isBorrowed() &&
                bo.getTitle().equals("BookA") && 
                bo.getAuthor().equals("AuthorA") &&
                bo.getDescription().equals("Book : BookA, Author : AuthorA"));
        
        // Test BluRay Methoden
        System.out.print("BluRay Methoden liefern das erwartete Ergebnis\n> ");
        System.out.println(bl.isBorrowed() &&
                bl.getTitle().equals("BluRayA") && 
                bl.getDirector().equals("DirectorA") &&
                bl.getDescription().equals("BluRay : BluRayA, Director : DirectorA"));

        // Test Library Methoden
        
        // Test search 1
        util.List l = lib.search("A");
        System.out.print("Library Methoden liefern das erwartete Ergebnis\n> ");
        if(l.empty()){
            // Test addItem
            lib.addItem(bo);
            lib.addItem(bl);
            // Items Borrowed?
            if(!bl.isBorrowed() && !bo.isBorrowed()){
                // Test search 2
                l = lib.search("A");
                if(!l.empty()){
                    // Test deleteItem
                    lib.deleteItem(bo);
                    lib.deleteItem(bl);
                    // Items Borrowed?
                    if(bl.isBorrowed() && bo.isBorrowed()){
                        // Test search 3 
                        l = lib.search("A");
                        System.out.println(l.empty());
                    } 
                    else
                        System.out.println("false");
                }
                else
                    System.out.println("false");
            }
            else
                System.out.println("false");
        }
        else
            System.out.println("false");
    }
}
