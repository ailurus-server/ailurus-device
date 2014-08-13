package ca.ailurus.boss.server;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class WelcomeServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/index.jsp");

        boolean isInitialized = false;
        try (Storage storage = new Storage()) {
            isInitialized = storage.isInitialized();
        }

        request.setAttribute("isInitialized", isInitialized);
        if (!isInitialized) {
            dispatcher.forward(request, response);
            return;
        }

        String userId = null;

        HttpSession session = request.getSession();
        synchronized (session) {
            userId = (String) session.getAttribute("userId");
        }

        request.setAttribute("userId", userId);
        dispatcher.forward(request, response);
    }
}
