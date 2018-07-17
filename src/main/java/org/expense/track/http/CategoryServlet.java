package org.expense.track.http;

import org.expense.track.dao.UtilityDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Services GET requests for /categories
 */
public class CategoryServlet extends HttpServlet {

    private static final long serialVersionUID = -1406535742904209355L;

    /**
     * Services GET requests for /categories
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {

        // used to set the datemenu to the moving month/day/year
        String movingDate = request.getParameter("moving");

        try {
            UtilityDAO dao = new UtilityDAO();

            Collection<?> entries = dao.findAllCategories();

            StringBuilder responseXML = new StringBuilder();
            responseXML.append("<rsp>");
            responseXML.append("<category>");
            responseXML.append("<name name=\"\"></name>");

            for (Object entry : entries) {
                String cat = (String) entry;
                responseXML.append("<name name=\"").append(cat).append("\">").append(firstUp(cat)).append("</name>");
            }
            responseXML.append("</category>");

            responseXML.append(getDateMenu(movingDate));

            responseXML.append("</rsp>");

            // include "Content-Type" and "Content-Length" response headers
            response.setContentType("application/xml");
            response.setContentLength(responseXML.length());

            PrintWriter writer = response.getWriter();
            writer.write(responseXML.toString());
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * Builds a date menu for the add form.
     */
    private String getDateMenu(String movingDate) {
        StringBuilder menu = new StringBuilder();
        Date d = parseDate(movingDate);
        Calendar cal = new GregorianCalendar();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);

        menu.append("<select>");

        for (int i = 1; i < 13; i++) {
            boolean selected = isMonthAndDay(i, 1, cal);
            menu.append("<option value=\"").append(i).append("-01-").append(year).append("\"");
            if (selected) {
                menu.append(" selected=\"true\"");
            }
            selected = isMonthAndDay(i, 15, cal);
            menu.append(">").append(i).append("-01-").append(year).append("</option>");
            menu.append("<option value=\"").append(i).append("-15-").append(year).append("\"");
            if (selected) {
                menu.append(" selected=\"true\"");
            }
            menu.append(">").append(i).append("-15-").append(year).append("</option>");
        }
        menu.append("</select>");
        return menu.toString();
    }

    // returns a Date object from a formatted string
    private Date parseDate(String date) {
        Date parsedDate = new Date();
        try {
            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            parsedDate = format.parse(date);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return parsedDate;
    }

    private boolean isMonthAndDay(int month, int day, Calendar cal) {
        return cal.get(Calendar.MONTH) + 1 == month
                && cal.get(Calendar.DATE) == day;
    }

    /**
     * Returns the given string with 1st character in uppercase.
     */
    private String firstUp(String str) {
        return str.substring(0, 1).toUpperCase()
                + str.substring(1, str.length());
    }

}
