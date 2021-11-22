package library;

/**
 * Class representing a book in the library.
 * 
 * @author Mathias Menninghaus
 */
public class Book extends LibraryItem {

	/** The title of the book. */
	private String title;
	/** The name of the author of the Book. */
	private String author;

	/**
	 * Constructor of a book.
	 * 
	 * @param title
	 *            the titel of the book
	 * @param author
	 *            name of the author
	 */
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
	}

	/**
	 * Returns the title of the book.
	 * 
	 * @return the title of the book
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the name of the author of the book.
	 * 
	 * @return name of the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Short description of the book.
	 * 
	 * @return the description of the book
	 */
	@Override
	public String getDescription() {
		return this.getTitle() + " by " + this.getAuthor();
	}
}
