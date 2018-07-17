package org.expense.track.test;

import org.expense.track.dao.TransactionDAO;
import org.expense.track.model.CategoryResultSet;
import org.expense.track.model.Transaction;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public class TransactionDAOTest {

    public static void main(String args[]) {
        testSearchByDateRange();
        testSearchByCategories();
    }

    /**
     *
     */
    private static void testSearchByCategories() {
        TransactionDAO dao = new TransactionDAO();
        Collection<?> result = dao.findByCategory(convertDate("01012009"),
                convertDate("12012009"));
        CategoryResultSet resultSet = new CategoryResultSet(result);
        System.out.println(resultSet.toXML());
    }

    /**
     *
     */
    private static void testSearchByDateRange() {
        TransactionDAO dao = new TransactionDAO();
        Collection<Transaction> result = dao.findByDateRange(convertDate("01012004"),
                convertDate("01012005"));
        double total = 0;
        StringBuilder buff = new StringBuilder();
        for (Transaction entry: result) {
            buff.append(entry.toXML());
            total += entry.amount;
        }
        DecimalFormat df = new DecimalFormat("#######.##");
        System.out.println("<rsp total=\"" + df.format(total) + "\">\n"
                + buff.toString() + "\n</rsp>");
    }

    /**
     *
     */
    private static Date convertDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy",
                Locale.getDefault());
        Date dateNew = new Date();
        try {
            dateNew = formatter.parse(date);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return dateNew;
    }

}
