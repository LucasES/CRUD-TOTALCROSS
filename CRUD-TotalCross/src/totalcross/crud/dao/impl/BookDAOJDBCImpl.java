package totalcross.crud.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import totalcross.crud.model.Book;
import totalcross.crud.util.ConnectionFactory;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;

/**
 * Classe responsável pela implementação do BookDAO.
 * 
 * @author Lucas Araújo - lucas.araujo@ifactory.com.br
 */
public class BookDAOJDBCImpl{
	
	private static final String INSERT = "INSERT INTO BOOK "
			+ "(title, isbn, pages) VALUES (:title, :isbn, :pages)";

	private static final String UPDATE = "UPDATE BOOK SET title = :title, "
			+ "isbn = :isbn, pages = :pages WHERE ID = :ID";

	private static final String DELETE = "DELETE FROM BOOK WHERE ID = :ID";

	private static final String FIND_ALL = "SELECT * FROM BOOK";
	
	private static final String FIND_BY_ID = "SELECT * FROM BOOK WHERE ID = :ID";
	
	public void save(Book book) {
		PreparedStatement ps= null;
		try {
			ps = ConnectionFactory.getConnection().prepareStatement(INSERT);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getIsbn());
			ps.setInt(3, book.getPages());
			
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
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void update(Long id, Book book) {
		PreparedStatement ps= null;
		try {
			ps = ConnectionFactory.getConnection().prepareStatement(UPDATE);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getIsbn());
			ps.setInt(3, book.getPages());
			ps.setLong(4, id);
			
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
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public List<Book> findAll() {
		List<Book> bookList = new ArrayList<Book>();
		Book book = null;
		PreparedStatement ps= null;
		try {
			ps = ConnectionFactory.getConnection().prepareStatement(FIND_ALL);

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
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return bookList;
	}

	public Book findById(String id) {
		PreparedStatement ps= null;
		Book book = null;
		try {
			ps = ConnectionFactory.getConnection().prepareStatement(FIND_BY_ID);
			ps.setString(1, id);
			
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
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return book;
	}

	public void delete(String id) {
		PreparedStatement ps= null;
		try {
			ps = ConnectionFactory.getConnection().prepareStatement(DELETE);
			ps.setString(1, id);
			
			int returned = ps.executeUpdate();

			if(returned > 0){
				System.out.printf("The book was deleted with success!");
			}else{
				System.out.println("Failed to deleted a book!");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return book;
		
	}
}
