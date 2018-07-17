package org.expense.track.model;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

/**
 * Used to pass results from searches to the lower tier.
 */
public class MonthlyResultSet {

    private Collection<?> results;
    private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n";

    /**
     * Creates a new TransactionResultSet.
     */
    public MonthlyResultSet(Collection<?> results) {
        this.results = results;
    }

    /**
     * Returns an xml representation of a result set.
     */
    public String toXML() {

        StringBuilder buff = new StringBuilder();

        double total = 0;

        for (int i = 0; i < 12; i++) { // for jan-dec
            String[] totalCount = getMonthTotalAndCount(i, results);
            String month = getMonth(i);
            buff.append("<entry id=\"").append(i).append("\">");
            buff.append("<month>").append(month).append("</month>");
            buff.append("<count>").append(totalCount[0]).append("</count>");
            buff.append("<amount>").append(totalCount[1]).append("</amount>");
            buff.append("</entry>");

        }

        for (Object result : results) {
            Transaction entry = (Transaction) result;
            total += entry.amount;
        }

        double average;
        int maxMonth = getMaxMonth(results);
        average = total / maxMonth;

        DecimalFormat df = new DecimalFormat("#######.##");

        StringBuilder xml = new StringBuilder(XML_DECLARATION);
        xml.append("<rsp ");
        xml.append("total=\"").append(df.format(total)).append("\" ");
        xml.append("count=\"").append(results.size()).append("\" ");
        xml.append("average=\"").append(df.format(average)).append("\" ");

        xml.append(">\n");
        xml.append(buff.toString());

        xml.append("\n</rsp>");

        return xml.toString();
    }

    private String[] getMonthTotalAndCount(int month, Collection<?> results) {
        int count = 0;
        double total = 0;
        String[] totalCount = {"", ""};
        for (Object result : results) {
            Transaction entry = (Transaction) result;
            Calendar cal = new GregorianCalendar();
            cal.setTime(entry.date);
            if (cal.get(Calendar.MONTH) == month) { // match month (Jan = 0)
                count++;
                total += entry.amount;
            }
        }
        totalCount[0] = Integer.toString(count);
        DecimalFormat df = new DecimalFormat("#######.##");
        totalCount[1] = df.format(total);
        return totalCount;
    }

    private String getMonth(int i) {
        switch (i) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
        }
        return "";
    }

    private int getMaxMonth(Collection<?> results) {
        int max = 0;
        for (Object result : results) {
            Transaction entry = (Transaction) result;
            Calendar cal = new GregorianCalendar();
            cal.setTime(entry.date);
            int thisMonth = cal.get(Calendar.MONTH);
            if (thisMonth > max) {
                max = thisMonth;
            }
        }
        return max + 1;
    }

}
