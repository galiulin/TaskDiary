package servlets;

import org.apache.log4j.Logger;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginServlet.class);
    private static final String encoding = "Cp1251";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding(encoding);
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding(encoding);
        String login = req.getParameter("login");
        String userPassword = req.getParameter("password");
        if (LoginService.checkUser(login, userPassword)) {
            req.getSession().setAttribute("login", login);
            resp.sendRedirect("/inner/dashboard");
            logger.trace(req.getSession().getId().toString() + " access success, redirect /inner/dashboard");
        } else {
            logger.trace(req.getSession().getId().toString() + " access denied");
            req.getRequestDispatcher("/login.jsp?access=access_denied").forward(req, resp);
        }
    }
}
