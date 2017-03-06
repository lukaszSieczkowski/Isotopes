package pl.isotope.servlet;

import pl.isotope.bean.Isotope;
import pl.isotope.bean.User;
import pl.isotope.dao.IsotopeDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
    /**
     * Method send loged user to welcome site
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        IsotopeDAO isotopeDao = new IsotopeDAO();
        List<Isotope> isotopeList = isotopeDao.generateIsotopeList(user.getId());
        request.getSession().setAttribute("isotopeList", isotopeList);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/welcome.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
