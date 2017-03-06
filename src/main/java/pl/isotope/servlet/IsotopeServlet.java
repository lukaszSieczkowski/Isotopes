package pl.isotope.servlet;

import pl.isotope.bean.Isotope;
import pl.isotope.bean.User;
import pl.isotope.dao.IsotopeDAO;
import pl.isotope.utils.DateChecker;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/IsotopeServlet")
public class IsotopeServlet extends HttpServlet {

    /**
     * Method controls all acctions betwean viever and model
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String param = request.getParameter("param");
        String url = null;
        String alert = null;
        IsotopeDAO isotopeDAO = new IsotopeDAO();
        DateChecker dateChecker = new DateChecker();
        User user = (User) request.getSession().getAttribute("user");

        if ("add".equals(param)) {

            String name = request.getParameter("name");
            String loadActivity = request.getParameter("loadActivity");
            String loadDate = request.getParameter("loadDate");
            String type = request.getParameter("type");
            Double loadActivityDouble = 0.0;
            List<String> isotopeNameList = isotopeDAO.createIsotopeNameList();

            if (!dateChecker.dateChecker(loadDate)) {
                url = "/ad_isotope.jsp";
                alert = "Wrong date format";
            } else if (isotopeNameList.contains(name)) {
                alert = "This Isotope Name is not free.";
                url = "/ad_isotope.jsp";
            } else if (!dateChecker.activityChecker(loadActivity)) {

                url = "/ad_isotope.jsp";
                alert = "Activity should be a number";
            } else {
                loadActivityDouble = Double.parseDouble(loadActivity);
                isotopeDAO.addIsotope(user, type, name, loadDate, loadActivityDouble);
                url = "/WelcomeServlet";
            }

        } else if ("select".equals(param)) {
            String isotopeName = request.getParameter("isotopeName");
            List<String> isotopeNameList = isotopeDAO.createIsotopeNameList();
            if (isotopeNameList.contains(isotopeName)) {
                Isotope isotope = isotopeDAO.selectIsotope(isotopeName, user);
                request.getSession().setAttribute("isotope", isotope);
                url = "/update_isotope.jsp";

            } else {
                alert = "This Isotope Name doesn't exist";
                url = "/select_isotope.jsp";
            }

        } else if ("update".equals(param)) {
            Isotope oldIsotope = (Isotope) request.getSession().getAttribute("isotope");
            String name = request.getParameter("name");
            String loadActivity = request.getParameter("loadActivity");
            String loadDate = request.getParameter("loadDate");
            String type = request.getParameter("type");

            String isotopeId = isotopeDAO.searchIsotopeID(oldIsotope.getIsotopeName());
            if (!dateChecker.dateChecker(loadDate)) {
                url = "/update_isotope.jsp";
                alert = "Wrong date format";
            } else if (!dateChecker.activityChecker(loadActivity)) {
                url = "/update_isotope.jsp";
                alert = "Activity should be a number";
            } else {
                Isotope newIsotope = createIsotope(oldIsotope, name, type, loadDate, loadActivity);
                isotopeDAO.updateIsotope(newIsotope, isotopeId);
                url = "/WelcomeServlet";
            }
        } else if ("remove".equals(param)) {
            String isotopeName = (String) request.getParameter("isotopeName");
            List<String> isotopeNameList = isotopeDAO.createIsotopeNameList();
            if (isotopeNameList.contains(isotopeName)) {
                isotopeDAO.removeIsotopr(isotopeName);
                url = "/WelcomeServlet";
            } else {
                alert = "This Isotope Name doesn't exist";
                url = "/remove_isotope.jsp";
            }
        }
        request.setAttribute("alert", alert);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Method creates new isotope to update old one
     * @param isotope Isotope to update
     * @param isotopeName New isotope name
     * @param isotopeType New isotope type
     * @param isotopeLoadDate New isotope load date
     * @param activity New activity
     * @return Updated isotope
     */

    public Isotope createIsotope(Isotope isotope, String isotopeName, String isotopeType, String isotopeLoadDate, String activity) {
        Isotope newIsotope = new Isotope();

        int idUserLogin;
        double activityInLoadDay;

        idUserLogin = isotope.getIdUserLogin();
        activityInLoadDay = Double.parseDouble(activity);

        if (isotopeType.equals("1")) {
            isotopeType = "Selenium";
        } else if (isotopeType.equals("2")) {
            isotopeType = "Iridium";
        } else if (isotopeType.equals("3")) {
            isotopeType = "Cobalt";
        } else if (isotopeType.equals("4")) {
            isotopeType = "Ytterbium";
        }

        newIsotope.setIdUserLogin(idUserLogin);
        newIsotope.setIsotopeName(isotopeName);
        newIsotope.setIsotopeType(isotopeType);
        newIsotope.setIsotopeLoadDate(isotopeLoadDate);
        newIsotope.setActivityInLoadDay(activityInLoadDay);
        return newIsotope;
    }

}
