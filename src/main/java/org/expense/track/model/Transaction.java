package org.expense.track.model;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * POJO representation of an entry in transactions.
 */
public class Transaction {

    public Long id;
    public String category;
    public String description;
    public Double amount;
    public Date date;

    private static final String ID = "id";
    private static final String CAT = "category";
    private static final String AMT = "amount";
    private static final String DESC = "description";
    private static final String DATE = "date";

    /**
     * Creates a Transaction from a NodeList
     */
    public Transaction(NodeList nodes) {
        for (int p = 0; p < nodes.getLength(); p++) {
            if (nodes.item(p).getNodeName().equals(ID))
                id = new Long(getNodeValue(nodes.item(p)));
            if (nodes.item(p).getNodeName().equals(CAT))
                category = getNodeValue(nodes.item(p));
            if (nodes.item(p).getNodeName().equals(AMT))
                amount = new Double(getNodeValue(nodes.item(p)));
            if (nodes.item(p).getNodeName().equals(DESC))
                description = getNodeValue(nodes.item(p));
            if (nodes.item(p).getNodeName().equals(DATE))
                date = parseDate(getNodeValue(nodes.item(p)));
        }
    }

    /**
     * Returns the text value of the given Node.
     */
    private static String getNodeValue(Node node) {
        NodeList nodeList = node.getChildNodes();

        int length = nodeList.getLength();
        for (int i = 0; i < length; i++) {
            Node n = nodeList.item(i);
            if (n instanceof Text) {
                Text t = (Text) n;
                return t.getNodeValue();
            }
        }
        return "";
    }

    /**
     * Creates a new Entry.
     */
    public Transaction(String category, String description, Double amount,
                       Date date) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Returns an XML representation of an entry.
     */
    public String toXML() {
        StringBuilder buff = new StringBuilder("<entry" + " id=\"" + id + "\">\n");
        buff.append("\t<category>").append(firstUp(category)).append("</category>\n");
        buff.append("\t<description>").append(description).append("</description>\n");
        buff.append("\t<amount>").append(amount).append("</amount>\n");
        buff.append("\t<date>").append(formatDate(date)).append("</date>\n");
        buff.append("</entry>\n");
        return buff.toString();
    }

    // returns the given string with 1st character in upper case
    private String firstUp(String str) {
        return str.substring(0, 1).toUpperCase()
                + str.substring(1, str.length());
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

    // returns an MM-dd-yyyy formatted string from the given Date object.
    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy",
                Locale.getDefault());
        return formatter.format(date);
    }

}
