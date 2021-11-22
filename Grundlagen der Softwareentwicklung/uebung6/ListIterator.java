package util;

import java.util.ConcurrentModificationException;

/**
 * ListIterator, welcher dazu verwendet werden kann, eine MyList zu iterieren
 * @version 05.28.17
 * @author Mark Hiltenkamp
 */
public class ListIterator<E> implements java.util.Iterator{
    
    private MyList<E> list;
    
    private int advances = 0, state;

    // darf remove() aufgerufen werden?
    private boolean removable; 

    public ListIterator(MyList<E> list){
        this.list = list;
        this.list.reset();
        state = this.list.getState();
    }

    /**
     * Gibt wieder, ob die Liste ein naechstes Element besitzt
     * @throws ConcurrentModificationException, falls die Liste veraendert wurde
     * @return true, falls die Liste ein naechstes Element besitzt, sonst false
     */
    public boolean hasNext(){
        if(state != list.getState())
            throw new ConcurrentModificationException("Liste wurde veraendert");
        if(list.endpos())
            return false;
        return true;
    }

    /**
     * Gibt das naechste Element der Liste wieder
     * @throws ConcurrentModificationException, falls die Liste veraendert wurde
     * @return das naechste Element der Liste
     */
    public E next(){
        if(state != list.getState())
            throw new ConcurrentModificationException("Liste wurde veraendert");
        if(hasNext()){
           E elem = list.elem();
           list.advance();
           advances++;
           removable = true;
           return elem;
        }
        return null;
    }

    /**
     * Loescht das zuletzt wiedergegebenen Element aus der Liste
     * @throws ConcurrentModificationException, falls die Liste veraendert wurde
     * @throws IllegalStateException, falls next nicht vor remove aufgerufen 
     *                                wurde
     */
    public void remove(){
        if(state != list.getState())
            throw new ConcurrentModificationException("Liste wurde veraendert");
        if(!removable)
            throw new IllegalStateException("next muss vor remove aufgerufen "
                    + "werden");
        list.reset();
        // Gehe zum letzten Element
        for(int i = 0; i < advances - 1; i++)
            list.advance();
        list.delete();
        removable = false;
        state++;
        advances--;
    }
}
