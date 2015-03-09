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
                response.sendRedirect("/welcome");
                initApps(tx);
                //initMockDevice(tx);
                //initMockUsers(tx);
                tx.commit();
                return;
                // initMockDevice(tx);
                // initMockUsers(tx);
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
    private void initApps(Transaction tx) {
        tx.addApp(
            new App("Wordpress", "wordpress",
                    "WordPress is web software you can use to create a beautiful blogs",
                    "img/apps/wordpress.png",
                    "blog",
                    false,
                    -1,
                    true,
                    "/wordpress"));
        tx.addApp(
            new App("Minecraft", "minecraft",
                    "Minecraft is a popular game about building blocks.",
                    "img/apps/minecraft.png",
                    "game-server",
                    false,
                    -1,
                    true,
                    ""));
        tx.addApp(
            new App("GitList", "gitlist",
                    "GitList is a simple git repository browser.",
                    "img/apps/git.png",
                    "blog source-control",
                    false,
                    -1,
                    true,
                    "/gitlist"));
        tx.addApp(
                new App("Owncloud", "owncloud",
                        "Owncloud is a personal cloud server.",
                        "img/apps/owncloud.png",
                        "cloud privacy",
                        false,
                        -1,
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