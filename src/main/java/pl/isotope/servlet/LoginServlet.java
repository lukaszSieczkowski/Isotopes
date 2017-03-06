package pl.isotope.servlet;


import pl.isotope.bean.User;
import pl.isotope.dao.UserDAO;
import pl.isotope.utils.DateChecker;
import pl.isotope.utils.SendMail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Klakson on 2016-11-22.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = null;
        String alert = null;
        String param = null;
        param = request.getParameter("param");
        UserDAO dao = new UserDAO();

        if ("login".equals(param)) {
            List<User> userList = dao.checkUserEmailPassword();
            User userEmailPassword = createUserEmailPassword(request);
            if (userList.contains(userEmailPassword)) {
                User user = dao.createUser(userEmailPassword.getEmail(), userEmailPassword.getPassword());
                request.getSession().setAttribute("user", user);
                url = "/WelcomeServlet";
            } else {
                url = "/index.jsp";
                alert = "Login or password is incorrect";
                request.setAttribute("alert", alert);
            }
        } else if ("remind".equals(param)) {
            List<User> userList = dao.checkUserEmail();
            System.out.println("USERLIST = " + userList);
            User userEmailPassword = createUserPassword(request);
            System.out.println(userEmailPassword);
            if (userList.contains(userEmailPassword)) {
                User userPassword = createUserPassword(request);
                String email = userPassword.getEmail();
                String password = dao.selectUserFromMail(email);
                String subject = "Password Reminder for Isotope Bunker";
                String mailContent = "Dear User\n\n" +
                        "This is your password reminder for ISOTOPE BUNKER\n\n" +
                        "Your login: " + email + ",\n\n" +
                        "Your password: " + password + "\n\n" +
                        "Kind Regards\n\n" +
                        "Isotope Bunker TEAM";

                SendMail.sendMail(email, mailContent, subject);
                url = "/index.jsp";
                alert = "Password was send to you email";
                request.setAttribute("alert", alert);
            } else {
                url = "/forgotten_password.jsp";
                alert = "User doesn't exist";
                request.setAttribute("alert", alert);
            }

        } else if ("create".equals(param)) {
            String name;
            String surname;
            String email;
            String password1;
            String password2;

            name = request.getParameter("name");
            surname = request.getParameter("surname");
            email = request.getParameter("email");
            password1 = request.getParameter("password");
            password2 = request.getParameter("re_password");
            List<User> userList = dao.checkUserEmail();
            User user = createUserPassword(request);

            if (password1.equals("") || password1.equals(null) || password2.equals("") || password2.equals(null)) {
                alert = "Incorrect value";
                url = "/first_login.jsp";
            } else if (password1.length() < 7) {
                alert = "Password should be longer than 6 signs";
                url = "/first_login.jsp";
            } else if (userList.contains(user)) {
                alert = "This email is not free";
                url = "/first_login.jsp";
            } else if (!password1.equals(password2)) {
                alert = "Passwords are not equals";
                url = "/first_login.jsp";
            } else {
                dao.createNewUserInDatabase(name, surname, password1, email);
                String subject = "New Account in Isotope Bunker";
                String mailContent = "Dear User\n\n" +
                        "You have created new account in application ISOTOPE BUNKER\n\n" +
                        "Your login: " + email + ",\n\n" +
                        "Your password: " + password1 + "\n\n" +
                        "Kind Regards\n\n" +
                        "Isotope Bunker TEAM";

                SendMail.sendMail(email, mailContent, subject);
                alert = "User was created successfully";
                url = "/index.jsp";
            }
            request.setAttribute("alert", alert);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public User createUserPassword(HttpServletRequest request) {
        String email = request.getParameter("email");
        User userEmailPass = new User(email);
        return userEmailPass;
    }

    public User createUserEmailPassword(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User userEmailPass = new User(email, password);
        return userEmailPass;
    }
}
