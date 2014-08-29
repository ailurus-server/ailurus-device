package ca.ailurus.dashboard;

import ca.ailurus.dashboard.entities.DeviceSettings;
import ca.ailurus.dashboard.managers.DeviceSettingsManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Singleton
public class Welcome extends HttpServlet {
    private static final String WELCOME_JSP_PATH = "/WEB-INF/jsp/welcome.jsp";

    private DeviceSettingsManager settingsManager;

    @Inject
    public Welcome(DeviceSettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DeviceSettings settings;
        try {
             settings = settingsManager.getSettings();
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