package ca.ailurus.dashboard;

import ca.ailurus.entities.DeviceSettings;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class Dashboard extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
        DeviceSettings settings;

        try {
            settings = DeviceSettings.getSettings();
        } catch (SQLException exception) {
            throw new ServletException(exception);
        }

        if (!settings.initialized) {
            response.sendRedirect("/welcome");
            return;
        }
        */

        String jspFile = "/WEB-INF/dashboard.jsp";
        RequestDispatcher jspDispatch = getServletContext().getRequestDispatcher(jspFile);
        jspDispatch.forward(request, response);
    }
}