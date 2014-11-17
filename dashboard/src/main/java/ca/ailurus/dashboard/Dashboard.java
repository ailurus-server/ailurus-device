package ca.ailurus.dashboard;

import ca.ailurus.dashboard.entities.App;
import ca.ailurus.dashboard.entities.DeviceSettings;
import ca.ailurus.dashboard.entities.UseCase;
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
                initMockUseCases(tx);
                initMockApps(tx);
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
    private void initMockUseCases(Transaction tx) {
        UseCase[] useCases = {
            new UseCase("blog", "Blog", " to share your thoughts", UseCase.Types.Personal),
            new UseCase("profile", "Profile Page", " to showcase your work", UseCase.Types.Personal),
            new UseCase("game-server", "Game Server", " to host games for your friends", UseCase.Types.Personal),
            new UseCase("corporate-website", "Corporate Website", " to showcase your company", UseCase.Types.Business),
            new UseCase("web-server", "Web Server", " to run your own website", UseCase.Types.Programming),
            new UseCase("source-control", "Source Control", " to safely store your source code", UseCase.Types.Programming)
        };

        for (UseCase useCase: useCases) {
            tx.addUseCase(useCase);
        }
    }

    // TODO delete this after testing
    private void initMockApps(Transaction tx) {
        tx.addInstalledApp(
            new App("Wordpress",
                    "WordPress is web software you can use to create a beautiful blogs",
                    "img/apps/wordpress.png",
                    "blog",
                    true,
                    "/wordpress"));
        tx.addInstalledApp(
            new App("Minecraft",
                    "Minecraft is a popular game about building blocks.",
                    "img/apps/minecraft.png",
                    "game-server",
                    true,
                    ""));
        tx.addInstalledApp(
                new App("GitList",
                        "GitList is a simple git repository browser.",
                        "img/apps/git.png",
                        "blog source-control",
                        true,
                    "/gitlist"));
    }

}