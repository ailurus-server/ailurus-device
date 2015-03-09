package ca.ailurus.dashboard;

import ca.ailurus.dashboard.transaction.TransactionMaker;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ca.ailurus.dashboard.transaction.Transaction;

@Singleton
public class WelcomeServlet extends HttpServlet {
    private static final String WELCOME_JSP_PATH = "/WEB-INF/jsp/welcome.jsp";

    private TransactionMaker transactionMaker;

    @Inject
    public WelcomeServlet(TransactionMaker transactionMaker) {
        this.transactionMaker = transactionMaker;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Transaction tx = transactionMaker.make()) {
            if (tx.hasSettings()) {
                response.sendRedirect("/dashboard");
                return;
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(WELCOME_JSP_PATH);
        dispatcher.forward(request, response);
    }
}