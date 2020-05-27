package server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/my-app/*"})
public class Servlet extends HttpServlet {

    private static int counter = 0;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String request = req.getParameterNames().nextElement();
        if (request.equals("randValue")) {

            String[] twoNumbers = req.getParameter("randValue").split("_");
            int randInt = new Random().nextInt(Integer.parseInt(twoNumbers[1]) + 1
                    - Integer.parseInt(twoNumbers[0])) + Integer.parseInt(twoNumbers[0]);
            resp.getWriter().write("randValue=" + randInt);


        } else if (request.equals("counterValue")) {

            counter += Integer.parseInt(req.getParameter("counterValue"));
            resp.getWriter().write("counterValue=" + counter);


        } else if (request.equals("toLog")) {

            String message = req.getParameter("toLog");
            log(message);
            if (message.length() > 80) {
                resp.getWriter().write("too long message");
            } else {
                FileWriter writer = new FileWriter("log.txt", true);
                PrintWriter print_line = new PrintWriter(writer);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                print_line.write(dtf.format(now) + " " + message + "\n");
                resp.getWriter().write("your message was written to the log");
                writer.close();
                print_line.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String request = req.getParameter("command");
        if (request.contains("randValue")) {
            String[] twoNumbers = request.substring(request.indexOf("=") + 1).split("_");
            int randInt = new Random().nextInt(Integer.parseInt(twoNumbers[1]) + 1
                                                      -Integer.parseInt(twoNumbers[0]))
                                                      + Integer.parseInt(twoNumbers[0]);
            resp.getWriter().write("randValue=" + randInt);

        } else if (request.contains("counterValue")) {

            String number = request.substring(request.indexOf("=") + 1);
            counter += Integer.parseInt(number);
            resp.getWriter().write("counterValue = " + counter);


        } else if (request.contains("toLog")) {

            String message = request.substring(request.indexOf("=") + 1);
            log(message);
            if (message.length() > 80) {
                resp.getWriter().write("too long message");

            } else {
                FileWriter writer = new FileWriter("log.txt", true);
                PrintWriter print_line = new PrintWriter(writer);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                print_line.write(dtf.format(now) + " " + message + "\n");
                resp.getWriter().write("your message was written to the log");
                print_line.close();
                writer.close();
            }
        }
        }


    private String formatParams(HttpServletRequest req) {
        return req.getParameter("=");
    }

    @Override
    public void destroy() {
        log("Method destroy =)");
    }
}
