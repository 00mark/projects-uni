package heap;

import java.util.Comparator;
import java.util.Arrays;

/**
 * Ermoeglicht die Erstellung von generischen Heaps
 * @version 05.23.17
 * @author Mark Hiltenkamp
 */
public class GenHeap<T>{

    private T[] content;
    private Comparator<T> comp;
    // Laenge des Arrays ausgenommen von null Werten
    private int l;
    // Laenge des gesamten Arrays
    private int lTotal = 5;

    /**
     * Erstellte einen generischen Heap, in dem angenommen wird, dass die 
     * hinzugefuegten Objecte Comparable implementieren
     * @param content der Inhalt des Heaps
     */
    public GenHeap(T... content){
        l = content.length;
        setContent(content);
        sort();
    }

    /**
     * Erstellt einen generischen Heap und gibt einen Comparator an, mit
     * welchem die hinzugefuegten Objekte verglichen werden koennen
     * @param content der Inhalt des Heaps
     * @param c der Comparator
     */
    public GenHeap(Comparator<T> c, T... content){
        l = content.length;
        comp = c;
        setContent(content);
        sort();
    }

    /**
     * Gibt den Inhalt des Heaps wieder
     * @return den Inhalt
     */
    public T[] getContent(){
        return content;
    }

    /**
     * Gibt die Laenge des Arrays ohne null Werte wieder
     * @return die Laenge des Arrays ohne null Werte
     */
    public int getLength(){
        return l;
    }

    /**
     * Gibt die Laenge des gesamten Arrays wieder
     * @return die Laenge des gesamten Arrays
     */
    public int getTotalLength(){
        return lTotal;
    }

    /**
     * Kopiert den eingegebenen Inhalt in ein neues Array und fuegt null Werte 
     * an das Ende des neuen Arrays an, bis dieses die gewuenschte groesse hat
     * @param content der Inhalt
     */
    public void setContent(T[] content){
        while(getLength() >= getTotalLength())
            lTotal *= 2;
        // Kopiere Inhalt in ein neues, laengeres Array
        this.content = Arrays.copyOf(content, getTotalLength());
    }

    /**
     * Sortiert den Heap, nach der urspruenglichen Erstelleung
     */
    private void sort(){
        // Alle Knoten ab der vorletzten Eben muessen evtl. getauscht werden
        for(int i = (getLength()-1)/2; i >= 0; i--){
            heapifyDown(i);
        }
    }

    /**
     * Gibt die Tiefe des Heaps an
     * @return die Anzahl der Ebenen
     */
    public int depth(){
        int d = 0;
        double i = getLength() + 1;
        for(; i > 1; i /= 2)
            d++;
        return d;
    }

    /**
     * Geht den Heap ab dem eingegebenen Startpunkt nach unten hin ab, und
     * fuehrt notwendige Vertauschungen durch
     * @param startPos die Startposition
     */
    private void heapifyDown(int startPos){
        for(int index = startPos; index < getLength();){
            // Wurde ein Comparator bei der Erzeugung mit angegeben?
            if(comp != null){ 
                if((index*2+2) < getLength()){
                    // Beide Soehne sind ungleich null
                    if(comp.compare(getContent()[index*2+1], getContent()[index*2+2]) <= 0 &&
                            comp.compare(getContent()[index], getContent()[index*2+1]) > 0){
                        T tmp = getContent()[index];
                        getContent()[index] = getContent()[index*2+1];
                        getContent()[index*2+1] = tmp;
                        index = index * 2 + 1;
                            }
                    else{
                        if(comp.compare(getContent()[index*2+2], getContent()[index*2+1]) <= 0 &&
                                comp.compare(getContent()[index], getContent()[index*2+2]) > 0){
                            T tmp = getContent()[index];
                            getContent()[index] = getContent()[index*2+2];
                            getContent()[index*2+2] = tmp;
                            index = index * 2 + 2;
                                }
                        else
                            // Keine Vertauschung noetig
                            break;
                    }
                }
                else{
                    if(index*2+1 < getLength()){
                        // Der linke Sohn ist ungleich null
                        if(comp.compare(getContent()[index], getContent()[index*2+1]) > 0){
                            T tmp = getContent()[index];
                            getContent()[index] = getContent()[index*2+1];
                            getContent()[index*2+1] = tmp;
                            index = index * 2 + 1;
                        }
                        else
                            // Keine Vertauschung noetig
                            break;
                    }
                    // Beide Soehne sind gleich null
                    else
                        break;
                }
            }
            else{
                try{
                    // Es wurde kein Comparator bei der Erzeugung angegeben 
                    // => Es wird angenommen, das die Objekte des Heaps Comparable
                    // implementieren
                    if((index*2+2) < getLength()){
                        // Beide Soehne sind ungleich null
                        if(((Comparable)getContent()[index*2+1]).compareTo(
                                    ((Comparable)(getContent()[index*2+2]))) <= 0 &&
                                ((Comparable)getContent()[index]).compareTo(
                                ((Comparable)getContent()[index*2+1])) > 0){
                            T tmp = getContent()[index];
                            getContent()[index] = getContent()[index*2+1];
                            getContent()[index*2+1] = tmp;
                            index = index * 2 + 1;
                                }
                        else{
                            if(((Comparable)getContent()[index*2+2]).compareTo(
                                        ((Comparable)getContent()[index*2+1])) <= 0 &&
                                    ((Comparable)getContent()[index]).compareTo(
                                    ((Comparable)getContent()[index*2+2])) > 0){
                                T tmp = getContent()[index];
                                getContent()[index] = getContent()[index*2+2];
                                getContent()[index*2+2] = tmp;
                                index = index * 2 + 2;
                                    }
                            else
                                // Keine Vertauschung noetig
                                break;
                        }
                    }
                    else{
                        if(index*2+1 < getLength()){
                            // Der Linke Sohn ist ungleich null
                            if(((Comparable)getContent()[index]).compareTo(
                                        ((Comparable)getContent()[index*2+1])) > 0){
                                T tmp = getContent()[index];
                                getContent()[index] = getContent()[index*2+1];
                                getContent()[index*2+1] = tmp;
                                index = index * 2 + 1;
                                        }
                            else
                                // Keine Vertauschung noetig 
                                break;
                        }
                        else
                            // Beide Soehne sind gleich null
                            break;
                    }
                }catch(ClassCastException e){
                    System.out.println("Kein Comparator bei der Erzeugung angegeben"
                            + " und Objekte implementieren Comparable nicht");
                    System.exit(0);
                }
            }
        }
    }

