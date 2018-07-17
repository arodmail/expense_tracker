package org.expense.track.http;

import org.expense.track.dao.TransactionDAO;
import org.expense.track.model.CategoryResultSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * Services GET requests for /reports
 */
public class ReportServlet extends HttpServlet {

    private static final long serialVersionUID = -1406535742904209355L;

    /**
     * Services GET requests for /reports
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {

        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String format = request.getParameter("format");

        TransactionDAO dao = new TransactionDAO();
        Collection<?> result = dao.findByCategory(convertDate(start),
                convertDate(end));

        CategoryResultSet resultSet = new CategoryResultSet(result);
        String responseXML;
        if (format != null && format.equals("rows")) {
            responseXML = resultSet.toXMLRow();
        } else if (format != null && format.equals("csv")) {
            responseXML = resultSet.toCSV();
        } else {
            responseXML = resultSet.toXML();
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

}
