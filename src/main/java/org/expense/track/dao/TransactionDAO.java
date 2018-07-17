package org.expense.track.dao;

import org.expense.track.db.DB;
import org.expense.track.db.GUIDGenerator;
import org.expense.track.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Provides data access API and hides SQL/DB.
 */
public class TransactionDAO {

	public static final String TRANSACTION = "TRANSACTION";

	// SQL to add a new entry
	private static final String ADD_SQL = "INSERT INTO " + TRANSACTION
			+ " (ID, CATEGORY, DESCRIPTION, AMOUNT, DATE) VALUES (?,?,?,?,?)";

	// SQL to update an entry by id
	private static final String UPDATE_SQL = "UPDATE " + TRANSACTION
			+ " SET CATEGORY = ?, DESCRIPTION = ?, AMOUNT = ?, "
			+ "DATE = ? WHERE ID = ?";

	// SQL to update an entry by id
	private static final String DELETE_SQL = "DELETE FROM " + TRANSACTION
			+ " WHERE ID = ?";

	// SQL to get an entry by id
	private static final String FIND_BY_ID_SQL = "SELECT ID, CATEGORY, DESCRIPTION, AMOUNT, DATE FROM "
			+ TRANSACTION + " WHERE ID = ?";

	// SQL to get entries by date range
	private static final String FIND_BY_DATE_SQL = "SELECT ID, CATEGORY, DESCRIPTION, AMOUNT, DATE FROM "
			+ TRANSACTION
			+ " WHERE DATE >= ? AND DATE <= ? ORDER BY AMOUNT DESC";

	// SQL to group and sum by category and date range
	private static final String GROUP_SUM_BY_CATEGORY = "select category, sum(amount) as \"total\", count(category) as \"count\" "
			+ "from " + TRANSACTION + " where ";

	// SQL to get entries by date range
	private static final String FIND_BY_CAT_AND_DATE_SQL = "SELECT ID, CATEGORY, DESCRIPTION, AMOUNT, DATE FROM "
			+ TRANSACTION
			+ " WHERE CATEGORY = ? AND DATE >= ? AND DATE <= ? ORDER BY DATE ASC";

