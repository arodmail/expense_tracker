package org.expense.track.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * A Singleton to get a connection to the db.
 */
public class DB {

	private Stack<Connection> connectionPool = new Stack<>();

	/**
	 * The singleton instance.
	 */
	private static DB instance;

	/**
	 * Creates a new DB.
	 */
	private DB() {
	}

	/**
	 * Returns the singleton instance of the DB.
	 * 
	 * @return The one instance of DB.
	 */
	public static synchronized DB gInstance() {
		if (instance == null)
			instance = new DB();
		return instance;
	}

	/**
	 * Returns a Connection from the pool. Grows the pool if needed, and returns new Connection.
	 */
	public Connection getConnection() {
		Connection con = null;
		if (!connectionPool.empty())
			con = connectionPool.pop();
		if (con != null)
			return con;
		else {
			growPool();
			con = connectionPool.pop();
		}
		return con;
	}

	/**
	 * Returns a Connection to the pool.
	 */
	public void returnConnection(Connection con) {
		// connectionPool.push(con);
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Grows the pool.
	 */
	private void growPool() {
		int growBy = 2;
		for (int i = 0; i < growBy; i++) {
			connectionPool.push(getNewConnection());
		}
	}

	/**
	 * Returns a new Connection.
	 */
	private Connection getNewConnection() {
		try {
			String jdbcDriver = "com.mysql.jdbc.Driver";
			String jdbcURL = "jdbc:mysql:///finance";
			String dbUser = "root";
			String dbPass = "secret";
			Class.forName(jdbcDriver).newInstance();
			return DriverManager.getConnection(jdbcURL, dbUser,
					dbPass);
		} catch (SQLException | InstantiationException | ClassNotFoundException | IllegalAccessException sqe) {
			sqe.printStackTrace();
		}
		return null;
	}

}
