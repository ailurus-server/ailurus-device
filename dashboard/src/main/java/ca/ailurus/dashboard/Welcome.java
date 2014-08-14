package ca.ailurus.dashboard;

import ca.ailurus.entities.DeviceSettings;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class Welcome extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DeviceSettings settings;

        try {
            settings = DeviceSettings.getSettings();
        } catch (SQLException exception) {
            throw new ServletException(exception);
        }

        boolean initialized = settings.initialized;

        if (initialized) {
            response.sendRedirect("/");
            return;
        }

        String jspFile = "/WEB-INF/welcome.jsp";
        RequestDispatcher jspDispatch = getServletContext().getRequestDispatcher(jspFile);
        jspDispatch.forward(request, response);
    }
}