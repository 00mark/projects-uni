package heap;

/**
 * Testet die Funktionalitaeten der GenHeap Klasse
 * @version 05.23.17
 * @author Mark Hiltenkamp
 */
public class GenHeapTest{

    public static void main(String[] args){

        GenHeap<Character> g = new GenHeap<Character>('a', 'y', 'b', 'a');
        GenHeap<Integer> g2 = new GenHeap<Integer>(0, 11, -7, 5, 18, -2, 0);
        GenHeap<String> g3 = new GenHeap<String>("as", "df", "jk");

        System.out.print("Integer und Character Heap erfolgreich initialisiert\n> ");
        System.out.println(g2.getContent() != null && g.getContent() != null);
        System.out.print("a y b a -> ");
        g.printContent();
        System.out.print("0, 11, -7, 5, 18, -2, 0 -> ");
        g2.printContent();
        System.out.println();

        System.out.print("Minimum erfolgreich ermittelt?\n> ");
        System.out.println(g.getMin().equals('a') && g2.getMin().equals(-7));
        System.out.println();

        System.out.print("Objekte erfolgreich hinzugefuegt?\n> ");
        g.add('c');
        g.add('a');
        System.out.println(g.getContent()[0].equals('a') && g.getContent()[1].equals('a')
                && g.getContent()[2].equals('a') && g.getContent()[3].equals('y')
                && g.getContent()[4].equals('c') && g.getContent()[5].equals('b'));
        System.out.print("add('c'); add('a') -> ");
        g.printContent();
        System.out.println();

        System.out.print("Objekte erfolgreich geloescht?\n> ");
        g.deleteMin();
        g.deleteMin();
        g.deleteMin();
        System.out.println(g.getContent()[0].equals('b') && g.getContent()[1].equals('y')
                && g.getContent()[2].equals('c'));
        System.out.print("deleteMin(); deleteMin(); deleteMin(); -> ");
        g.printContent();
        System.out.println();

        System.out.print("Benutzung von Comparator funktioniert?\n> ");
        library.Book b1 = new library.Book("Z", "B");
        library.Book b2 = new library.Book("A", "B");
        library.Book b3 = new library.Book("C", "B");
        library.Book b4 = new library.Book("D", "B");
        GenHeap<library.Book> g4 = new GenHeap<library.Book>(new BookComparator(), b1, b2, b3, b4);
        System.out.println(g4.getContent()[0].getTitle().equals("A")
                && g4.getContent()[1].getTitle().equals("D")
                && g4.getContent()[2].getTitle().equals("C")
                && g4.getContent()[3].getTitle().equals("Z"));
        System.out.println();
        
        System.out.print("Test : RuntimeException\n> ");
        try{
            g.deleteMin();
            g.deleteMin();
            g.deleteMin();
            g.deleteMin();
        }catch(RuntimeException e){
            System.out.println("Exception erfolgreich geworfen (deleteMin bei leerem Heap)");
        }

        System.out.print("Test : ClassCastException\n> ");
        util.List l1 = new util.List();
        util.List l2 = new util.List();
        try{
        GenHeap<util.List> g5 = new GenHeap<util.List>(l1, l2);
        }catch(ClassCastException e){
            System.out.println("");
        }
    }
}
