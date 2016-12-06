package totalcross.crud.dao;

import java.util.List;

import totalcross.crud.model.Book;

/**
 * Interface responsável pelo acesso a implementação de Book.
 * 
 * @author Lucas Araújo - lucas.araujo@ifactory.com.br
 */
public interface BookDAO {

	/**
	 * Salva um livro.
	 * 
	 * @param book.
	 */
	public void save(Book book);

	/**
	 * Atualiza um livro.
	 * 
	 * @param book
	 */
	public void update(Long id, Book book);
	
	/**
	 * Recupera todos os livros.
	 * 
	 * @return Devolve todos os livros.
	 */
	public List<Book> findAll();
	
	/**
	 * Recupera um livro pelo seu id.
	 * 
	 * @param id
	 * @return Devolve o livro.
	 */
	public Book findById(Long id);
	
	/**
	 * Remove o livro pelo seu id.
	 * 
	 * @param id
	 */
	public void delete(Long id);
}
