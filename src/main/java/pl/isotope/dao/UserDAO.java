package pl.isotope.dao;

import pl.isotope.bean.User;
import pl.isotope.utils.DbUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String CHECK_USER_EMAIL_PASSWORD = "SELECT email, password FROM userlogin";
    private static final String CHECK_USER_EMAIL = "SELECT email FROM userlogin";
    private static final String CREATE_USER = "SELECT iduserLogin,name,surname,email FROM userlogin WHERE email = ? AND password = ?";
    private static final String SELECT_USER_WITH_MAIL = "SELECT password FROM userlogin WHERE email = ?";
    private static final String CREATE_NEW_USER_IN_DATABASE = "INSERT INTO `isotope_bunker`.`userlogin` (`name`, `surname`, `password`, `email`)" +
            " VALUES (?, ?, ?, ?)";

    /**
     * Method allows to select users using email and password
     *
     * @return Users list
     */
    public List<User> checkUserEmailPassword() {
        Connection conn = null;
        PreparedStatement prepStat = null;
        ResultSet resultSet = null;
        List<User> usersList = new ArrayList<User>();
        try {
            conn = DbUtil.getInstance().getConnection();
            prepStat = conn.prepareStatement(CHECK_USER_EMAIL_PASSWORD);
            resultSet = prepStat.executeQuery();

            String email;
            String password;
            while (resultSet.next()) {
                email = resultSet.getString("email");
                password = resultSet.getString("password");
                User user = new User(email, password);
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(prepStat, resultSet, conn);
        }
        return usersList;
    }

    /**
     * Method allows to select users using email
     *
     * @return Users list
     */
    public List<User> checkUserEmail() {
        Connection conn = null;
        PreparedStatement prepStat = null;
        ResultSet resultSet = null;
        List<User> usersList = new ArrayList<User>();
        try {
            conn = DbUtil.getInstance().getConnection();
            prepStat = conn.prepareStatement(CHECK_USER_EMAIL);
            resultSet = prepStat.executeQuery();

            String email;

            while (resultSet.next()) {
                email = resultSet.getString("email");
                User user = new User(email);
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(prepStat, resultSet, conn);
        }
        return usersList;
    }

    /**
     * Method allows to select a user from database using his email and password
     *
     * @param emailFromRequest User email
     * @param pass             User password
     * @return Selected user
     */
    public User createUser(String emailFromRequest, String pass) {
        User user = new User();
        Connection conn = null;
        PreparedStatement prepStat = null;
        ResultSet resultSet = null;
        try {
            conn = DbUtil.getInstance().getConnection();
            prepStat = conn.prepareStatement(CREATE_USER);
            prepStat.setString(1, emailFromRequest);
            prepStat.setString(2, pass);
            resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String emailFromDatabase = resultSet.getString("email");

                user.setId(id);
                user.setName(name);
                user.setSurname(surname);
                user.setEmail(emailFromDatabase);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(prepStat, resultSet, conn);
        }
        return user;
    }

    /**
     * Mtehod allows select users password from database using email;
     *
     * @param email User email;
     * @return Password
     */
    public String selectUserFromMail(String email) {

        String password = null;

        Connection conn = null;
        PreparedStatement prepStat = null;
        ResultSet resultSet = null;

        try {
            conn = DbUtil.getInstance().getConnection();
            prepStat = conn.prepareStatement(SELECT_USER_WITH_MAIL);
            prepStat.setString(1, email);
            resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                password = resultSet.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(prepStat, resultSet, conn);
        }
        return password;
    }

    /**
     * Method creates a new user in database
     *
     * @param name     User name
     * @param surname  User surname
     * @param password User passwword
     * @param email    User email
     */
    public void createNewUserInDatabase(String name, String surname, String password, String email) {
        Connection conn = null;
        PreparedStatement prepStat = null;
        try {
            conn = DbUtil.getInstance().getConnection();
            prepStat = conn.prepareStatement(CREATE_NEW_USER_IN_DATABASE);
            System.out.println(prepStat.toString());
            prepStat.setString(1, name);
            prepStat.setString(2, surname);
            prepStat.setString(3, password);
            prepStat.setString(4, email);
            System.out.println(prepStat.toString());
            prepStat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(prepStat, null, conn);
        }
    }

    /**
     * Method closes resorces
     *
     * @param prep   PreparedStatement
     * @param result ResultSet
     * @param conn   Connection
     */
    private void relaseResouses(PreparedStatement prep, ResultSet result, Connection conn) {
        try {
            if (prep != null || !prep.isClosed()) {
                prep.close();
            }
            if (result != null || !result.isClosed()) {
                result.close();
            }
            if (conn != null || conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
