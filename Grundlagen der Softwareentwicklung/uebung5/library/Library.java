package library;

import util.List;

/**
 * Class representing a library.
 * 
 * @author Mathias Menninghaus
 * @see LibraryItem
 */
public class Library {

   /** List of LibraryItem objects representing the inventory of this Library. */
   List inventory;

   /**
    * Default constructor initializing the inventory of this Library.
    */
   public Library() {
      inventory = new List();
   }

   /**
    * Adds a LibraryItem into the Library.
    * 
    * @param item
    *           the LibraryItem to be added
    */
   public void addItem(LibraryItem item) {
      inventory.add(item);
   }

   /**
    * Deletes a LibraryItem from this library.
    * 
    * @param item
    *           The item that has to be deleted
    */
   public void deleteItem(LibraryItem item) {
      inventory.reset();
      int i = 0;
      while (!inventory.endpos()) {
         LibraryItem next = (LibraryItem) inventory.elem();

         if (next.equals(item)) {
            inventory.delete();
         } else {
            inventory.advance();
         }

      }
   }

   /**
    * Search for a library item which description contains the given string.
    * 
    * @param text
    *           string to find
    * @return a list of library items which descriptions contain the given
    *         string
    */
   public List search(String text) {
      List lstResult = new List();

      inventory.reset();

      while (!inventory.endpos()) {
         LibraryItem next = (LibraryItem) inventory.elem();

         String description = next.getDescription();
         if (description.contains(text)) {
            lstResult.add(next);
         }
         inventory.advance();
      }

      lstResult.reset();

      return lstResult;
   }
}