	/**
	 * Returns entries by search param.
	 */
	public Collection<Object[]> findByCategory(Date start, Date end) {
		Collection<Object[]> result = new Vector<>();

		// 1) get a Connection from the DataSource
		Connection con = DB.gInstance().getConnection();

		try {
			// 2) create a PreparedStatement from a SQL string
			String sqlStr = GROUP_SUM_BY_CATEGORY +
					" DATE >= ? AND DATE <= ? GROUP BY CATEGORY";
			PreparedStatement ps = con.prepareStatement(sqlStr);
			ps.setDate(1, createDate(start));
			ps.setDate(2, createDate(end));

			// 3) execute the SQL
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {

				String category = resultSet.getString(1);
				Double amt = resultSet.getDouble(2);
				String count = resultSet.getString(3);

				result.add(new Object[] { category, amt, count });
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			DB.gInstance().returnConnection(con);
		}

		return result;
	}

	/**
	 * Returns entries by search param.
	 */
	public Collection<Transaction> findByParam(String param) {
		Collection<Transaction> result = new Vector<>();

		// 1) get a Connection from the DataSource
		Connection con = DB.gInstance().getConnection();

		try {

			// 2) create a PreparedStatement from a SQL string
			PreparedStatement ps = con.prepareStatement(buildSQLString(param));
			if (isString(param)) {
				ps.setString(1, param);
				ps.setString(2, '%' + param + '%');
				ps.setDate(3, getYearStart());
				ps.setDate(4, getYearEnd());
			}
			if (isDouble(param)) {
				ps.setDouble(1, new Double(param));
				ps.setDate(2, getYearStart());
				ps.setDate(3, getYearEnd());
			}
			if (isDate(param))
				ps.setDate(1, createDate(parseDate(param)));

			// 3) execute the SQL
			ResultSet resultSet = ps.executeQuery();
			createEntries(result, resultSet);

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			DB.gInstance().returnConnection(con);
		}

		return result;
	}

	private void createEntries(Collection<Transaction> result, ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {

			Long id = resultSet.getLong(1);
			String cat = resultSet.getString(2);
			String desc = resultSet.getString(3);
			Double amt = resultSet.getDouble(4);
			Date date = resultSet.getDate(5);

			// 4) create a new Transaction object from the column values
			// in the ResultSet
			Transaction entry = new Transaction(cat, desc, amt, date);

			// include the GUIDs
			entry.id = id;

			result.add(entry);
		}
	}

	/**
	 * Returns an entry by id.
	 */
	public Collection<Transaction> findByID(Long id) {
		Collection<Transaction> result = new Vector<>();

		// 1) get a Connection from the DataSource
		Connection con = DB.gInstance().getConnection();

		try {

			// 2) create a PreparedStatement from a SQL string
			PreparedStatement ps = con.prepareStatement(FIND_BY_ID_SQL);
			ps.setLong(1, id);

			// 3) execute the SQL
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {

				String cat = resultSet.getString(2);
				String desc = resultSet.getString(3);
				Double amt = resultSet.getDouble(4);
				Date date = resultSet.getDate(5);

				// 4) create a new Transaction object from the column values
				// in the ResultSet
				Transaction entry = new Transaction(cat, desc, amt, date);

				// include the GUIDs
				entry.id = id;

				result.add(entry);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			DB.gInstance().returnConnection(con);
		}

		return result;
	}

	/**
	 * Returns entries by date range.
	 */
	public Collection<Transaction> findByCategoryAndDate(String category,
			Date start, Date end) {
		Collection<Transaction> result = new Vector<>();

		// 1) get a Connection from the DataSource
		Connection con = DB.gInstance().getConnection();

		try {

			// 2) create a PreparedStatement from a SQL string
			PreparedStatement ps = con
					.prepareStatement(FIND_BY_CAT_AND_DATE_SQL);
			ps.setString(1, category);
			ps.setDate(2, createDate(start));
			ps.setDate(3, createDate(end));

			// 3) execute the SQL
			ResultSet resultSet = ps.executeQuery();
			createEntries(result, resultSet);

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			DB.gInstance().returnConnection(con);
		}

		return result;
	}

	/**
	 * Returns entries by date range.
	 */
	public Collection<Transaction> findByDateRange(Date start, Date end) {
		Collection<Transaction> result = new Vector<>();

		// 1) get a Connection from the DataSource
		Connection con = DB.gInstance().getConnection();

		try {

			// 2) create a PreparedStatement from a SQL string
			PreparedStatement ps = con.prepareStatement(FIND_BY_DATE_SQL);
			ps.setDate(1, createDate(start));
			ps.setDate(2, createDate(end));

			// 3) execute the SQL
			ResultSet resultSet = ps.executeQuery();
			createEntries(result, resultSet);

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			DB.gInstance().returnConnection(con);
		}

		return result;
	}

	/**
	 * Calculates the balance YTD, assumes $0.00 on Jan 1.
	 */
	public double findYearToDate(Date start, Date end) {

		// 1) get a Connection from the DataSource
		Connection con = DB.gInstance().getConnection();
		double debits = 0;
		double credits = 0;

		try {

			// 2) create a PreparedStatement from a SQL string
			PreparedStatement ps = con.prepareStatement(FIND_BY_DATE_SQL);
			ps.setDate(1, createDate(start));
			ps.setDate(2, createDate(end));

			// 3) execute the SQL
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {

				String cat = resultSet.getString(2);
				Double amt = resultSet.getDouble(4);
				if (!isCredit(cat))
					debits += amt;
				if (isCredit(cat))
					credits += amt;

			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			DB.gInstance().returnConnection(con);
		}

		return credits - debits;
	}

	// returns true of the given category represents a credit (a deposit).
	private boolean isCredit(String cat) {
		return cat.equalsIgnoreCase("deposit")
				|| cat.equalsIgnoreCase("netsalary");
	}

	/**
	 * Updates the given entry.
	 */
	public void delete(Long id) {

		// 1) get a Connection from the DataSource
		Connection con = DB.gInstance().getConnection();

		try {

			// 2) create a PreparedStatement from a SQL string
			PreparedStatement ps = con.prepareStatement(DELETE_SQL);
			ps.setLong(1, id);

			// 3) execute the SQL
			ps.executeUpdate();
			ps.close();

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			DB.gInstance().returnConnection(con);
		}

	}

	/**
	 * Updates the given entry.
	 */
	public void update(Transaction entry) {

		// 1) get a Connection from the DataSource
		Connection con = DB.gInstance().getConnection();

		try {

			// 2) create a PreparedStatement from a SQL string
			PreparedStatement ps = con.prepareStatement(UPDATE_SQL);
			ps.setString(1, entry.category);
			ps.setString(2, entry.description);
			ps.setDouble(3, entry.amount);
			ps.setDate(4, createDate(entry.date));
			ps.setLong(5, entry.id);

			// 3) execute the SQL
			ps.executeUpdate();
			ps.close();

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			DB.gInstance().returnConnection(con);
		}

	}

	/**
	 * Creates a new entry.
	 */
	public void create(Transaction entry) {

		// 1) get a Connection from the DataSource
		try (Connection con = DB.gInstance().getConnection()) {

			try {

				long id = GUIDGenerator.getGUID();

				// 2) create a PreparedStatement from a SQL string
				PreparedStatement ps = con.prepareStatement(ADD_SQL);
				ps.setLong(1, id);
				ps.setString(2, entry.category);
				ps.setString(3, entry.description);
				ps.setDouble(4, entry.amount);
				ps.setDate(5, createDate(entry.date));

				// 3) execute the SQL
				ps.executeUpdate();

			} catch (SQLException se) {
				se.printStackTrace();
			} finally {
				DB.gInstance().returnConnection(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private java.sql.Date getYearStart() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DATE, 0);
		return createDate(calendar.getTime());
	}

	private java.sql.Date getYearEnd() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DATE, 31);
		return createDate(calendar.getTime());
	}

	// converts a java.util.Date into a java.sql.Date
	private java.sql.Date createDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	private String buildSQLString(String param) {
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append("SELECT ID, CATEGORY, DESCRIPTION, AMOUNT, DATE FROM "
				+ TRANSACTION + " WHERE ");
		if (isString(param)) {
			sqlStr.append("(CATEGORY = ? OR DESCRIPTION LIKE ?) ");
			sqlStr.append("AND (DATE >= ? AND DATE <= ?) ");
		}
		if (isDouble(param)) {
			sqlStr.append("(AMOUNT = ?) ");
			sqlStr.append("AND (DATE >= ? AND DATE <= ?) ");
		}
		if (isDate(param))
			sqlStr.append("DATE >= ?");

		sqlStr.append("ORDER BY DATE ASC");

		return sqlStr.toString();
	}

	private boolean isString(String s) {
		return !isDouble(s) && !isDate(s);
	}

	private boolean isDouble(String s) {
		try {
			new Double(s);
			return true;
		} catch (NumberFormatException ne) {
			return false;
		}
	}

	private boolean isDate(String s) {
		try {
			DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			format.parse(s);
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}

	// returns a Date object from a formatted string
	private Date parseDate(String date) {
		Date parsedDate = new Date();
		try {
			DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			parsedDate = format.parse(date);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
		return parsedDate;
	}

}
