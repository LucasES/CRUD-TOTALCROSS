package totalcross.crud.util;

import java.sql.SQLException;

import totalcross.sql.Connection;
import totalcross.sql.DriverManager;
import totalcross.sys.Convert;
import totalcross.sys.Settings;

/**
 * Utilizando padrão Singleton para realizar a conexão com o SQLite.
 * 
 * @author Lucas Araújo
 */
public class ConnectionFactory {

	private static final String DATABASE = "crud-totalcross.db";
	private static ConnectionFactory instance = new ConnectionFactory();

	/**
	 * Método privado para criar uma conexão com o banco.
	 * 
	 * @return Conexão com o SQLite.
	 */
	private Connection createConnection() {
		Connection con = null;

		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + Convert.appendPath(Settings.appPath, DATABASE));
						
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return con;
	}

	/**
	 * @return Instância da conexão com o SQLite.
	 */
	public static Connection getConnection() {
		return instance.createConnection();
	}
}
