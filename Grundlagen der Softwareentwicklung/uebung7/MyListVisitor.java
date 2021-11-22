package util;

/**
 * Implementiert die Methode visit, welche an den Objekten einer MyList
 * durch den Aufruf der accept Methode ausgefuehrt wird
 * @version 06.04.17
 * @author Mark Hiltenkamp
 */
public class MyListVisitor<E> implements Visitor<E>{

    private static Object[] passedElements = new Object[100];
    private static int currentElement = 0;
    /**
     * Methode, die an den Elementen einer MyList ausgefuehrt werden kann
     * @param o das jeweilige Element, an dem visit ausgefuehrt wird
     * @return true, falls visit weiter ausgefuehrt werden soll, sonst false
     */
    public boolean visit(E o){
        if(o instanceof Integer){
            if((Integer)o > 10)
                return false;
            passedElements[currentElement] = o;
            currentElement++;
            return true;
        }
        else
            return false;
    }

    /**
     * Gibt die Elemente wieder, die der Visitor "besucht" hat
     * @return die besuchten Elemente
     */
    public Object[] getPassedElements(){
        return passedElements; 
    }
}
