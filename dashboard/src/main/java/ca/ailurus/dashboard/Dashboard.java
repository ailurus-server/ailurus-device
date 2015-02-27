package ca.ailurus.dashboard;

import ca.ailurus.dashboard.entities.App;
import ca.ailurus.dashboard.entities.DeviceSettings;
import ca.ailurus.dashboard.entities.User;
import ca.ailurus.dashboard.transaction.Transaction;
import ca.ailurus.dashboard.transaction.TransactionMaker;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import java.io.IOException;

@Singleton
public class Dashboard extends HttpServlet {
    private static final String DASHBOARD_JSP_PATH = "/WEB-INF/jsp/dashboard.jsp";

    private TransactionMaker transactionMaker;

    @Inject
    public Dashboard(TransactionMaker transactionMaker) {
        this.transactionMaker = transactionMaker;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Transaction tx = transactionMaker.make()) {
            if (!tx.hasSettings()) {
            // TODO use this to redirect to welcome screen
            //    response.sendRedirect("/welcome");
            //    return;
                initMockDevice(tx);
                initMockApps(tx);
                initMockUsers(tx);
                tx.commit();
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(DASHBOARD_JSP_PATH);
        dispatcher.forward(request, response);
    }

    // TODO delete this after testing
    private void initMockDevice(Transaction tx) {
        if (tx.hasSettings()) {
            throw new BadRequestException("Device has already been initialized.");
        }

        User user = new User();
        user.name = "name";
        user.password = "password";
        user.email = "me@ailurus.ca";
        tx.addUser(user);

        DeviceSettings settings = new DeviceSettings();
        settings.url = "me.ailurus.ca";
        tx.createSettings(settings);
    }

    // TODO delete this after testing
    private void initMockApps(Transaction tx) {
        tx.addApp(
            new App("Wordpress",
                    "WordPress is web software you can use to create a beautiful blogs",
                    "img/apps/wordpress.png",
                    "blog",
                    false,
                    -1,
                    true,
                    "/wordpress"));
        tx.addApp(
            new App("Minecraft",
                    "Minecraft is a popular game about building blocks.",
                    "img/apps/minecraft.png",
                    "game-server",
                    false,
                    5,
                    true,
                    ""));
        tx.addApp(
            new App("GitList",
                    "GitList is a simple git repository browser.",
                    "img/apps/git.png",
                    "blog source-control",
                    true,
                    -1,
                    true,
                    "/gitlist"));
        tx.addApp(
                new App("Owncloud",
                        "Owncloud is a personal cloud server.",
                        "img/apps/owncloud.png",
                        "cloud privacy",
                        true,
                        48,
                        false,
                        "/owncloud"));
    }

    // TODO delete this after testing
    private void initMockUsers(Transaction tx) {
        User user = new User();
        user.name = "asdf";
        user.password = "asdfasdf";
        user.email = "asdf@ailurus.ca";
        tx.addUser(user);
    }

}