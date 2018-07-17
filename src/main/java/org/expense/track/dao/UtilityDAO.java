package org.expense.track.dao;

import org.expense.track.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides helper methods for ancillary db lookups.
 */
public class UtilityDAO {

	// SQL to get category names
	private static final String GET_CATEGORIES_SQL = "SELECT NAME FROM CATEGORY ORDER BY NAME ASC";

	/**
	 * Returns all available expense categories.
	 */
	public List<String> findAllCategories() {
		List<String> result = new ArrayList<>();

		// 1) get a Connection from the DataSource
		Connection con = DB.gInstance().getConnection();

		try {

			// 2) create a PreparedStatement from a SQL string
			PreparedStatement ps = con.prepareStatement(GET_CATEGORIES_SQL);

			// 3) execute the SQL
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {

				String cat = resultSet.getString(1);
				result.add(cat);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			DB.gInstance().returnConnection(con);
		}

		return result;
	}

}
