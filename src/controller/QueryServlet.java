package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import model.GetQueryResults;
import utils.Constants;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/QueryServlet")
public class QueryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public boolean querySent = false;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.println("<!DOCTYPE html><html>");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\" />");
            writer.println("<title>Monopoly Servlet</title>");
            writer.println("</head>");
            writer.println("<body>");
            /* + "\" method=\"get" */
            writer.println(
                    "<form method=\"post\" action=\"QueryServlet\">\r\n" + "<h1>Welcome to Search Query Monopoly!</h1>\r\n"
                            + "<textarea name=\"query\" cols=\"50\" rows=\"1\">"
                            + "Enter a common English noun" + "</textarea>\r\n"
                            + "<br />\r\n" + "<input type=\"submit\" />\r\n" + "</form>");

//	        if (querySent)
//	            writer.println("<form method=\"get\" action=\"" + Constants.MONOPOLY_URL + "\"></form>\r\n");
            writer.println("</body>");
            writer.println("</html>");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String query = request.getParameter("query");
        query.trim();
        try {
            GetQueryResults.setup(Constants.START, Constants.END, query);
            for (int i = Constants.START; i < Constants.END; i++) {
                JSONArray ja = GetQueryResults.pullShell(10, i * 10 + 1); // errsAllowed, pg
                GetQueryResults.writeToFileFormatted(ja, i, Constants.END);
            }
        } catch (TimeoutException ignore) {

        }
        redirect(request, response, GetQueryResults.cleanup(Constants.START, Constants.END));
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, boolean flag) throws IOException {
        if (flag) {
            try (PrintWriter writer = response.getWriter()) {
                writer.println("<!DOCTYPE html><html>");
                writer.println("<head>");
                writer.println("<meta charset=\"UTF-8\" />");
                writer.println("<title>Monopoly Servlet</title>");
                writer.println("</head>");
                writer.println("<body>");
                writer.println(
                        "<meta http-equiv=\"Refresh\" content=\"0; url= " + Constants.MONOPOLY_URL + "\" />\r\n");
                writer.println("</body>");
                writer.println("</html>");
            }
        } else {
            try (PrintWriter writer = response.getWriter()) {
                writer.println("<!DOCTYPE html><html>");
                writer.println("<head>");
                writer.println("<meta charset=\"UTF-8\" />");
                writer.println("<title>Monopoly Servlet</title>");
                writer.println("</head>");
                writer.println("<body>");
                writer.println("<h3>Error: invalid query. Return to the previous page and submit a different query.</h3>");
                writer.println("</body>");
                writer.println("</html>");
            }
        }
    }

}
