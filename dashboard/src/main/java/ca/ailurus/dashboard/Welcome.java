package ca.ailurus.dashboard;

import ca.ailurus.dashboard.entities.DeviceSettings;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Welcome", urlPatterns = {"/welcome"})
public class Welcome extends HttpServlet {
    private static final String WELCOME_JSP_PATH = "/WEB-INF/jsp/welcome.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DeviceSettings settings;
        try {
             settings = DeviceSettings.getSettings();
        } catch(SQLException e) {
            throw new ServletException(e);
        }

        if (settings.initialized) {
            response.sendRedirect("");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(WELCOME_JSP_PATH);
        dispatcher.forward(request, response);
    }
}