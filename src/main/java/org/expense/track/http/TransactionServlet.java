package org.expense.track.http;

import org.apache.xerces.parsers.DOMParser;
import org.expense.track.dao.TransactionDAO;
import org.expense.track.model.MonthlyResultSet;
import org.expense.track.model.Transaction;
import org.expense.track.model.TransactionResultSet;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Services requests sent to the /transactions URI.
 */
public class TransactionServlet extends HttpServlet {

    static final long serialVersionUID = 1L;

    /**
     * The following URL path parts are expected:
     * <p>
     * /transactions/ /transactions/{ID} /transactions/2008/02
     * /transactions/2008/02/15
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {

        PathFragment pathFragment = new PathFragment(
                request.getPathInfo());

        String queryString = request.getQueryString();

        String groupBy = request.getParameter("groupby");
        if (queryString != null && groupBy != null) {

            getGroupedTransactions(request, response, queryString);

        }

        if (queryString != null) {

            getTransactionsByQuery(request, response, queryString);

        }

        if (pathFragment.getFragmentCount() == PathFragment.PATH_ROOT) {

            // get transactions by current month
            getCurrentTransactions(response);

        } else if (pathFragment.getFragmentCount() == PathFragment.PATH_TWO) {

            getTransactionByID(response, pathFragment);

        } else if (pathFragment.getFragmentCount() == PathFragment.PATH_THREE
                || pathFragment.getFragmentCount() == PathFragment.PATH_FOUR) {

            getTransactionsByDate(response, pathFragment);

        }

    }

    // Returns a transaction by ID.
    private void getTransactionsByQuery(HttpServletRequest request,
                                        HttpServletResponse response, String queryString) {

        String responseXML;

        try {
            queryString = URLDecoder.decode(queryString, "UTF-8");
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
        }

        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String category = request.getParameter("category");

        if (start != null && end != null && category != null) {
            TransactionDAO dao = new TransactionDAO();
            Collection<?> entries = dao.findByCategoryAndDate(category,
                    convertDate(start), convertDate(end));
            TransactionResultSet result = new TransactionResultSet(entries);
            responseXML = result.toXML();
        } else {
            TransactionDAO dao = new TransactionDAO();
            Collection<?> entries = dao.findByParam(queryString);
            TransactionResultSet result = new TransactionResultSet(entries,
                    true);
            responseXML = result.toXML();
        }
        // include "Content-Type" and "Content-Length" response headers
        response.setContentType("application/xml");
        response.setContentLength(responseXML.length());

        try {
            try (PrintWriter writer = response.getWriter()) {
                writer.write(responseXML);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Returns a transaction by ID.
    private void getGroupedTransactions(HttpServletRequest request,
                                        HttpServletResponse response, String queryString) {

        String responseXML = "";

        try {
            queryString = URLDecoder.decode(queryString, "UTF-8");
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
        }

        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String category = request.getParameter("category");

        if (start != null && end != null && category != null) {
            TransactionDAO dao = new TransactionDAO();
            Collection<?> entries = dao.findByCategoryAndDate(category,
                    convertDate(start), convertDate(end));
            MonthlyResultSet result = new MonthlyResultSet(entries);
            responseXML = result.toXML();
        }

        // include "Content-Type" and "Content-Length" response headers
        response.setContentType("application/xml");
        response.setContentLength(responseXML.length());

        try {
            PrintWriter writer = response.getWriter();
            writer.write(responseXML);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Returns a transaction by ID.
    private void getTransactionByID(HttpServletResponse response, PathFragment fragment) {

        TransactionDAO dao = new TransactionDAO();
        Long id = Long.valueOf(fragment.getLastFragment());
        Collection<?> entries = dao.findByID(id);
        TransactionResultSet result = new TransactionResultSet(entries);
        String responseXML = result.toXML();

        // include "Content-Type" and "Content-Length" response headers
        response.setContentType("application/xml");
        response.setContentLength(responseXML.length());

        try {
            PrintWriter writer = response.getWriter();
            writer.write(responseXML);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Services requests for a list of tranactions within the current month
    private void getTransactionsByDate(HttpServletResponse response, PathFragment fragment) {

        try {
            String year = fragment.getFragment(1);
            String month = fragment.getFragment(2);
            String day = fragment.getFragment(3);

            Calendar calendar = new GregorianCalendar();
            if (day.equals(""))
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            else
                calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));

            calendar.set(Calendar.YEAR, Integer.valueOf(year));
            calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);

            Date start = calendar.getTime();
            if (day.equals(""))
                calendar.set(Calendar.DAY_OF_MONTH, 15);
            Date end = calendar.getTime();

            TransactionDAO dao = new TransactionDAO();
            Collection<?> entries = dao.findByDateRange(start, end);

            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DATE, 1);
            start = calendar.getTime();

            double ytdBalance = dao.findYearToDate(start, end);

            TransactionResultSet result = new TransactionResultSet(entries);
            result.setYearToDateBalance(ytdBalance);

            double previous = dao.findYearToDate(start, moveBackOne(end));
            result.setOneBefore(previous);

            String responseXML = result.toXML();

            // include "Content-Type" and "Content-Length" response headers
            response.setContentType("application/xml");
            response.setContentLength(responseXML.length());

            PrintWriter writer = response.getWriter();
            writer.write(responseXML);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }

    }

    // returns a Date representing one period before the given Date
    private Date moveBackOne(Date start) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(start);
        if (cal.get(Calendar.DATE) == 15) {
            cal.set(Calendar.DATE, 1);
        } else {
            cal.set(Calendar.DATE, 15);
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        }
        return cal.getTime();
    }

    // Services requests for a list of tranactions within the current month
    private void getCurrentTransactions(HttpServletResponse response) {

        TransactionDAO dao = new TransactionDAO();
        Collection<?> entries = dao.findByDateRange(getMonthBeginDate(),
                getMonthEndDate());
        TransactionResultSet result = new TransactionResultSet(entries);
        String responseXML = result.toXML();

        // include "Content-Type" and "Content-Length" response headers
        response.setContentType("application/xml");
        response.setContentLength(responseXML.length());

        try {
            PrintWriter writer = response.getWriter();
            writer.write(responseXML);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * Creates a new transaction
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) {

        try {
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(request.getInputStream()));
            Document doc = parser.getDocument();

            NodeList nodes = doc.getElementsByTagName("entry");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                NodeList children = node.getChildNodes();
                Transaction transaction = new Transaction(children);
                TransactionDAO dao = new TransactionDAO();
                dao.create(transaction);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(500);
        }
        response.setStatus(200);
    }

    /**
     * Updates a transaction.
     */
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) {

        try {
            DOMParser parser = new DOMParser();
            parser.parse(new InputSource(request.getInputStream()));
            Document doc = parser.getDocument();

            NodeList nodes = doc.getElementsByTagName("entry");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                NodeList children = node.getChildNodes();
                Transaction transaction = new Transaction(children);
                TransactionDAO dao = new TransactionDAO();
                dao.update(transaction);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(500);
        }

        response.setStatus(200);

    }

    /**
     * Deletes a transaction (by ID)
     */
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response) {

        PathFragment pathFragment = new PathFragment(
                request.getPathInfo());
        try {
            TransactionDAO dao = new TransactionDAO();
            dao.delete(Long.valueOf(pathFragment.getLastFragment()));

        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(500);
        }
        response.setStatus(200);
    }

    /**
     * Converts a date string into a Date object
     */
    private static Date convertDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",
                Locale.getDefault());
        Date dateNew = new Date();
        try {
            dateNew = formatter.parse(date);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return dateNew;
    }

    // returns the 1st day of the month
    private Date getMonthBeginDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    // returns the 15th day of the month
    private Date getMonthEndDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        return calendar.getTime();
    }

}