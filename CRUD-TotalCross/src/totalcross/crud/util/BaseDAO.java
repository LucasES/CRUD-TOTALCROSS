package totalcross.crud.util;

import java.sql.SQLException;

import totalcross.sql.PreparedStatement;

public class BaseDAO {
	
	private static final String CREATE_TABLE_BOOK = "create table if not exists book "
			+ "(ID INTEGER PRIMARY KEY , title varchar, isbn varchar, pages integer, publicationYear integer, editionNumber integer)";
	
	public BaseDAO() {
	}

	public void createTables() {
		PreparedStatement ps= null;
		try {
			ps =ConnectionFactory.getConnection().prepareStatement(CREATE_TABLE_BOOK);

			int returned = ps.executeUpdate();
			if(returned > 0){
				System.out.printf("The book table was create with success!");
			}else{
				System.out.println("Failed to create book table!");
			} 
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
}
