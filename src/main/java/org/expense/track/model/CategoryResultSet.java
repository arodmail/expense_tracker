package org.expense.track.model;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

/**
 * Holder for the result of a transactions search, grouped by category and summed.
 */	
public class CategoryResultSet {
	
	// A Collection of Object[] {String, Double, String}
	private Collection<?> results;

	private static final String XML_DECLARATION =
		"<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n";

	/**
	 * Creates a new CategoryResultSet.
	 */	
	public CategoryResultSet(Collection<?> results) {
		this.results = results;
	}

	/**
	 * Returns an xml representation of a result set.
	 */	
	public String toXML() {

		DecimalFormat df = new DecimalFormat("###,###,###.00");

		StringBuilder buff = new StringBuilder();
		buff.append(XML_DECLARATION);
		buff.append("<rsp>\n");

		for (Object result : results) {

			Object[] row = (Object[]) result;
			String cat = (String) row[0];
			Double sum = (Double) row[1];
			String count = (String) row[2];

			buff.append("<").append(cat.toLowerCase()).append(" count=\"").append(count).append("\"").append(">");
			buff.append(df.format(sum));
			buff.append("</").append(cat.toLowerCase()).append(">\n");
		}
		buff.append("</rsp>\n");
		return buff.toString();
	
	}

	/**
	 * Returns an xml representation of a result set.
	 */	
	public String toXMLRow() {

		DecimalFormat df = new DecimalFormat("#########.00");

		StringBuilder buff = new StringBuilder();
		buff.append(XML_DECLARATION);
		buff.append("<rsp>\n");
		int num = 0;
		Iterator<?> it = results.iterator();
		buff.append("<row>\n");
		while (it.hasNext()) {

			Object[] row	= (Object[])it.next();
			String cat		= (String)row[0];
			Double sum		= (Double)row[1];
			String count	= (String)row[2];

			buff.append("<").append(firstUp(cat.toLowerCase())).append(" count=\"").append(count).append("\"").append(">");
			buff.append(df.format(sum));
			buff.append("</").append(firstUp(cat.toLowerCase())).append(">\n");
			num++;
			if (num == 4 || !it.hasNext()) {
				buff.append("</row>\n");
				num = 0;
				if (it.hasNext()) {
					buff.append("<row>\n");				
				}
			}
			
		}
		buff.append("</rsp>\n");
		return buff.toString();
	
	}

	/**
     * Returns an csv representation of a result set.
     */ 
    public String toCSV() {

        DecimalFormat df = new DecimalFormat("#########.##");

        StringBuilder buff = new StringBuilder();
        buff.append(XML_DECLARATION);
        buff.append("<rsp>\n");

		for (Object result : results) {

			Object[] row = (Object[]) result;
			String cat = (String) row[0];
			Double sum = (Double) row[1];

			buff.append(cat.toLowerCase()).append("\t");
			buff.append(df.format(sum));
			buff.append("\n");
		}
        buff.append("</rsp>\n");
        return buff.toString();
    
    }

	/**
	 * Returns the given string with 1st character in uppercase.
	 */
	private String firstUp(String str) {
		return str.substring(0, 1).toUpperCase()+str.substring(1, str.length());
	}
}
