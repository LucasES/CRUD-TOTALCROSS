package totalcross.crud.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import totalcross.crud.dao.BookDAO;
import totalcross.crud.model.Book;
import totalcross.crud.util.ConnectionFactory;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

/**
 * Classe responsável pela implementação do BookDAO.
 * 
 * @author Lucas Araújo - lucas.araujo@ifactory.com.br
 */
public class BookDAOJDBCImpl implements BookDAO{

	private static final String INSERT = "INSERT INTO BOOK "
			+ "(title, isbn, pages, publicationYear, editionNumber) "
			+ "VALUES (:title, :isbn, :pages, :publicationYear, :editionNumber)";

	private static final String UPDATE = "UPDATE BOOK SET title = :title, "
			+ "isbn = :isbn, pages = :pages, publicationYear = :publicationYear, editionNumber = :editionNumber "
			+ "WHERE ID = :ID";

	private static final String DELETE = "DELETE FROM BOOK WHERE ID = :ID";

	private static final String FIND_ALL = "SELECT * FROM BOOK";
	
	private static final String FIND_BY_ID = "SELECT * FROM BOOK WHERE ID = :ID";
	
	private Statement statement;
	
	@Override
	public void save(Book book) {
		statement = null;
		try {
			PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(INSERT);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getIsbn());
			ps.setInt(3, book.getPages());
			ps.setInt(4, book.getPublicationYear());
			ps.setInt(5, book.getEditionNumber());
			
			int returned = ps.executeUpdate();

			if(returned > 0){
				System.out.printf("The book was saved with success!");
			}else{
				System.out.println("Failed to save a book!");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void update(Long id, Book book) {
		statement = null;
		try {
			PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(UPDATE);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getIsbn());
			ps.setInt(3, book.getPages());
			ps.setInt(4, book.getPublicationYear());
			ps.setInt(5, book.getEditionNumber());
			ps.setLong(6, id);
			
			int returned = ps.executeUpdate();

			if(returned > 0){
				System.out.printf("The book was saved with success!");
			}else{
				System.out.println("Failed to save a book!");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public List<Book> findAll() {
		List<Book> bookList = new ArrayList<Book>();
		statement = null;
		Book book = null;
		try {
			PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(FIND_ALL);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				book = BookRowMapper(rs);
				bookList.add(book);
			}
			return bookList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return bookList;
	}

	@Override
	public Book findById(Long id) {
		statement = null;
		Book book = null;
		try {
			PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(FIND_BY_ID);
			ps.setLong(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				book = BookRowMapper(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return book;
	}

	@Override
	public void delete(Long id) {
		statement = null;
		try {
			PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(DELETE);
			ps.setLong(1, id);
			
			int returned = ps.executeUpdate();

			if(returned > 0){
				System.out.printf("The book was saved with success!");
			}else{
				System.out.println("Failed to save a book!");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * RowMapper para a entidade de Book.
	 * 
	 * @param rs
	 * @return Book com seus dados.
	 */
	private Book BookRowMapper(ResultSet rs){
		Book book = new Book();
		
		try {
			book.setIsbn(rs.getString("isbn"));
			book.setTitle(rs.getString("title"));
			book.setPages(rs.getInt("pages"));
			book.setPublicationYear(rs.getInt("publicationYear"));
			book.setEditionNumber(rs.getInt("editionNumber"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return book;
		
	}
}
