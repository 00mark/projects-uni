package library;

import util.List;

/**
 * Simple usage of the Library implementation
 * 
 */
public class UseLibrary {

   /**
    * Main routine to start the program.
    * 
    * @param args
    *           the command line arguments
    */
   public static void main(String[] args) {
      Library lib = new Library();

      // create some library items
      Book li = new Book("Clash of Kings", "G.R.R. Martin");

      // add items to library
      lib.addItem(li);

      // make search
      List searchResult = lib.search("Clash");

      String description = ((LibraryItem) searchResult.elem()).getDescription();

      // print first element on console
      System.out.println(description);

   }
}
