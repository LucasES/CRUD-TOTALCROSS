package totalcross.crud.util;

import java.sql.SQLException;

import totalcross.sql.Statement;

public class BaseDAO {
	
	private static final String CREATE_TABLE_BOOK = "create table if not exists book "
			+ "(id integer primary key autoincrement, title varchar, isbn varchar, pages integer, publicationYear integer, pages editionNumber)";
	
	private Statement statement;

	public BaseDAO() {
	}

	@SuppressWarnings("static-access")
	public void createTables() {
		statement = null;
		try {
			statement = (Statement) new ConnectionFactory().getConnection().createStatement();

			statement.execute(CREATE_TABLE_BOOK);
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
}
