package util;

import java.util.ConcurrentModificationException;
/**
 * Testet den ListIterator anhand der MyList Klasse
 * @version 05.28.17
 * @author Mark Hiltenkamp
 */
public class MyListTest{

    public static void main(String[] args){

        MyList<Character> list = new MyList<Character>();
        java.util.Iterator iter = list.iterator();
        boolean hasNextEmpty = iter.hasNext();
        char[] content = new char[8];

        list.add('a');
        list.add('b');
        list.add('c');
        list.add('d');
        list.add('e');
        list.add('f');
        list.add('g');
        list.add('h');

        java.util.Iterator iter2 = list.iterator();

        System.out.print("Test : hasNext()\n> ");
        // hasNext ist false bei leerer Liste, sonst true 
        System.out.println(!hasNextEmpty && iter2.hasNext());

        int i = 0;
        while(iter2.hasNext()){
            content[i] = (char)(iter2.next());
            iter2.remove();
            i++;
        }
        System.out.print("Test : next()\n> ");
        // Durch next werden alle Elemente der Liste abgegangen
        System.out.println(content[0] == 'h' && content[1] == 'g' &&
                content[2] == 'f' && content[3] == 'e' && content[4] == 'd' &&
                content[5] == 'c' && content[6] == 'b' && content[7] == 'a');

        System.out.print("Test : remove()\n> ");
        boolean empty = list.empty();
        list.add('a');
        list.add('b');
        list.add('c');
        list.add('d');
        java.util.Iterator iter3 = list.iterator();
        iter3.next();
        iter3.remove();
        iter3.next();
        iter3.next();
        iter3.remove();
        char[] content2 = new char[2];
        list.reset();
        content2[0] = list.elem();
        list.advance();
        content2[1] = list.elem();
        // Liste ist leer, wenn nach jedem next remove aufgerufen wird
        // und es wird das richtige Element geloescht
        System.out.println(empty && content2[0] == 'c' && content2[1] == 'a');


        System.out.print("Test : ConcurrentModificationException\n> ");
        // add fuehrt zu einer Exception des Iterators
        list.add('a');
        java.util.Iterator iter4 = list.iterator();
        try{
            iter3.hasNext(); 
        }catch(ConcurrentModificationException e1){
            try{
                // delete fuehrt zu einer Exception des Iterators
                list.delete();
                iter4.hasNext();
            }catch(ConcurrentModificationException e2){
                System.out.println(true);
            }
        }

        System.out.print("Test : IllegalStateException\n> ");
        list.add('a');
        list.add('b');
        java.util.Iterator iter5 = list.iterator();
        try{
            iter5.next();
            iter5.next();
            // Doppletes Loeschen fuehrt zu einer Exception
            iter5.remove();
            iter5.remove();
        }catch(IllegalStateException e3){
            System.out.println(true);
        }
    }
}
