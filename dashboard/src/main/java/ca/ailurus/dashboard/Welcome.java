package ca.ailurus.dashboard;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Welcome extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean firstBoot = true;   // TODO changes this to read from database

        if (!firstBoot) {
            response.sendRedirect("/");
            return;
        }

        String jspFile = "/WEB-INF/welcome.jsp";
        RequestDispatcher jspDispatch = getServletContext().getRequestDispatcher(jspFile);
        jspDispatch.forward(request, response);
    }
}