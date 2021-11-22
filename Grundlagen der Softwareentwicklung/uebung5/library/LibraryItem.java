package library;

/**
 * Class representing a lendable object in the library.
 * 
 * @author Mathias Menninghaus
 * @see Library
 */
public abstract class LibraryItem {

	/** Flag indicating the library item is 'borrowed' (true) or not (false). */
	private boolean isBorrowed;

	/**
	 * Default constuctor setting this library item to be 'not borrowed'.
	 */
	public LibraryItem() {
		isBorrowed = false;
	}

	/**
	 * Returns wether the library item is 'borrowed' (true) or 'not borrowed'
	 * (false).
	 * 
	 * @return true if object is borrowed, false otherwise
	 */
	public boolean isBorrowed() {
		return isBorrowed;
	}

	/**
	 * Sets the status of the library item.
	 * 
	 * @param isBorrowed
	 *            if true this library item will be set to status 'borrowed',
	 *            otherwise it will be set to status 'not borrowed'
	 */
	public void setBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}

	/**
	 * Returns a description of the library item. The method
	 * {@link Library#search(String)} will search in this description to find a
	 * matching LibraryItem.
	 * 
	 * @return the description of the library item
	 */
	public abstract String getDescription();
}
