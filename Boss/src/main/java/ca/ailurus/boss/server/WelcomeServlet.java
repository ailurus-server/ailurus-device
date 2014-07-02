package ca.ailurus.boss.server;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class WelcomeServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        boolean isInitialized = false;
        try (Storage storage = new Storage()) {
            isInitialized = storage.isInitialized();
        }

        PrintWriter writer = response.getWriter();
        writer.print(
                "<!DOCTYPE html>\n" +
                "<html class=\"expand\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Ailurus Boss</title>\n" +
                "    <script src=\"boss/boss.nocache.js\"></script>\n" +
                "    <script>\n" +
                "        var isInitialized = " + isInitialized + ";\n" +
                "    </script>\n" +
                "</head>\n" +
                "<body class=\"expand\">\n" +
                "<iframe src=\"javascript:''\" id=\"__gwt_historyFrame\" tabIndex='-1' style=\"position:absolute;width:0;height:0;border:0\"></iframe>\n" +
                "    <noscript>\n" +
                "        <div style=\"width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; border: 1px solid red; padding: 4px; font-family: sans-serif\">\n" +
                "            Your web browser must have JavaScript enabled\n" +
                "            in order for this application to display correctly.\n" +
                "        </div>\n" +
                "    </noscript>\n" +
                "</body>\n" +
                "</html>");
    }
}
