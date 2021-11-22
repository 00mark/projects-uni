package library;

import library.LibraryItem;
import util.List;

/**
 * Ermoeglicht die Erstellung von Library Instanzen. LibraryItem
 * Objekte koennen zugefuegt und entfernt werden. Ausserdem kann nach
 * Objekten gesucht werden
 * @version 05.15.17
 * @author Mark Hiltenkamp
 */
public class Library{
    
    private List list;

    public Library(){
        list = new List();
    }

    /**
     * Fuegt der Library ein LibraryItem Objekt hinzu
     * @param item das LibraryItem Objekt
     */
    public void addItem(LibraryItem item){
        item.setBorrowed(false);
        list.add(item); 
    }

    /**
     * Entfernt ein LibraryItem Objekt aus der Library
     * @param item das LibraryItem Objekt
     */
    public void deleteItem(LibraryItem item){
        list.reset();
        while(!list.endpos()){
            if(list.elem().equals(item)){
                item.setBorrowed(true);
                list.delete();
            }
            else
                list.advance();
        }
    }

    /**
     * Sucht LibraryItem Objekte der Library und gibt die passenden Objekte zurueck
     * @param text String, mit dem die getDescription Methode der LibraryItem
     *             Instanzen abgeglichen werden sollen
     * @return Liste von LibraryItem Objekten, die gefunden wurden
     */
    public List search(String text){
        List searchList = new List();
        list.reset();
        while(!list.endpos()){
            if(stringInString(text, ((LibraryItem)list.elem()).getDescription()))
                searchList.add(list.elem());
            list.advance();
        }
        return searchList;
    }

    /**
     * Prueft, ob ein String in einem Anderen enthalten ist
     * @param shortS String mit Laenge <= longS
     * @param longS String mit Laenge >= shortS
     * @throws RuntimeException, falls shortS nichts beinhaltet
     * @return true, falls shortS in longS enthalten ist, sonst false
     */
    private static boolean stringInString(String shortS, String longS){
        if(shortS.length() == 0)
            throw new RuntimeException("short string should not be empty");
        int l = 0;
        for(int i = 0; i < longS.length(); i++){
            if(shortS.substring(l, l+1).equals(longS.substring(i, i+1)))
                l++;
            else
                l = 0;
            // Laenge des kurzen Strings ist gleich der Anzahl der aufeinanderfolgenden
            // gleichen substrings => shortS ist in longS enthalten
            if(shortS.length() == l)
                return true;
        }
        return false;
    }
}
