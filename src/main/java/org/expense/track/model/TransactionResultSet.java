package org.expense.track.model;

import java.text.DecimalFormat;
import java.util.Collection;

/**
 * Used to pass results from searches to the lower tier.
 */
public class TransactionResultSet {

	private Collection<?> results;
	private boolean calcTotal = false;
	private double yearToDateBalance = 0;
	private double oneBefore = 0;

	private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n";

	/**
	 * Creates a new TransactionResultSet.
	 */
	public TransactionResultSet(Collection<?> results) {
		this.results = results;
	}

	/**
	 * Creates a new TransactionResultSet.
	 */
	public TransactionResultSet(Collection<?> results, boolean calcTotal) {
		this.results = results;
		this.calcTotal = calcTotal;
	}

	/**
	 * Sets the year to date balance.
	 */
	public void setYearToDateBalance(double yearToDate) {
		this.yearToDateBalance = yearToDate;
	}

	/**
	 * Sets the year to date balance.
	 */
	public void setOneBefore(double oneBefore) {
		this.oneBefore = oneBefore;
	}

	/**
	 * Returns an xml representation of a result set.
	 */
	public String toXML() {

		StringBuilder buff = new StringBuilder();

		double total = 0;
		double debits = 0;
		double credits = 0;

		for (Object result : results) {

			Transaction entry = (Transaction) result;

			if (!isCredit(entry.category))
				debits += entry.amount;
			if (isCredit(entry.category))
				credits += entry.amount;

			buff.append(entry.toXML());
			if (calcTotal)
				total += entry.amount;
		}

		DecimalFormat df = new DecimalFormat("#######.##");

		StringBuilder xml = new StringBuilder(XML_DECLARATION);
		xml.append("<rsp ");
		xml.append("total=\"").append(df.format(total)).append("\" ");
		xml.append("count=\"").append(results.size()).append("\" ");
		xml.append("debits=\"").append(df.format(debits)).append("\" ");
		xml.append("credits=\"").append(df.format(credits)).append("\" ");
		xml.append("ytd-balance=\"").append(df.format(yearToDateBalance)).append("\" ");
		xml.append("previous=\"").append(df.format(oneBefore)).append("\"");
		xml.append(">\n");
		xml.append(buff.toString());
		xml.append("\n</rsp>");

		return xml.toString();
	}

	// returns true of the given category represents a credit (a deposit).
	private boolean isCredit(String cat) {
		return cat.equalsIgnoreCase("deposit")
				|| cat.equalsIgnoreCase("netsalary");
	}
}