    /** 
     * Geht den Heap ab dem eingegebenen Startpunkt nach oben ab und fuehrt
     * notwendige Vertauschungen durch
     * @param startPos die Startposition
     */
    private void heapifyUp(int startPos){
        for(int index = startPos; index >= 1; index = (index-1)/2){
            // Wurde ein Comparator bei der Erzeugung mit angegeben?
            if(comp != null){
                // Aktueller Knoten < Vater?
                if(comp.compare(getContent()[index], getContent()[(index-1)/2]) < 0){
                    T tmp = getContent()[index];
                    getContent()[index] = getContent()[(index-1)/2];
                    getContent()[(index-1)/2] = tmp;
                }
                else
                    // Keine Vertauschung noetig
                    break;
            }
            else{
                try{
                    // Kein Comparator bei er Erzeugung angegeben
                    // => Es wird angenommen, dass die Objekte des Heaps Comparable
                    // implementieren
                    if(((Comparable)getContent()[index]).compareTo( 
                                ((Comparable)getContent()[(index-1)/2])) < 0){
                        T tmp = getContent()[index];
                        getContent()[index] = getContent()[(index-1)/2];
                        getContent()[(index-1)/2] = tmp;
                                }
                    else
                        break;
                }catch(ClassCastException e){
                    System.out.println("Kein Comparator bei der Erzeugung angegeben"
                            + " und Objekte implementieren Comparable nicht");
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Gibt den Inhalt des Heaps aus (ohne null Werte)
     */
    public void printContent(){
        for(T c : getContent()){
            if(c == null)
                break;
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * Gibt das Minimum des Heaps wieder
     * @return das Minimun
     */
    public Object getMin(){
        // Das Minimum ist das erste Element des Heaps
        return getContent()[0];
    }

    /**
     * Loescht das Minimum des Heaps
     * @throws RuntimeException wenn der Heap leer ist
     */
    public void deleteMin(){
        if(getLength() < 1)
            throw new RuntimeException("Heap ist leer");
        // Schreibe das letzte Element des Heaps and die erste Stelle
        // und lass es durchsickern
        getContent()[0] = getContent()[getLength()-1];
        getContent()[getLength()-1] = null;
        // Die Laenge des Arrays ohne null Werte wird um eins kuerzer
        l--;
        this.heapifyDown(0);
    }

    /**
     * Fuegt ein neues Element in den Heap ein
     * @param x das neue Element
     */
    public void add(T x){
        // Falls die Laenge des beschriebenen Arrays groesser oder gleich
        // der Laenge des gesamten Arrays ist, erstelle ein neues, groesseres
        // Array und fuege kopiere die Werte.
        if(getLength() >= getTotalLength())
            setContent(content);
        // Fuege das neue Element an das Ende des Heaps an und lass es nach
        // oben sickern
        getContent()[getLength()] = x;
        // Die Laenge des Arrays ohne null Werte wird um eins groesser
        l++;
        this.heapifyUp(getLength()-1);
    }

}
