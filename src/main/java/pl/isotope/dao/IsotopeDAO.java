package pl.isotope.dao;

import pl.isotope.bean.Isotope;
import pl.isotope.bean.User;
import pl.isotope.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IsotopeDAO {

    private final static String CREATE_ISOTOPE_LIST = "SELECT A.iduserLogin,A.idIsotopeType, isotopeName, isotopeDateLoadDate,activityInLoadDay\n" +
            "FROM isotopedate AS A,isotopetype AS B, userlogin AS C \n" +
            "WHERE A.iduserlogin = ?\n" +
            "AND A.idUserLogin = C.iduserlogin\n" +
            "AND B.idIsotopeType = A.idIsotopeType\n " +
            "ORDER BY isotopeName";

    private final static String ADD_ISOTOPE = "INSERT INTO `isotope_bunker`.`isotopedate` (`iduserlogin`, `idIsotopeType`, `isotopeName`, `isotopeDateLoadDate`,`activityInLoadDay`)" +
            " VALUES (?, ?, ?, ?,?);";

    private final static String SELECT_ISOTOPE = "SELECT iduserLogin,idIsotopeType,isotopeName,isotopeDateLoadDate,activityInLoadDay,idIsotopeDate " +
            "FROM isotope_bunker.isotopedate WHERE iduserLogin = ? AND isotopeName = ?";

    private final static String UPDATE_ISOTOPE = "UPDATE isotopedate\n" +
            "SET idIsotopeType = ?,\n" +
            "isotopeName = ?,\n" +
            "isotopeDateLoadDate = ?,\n" +
            "activityInLoadDay = ?\n" +
            "WHERE idIsotopeDate = ?\n";

    private final static String FIND_ISOTOPE_ID = "SELECT idIsotopeDate FROM isotopedate WHERE isotopeName = ?";

    private final static String REMOVE_ISOTOPE = "DELETE FROM isotopedate WHERE isotopeName = ?";

    private final static String SELECT_ISOTOPENAME = "SELECT isotopeName FROM isotopedate";

    /**
     * Method reads all isotopes for specific user from database and create list of this objects;
     * @param userID  Logged user
     * @return Isotope list
     */
    public List<Isotope> generateIsotopeList(int userID) {

        List<Isotope> isotopeList = null;
        ResultSet result = null;
        PreparedStatement prepStat = null;
        Connection conn = null;
        try {
            conn = DbUtil.getInstance().getConnection();
            prepStat = conn.prepareStatement(CREATE_ISOTOPE_LIST);
            prepStat.setInt(1, userID);
            result = prepStat.executeQuery();

            int idUserLogin;
            String isotopeType;
            String isotopeName;
            String isotopeLoadDate;
            int activityInLoadDay;
            isotopeList = new ArrayList<>();

            while (result.next()) {
                Isotope isotope = new Isotope();
                idUserLogin = result.getInt(1);
                isotopeType = result.getString("idIsotopeType");
                isotopeName = result.getString("isotopeName");
                isotopeLoadDate = result.getString("isotopeDateLoadDate");
                activityInLoadDay = result.getInt(5);

                if (isotopeType.equals("1")) {
                    isotopeType = "Selenium";
                } else if (isotopeType.equals("2")) {
                    isotopeType = "Iridium";
                } else if (isotopeType.equals("3")) {
                    isotopeType = "Cobalt";
                } else if (isotopeType.equals("4")) {
                    isotopeType = "Ytterbium";
                }

                isotope.setIdUserLogin(idUserLogin);
                isotope.setIsotopeType(isotopeType);
                isotope.setIsotopeName(isotopeName);
                isotope.setIsotopeLoadDate(isotopeLoadDate);
                isotope.setActivityInLoadDay(activityInLoadDay);

                isotopeList.add(isotope);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(prepStat, result, conn);
        }
        return isotopeList;
    }

    /**
     * Method add new isotope to database
     * @param user Logged user
     * @param isotopeType Type of isotope(Iridium,Selenium, Cobalt or Ytterbium)
     * @param name Isotope name
     * @param date First day of use of isotope;
     * @param activityInLoadDay Activity in first day
     */
    public void addIsotope(User user, String isotopeType, String name, String date, Double activityInLoadDay) {

        Connection conn = null;
        PreparedStatement prepStat = null;

        try {
            conn = DbUtil.getInstance().getConnection();
            prepStat = conn.prepareStatement(ADD_ISOTOPE);
            prepStat.setInt(1, user.getId());
            prepStat.setString(2, isotopeType);
            prepStat.setString(3, name);
            prepStat.setString(4, date);
            prepStat.setDouble(5, activityInLoadDay);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(prepStat, null, conn);
        }
    }

    /**
     * Methodt allow to choose a specific Isotope from database
     * @param isotopeName Name of isotope
     * @param user Logged user
     * @return Selected isotope
     */

    public Isotope selectIsotope(String isotopeName, User user) {
        Connection conn = null;
        PreparedStatement prepStat = null;
        ResultSet resultSet = null;
        Isotope isotope = new Isotope();
        try {
            conn = DbUtil.getInstance().getConnection();
            prepStat = conn.prepareStatement(SELECT_ISOTOPE);

            prepStat.setInt(1, user.getId());
            prepStat.setString(2, isotopeName);
            resultSet = prepStat.executeQuery();

            int idUserLogin;
            String isotopeType;
            String isotopeLoadDate;
            int activityInLoadDay;

            while (resultSet.next()) {

                idUserLogin = resultSet.getInt(1);
                isotopeType = resultSet.getString("idIsotopeType");
                isotopeLoadDate = resultSet.getString("isotopeDateLoadDate");
                activityInLoadDay = resultSet.getInt(5);

                if (isotopeType.equals("1")) {
                    isotopeType = "Selenium";
                } else if (isotopeType.equals("2")) {
                    isotopeType = "Iridium";
                } else if (isotopeType.equals("3")) {
                    isotopeType = "Cobalt";
                } else if (isotopeType.equals("4")) {
                    isotopeType = "Ytterbium";
                }

                isotope.setIdUserLogin(idUserLogin);
                isotope.setIsotopeType(isotopeType);
                isotope.setIsotopeName(isotopeName);
                isotope.setIsotopeLoadDate(isotopeLoadDate);
                isotope.setActivityInLoadDay(activityInLoadDay);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(prepStat, resultSet, conn);
        }
        return isotope;
    }

    /**
     * Method updates a selected isotope
     * @param newIsotope New isotope
     * @param isotopeId Id selected isotope
     */

    public void updateIsotope(Isotope newIsotope, String isotopeId) {

        Connection conn = null;
        PreparedStatement presStat = null;
        String isotopeType;

        isotopeType = newIsotope.getIsotopeType();

        if (isotopeType.equals("Selenium")) {
            isotopeType = "1";
        } else if (isotopeType.equals("Iridium")) {
            isotopeType = "2";
        } else if (isotopeType.equals("Cobalt")) {
            isotopeType = "3";
        } else if (isotopeType.equals("Ytterbium")) {
            isotopeType = "4";
        }

        try {
            conn = DbUtil.getInstance().getConnection();
            presStat = conn.prepareStatement(UPDATE_ISOTOPE);
            presStat.setString(1, isotopeType);
            presStat.setString(2, newIsotope.getIsotopeName());
            presStat.setString(3, newIsotope.getIsotopeLoadDate());
            presStat.setDouble(4, newIsotope.getActivityInLoadDay());
            presStat.setString(5, isotopeId);

            presStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(presStat, null, conn);
        }
    }

    /**
     * Method allow to find specific isotope in database using name;
     * @param isotopeName Isotope name
     * @return Selected isotope
     */

    public String searchIsotopeID(String isotopeName) {
        String idIsotope = null;
        Connection conn = null;
        PreparedStatement prepStat = null;
        ResultSet result = null;

        try {
            conn = DbUtil.getInstance().getConnection();
            prepStat = conn.prepareStatement(FIND_ISOTOPE_ID);
            prepStat.setString(1, isotopeName);
            result = prepStat.executeQuery();
            while (result.next()) {
                idIsotope = result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(prepStat, result, conn);
        }
        return idIsotope;
    }

    /**
     * Method allows to remmove selected isotope form database
     * @param isotopeName Isotope name
     */
    public void removeIsotopr(String isotopeName) {
        Connection conn = null;
        PreparedStatement preStat = null;

        try {
            conn = DbUtil.getInstance().getConnection();
            preStat = conn.prepareStatement(REMOVE_ISOTOPE);
            preStat.setString(1, isotopeName);
            preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(preStat, null, conn);
        }
    }

    /**
     * Method allows to create list of isotope names
     * @return Isotope names list
     */

    public List<String> createIsotopeNameList() {

        List<String> isotopeNameList = new ArrayList<>();
        String isotopeName = null;
        Connection conn = null;
        PreparedStatement preStat = null;
        ResultSet result = null;
        try {
            conn = DbUtil.getInstance().getConnection();
            preStat = conn.prepareStatement(SELECT_ISOTOPENAME);
            result = preStat.executeQuery();
            while (result.next()) {
                isotopeName = result.getNString(1);
                isotopeNameList.add(isotopeName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            relaseResouses(preStat, result, conn);
        }
        return isotopeNameList;
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
            if (prep != null && !prep.isClosed()) {
                prep.close();
            }
            if (result != null && !result.isClosed()) {
                result.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}