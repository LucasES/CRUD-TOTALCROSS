package totalcross.crud.model;


/**
 * Entidade responsável pela classe Book.
 * 
 * @author Lucas Araújo
 *
 */
public class Book {

	private int id;
	
	private String title;
	
	private String isbn;
	
	private int pages;
	

	public Book() {
		super();
	}

	public Book(String title, String isbn, int pages) {
		super();
		this.title = title;
		this.isbn = isbn;
		this.pages = pages;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
}
