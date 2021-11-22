package heap;

import java.util.Comparator;
import library.Book;
public class BookComparator implements Comparator<Book>{

    public int compare(Book b1, Book b2){
        return b1.getTitle().compareTo(b2.getTitle());
    }
}
