package library;

/**
 * Class representing a BluRay in a Library.
 * 
 * @author Mathias Menninghaus
 */
public class BluRay extends LibraryItem {

   /** The title of this BluRay */
   private String title;
   /** The name of the director of this BluRay */
   private String director;

   /**
    * Constructor of a BluRay.
    * 
    * @param title
    *           the titel of the BluRay
    * @param director
    *           name of the director
    */
   public BluRay(String title, String director) {
      this.title = title;
      this.director = director;
   }

   /**
    * Returns the title of the BluRay.
    * 
    * @return the title of the BluRay
    */
   public String getTitle() {
      return this.title;
   }

   /**
    * Returns the name of the director of the BluRay.
    * 
    * @return name of the director
    */
   public String getDirector() {
      return this.director;
   }

   /**
    * Short description of the BluRay.
    * 
    * @return the description of the BluRay
    */
   @Override
   public String getDescription() {
      return this.getTitle() + " " + this.getDirector();
   }
}
