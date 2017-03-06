package pl.isotope.utils;

public class DateChecker {

    /**
     * Method checks is date in correct format
     *
     * @param date
     * @return
     */

    public static boolean dateChecker(String date) {

        boolean isDate = false;
        char[] tab = null;
        tab = date.toCharArray();

        if (date.length() != 10) {
            isDate = false;
        } else if (tab[4] != '-' || tab[7] != '-') {
            isDate = false;
        } else if ((tab[5] != '0' && tab[5] != '1') || (tab[8] != '0' && tab[8] != '1' && tab[8] != '2' && tab[8] != '3')) {
            isDate = false;
        } else if (tab[5] == '1' && (tab[6] == '3' || tab[6] == '4' || tab[6] == '5' || tab[6] == '6' || tab[6] == '7' || tab[6] == '8'
                || tab[6] == '9')) {
            isDate = false;
        } else if (Character.isDigit(tab[0]) && Character.isDigit(tab[1]) && Character.isDigit(tab[2]) && Character.isDigit(tab[3])
                && Character.isDigit(tab[5]) && Character.isDigit(tab[6]) && Character.isDigit(tab[8]) && Character.isDigit(tab[9])) {
            isDate = true;
        }
        return isDate;
    }

    /**
     * Method check is activity in correct format
     *
     * @param activitiInLoadday
     * @return
     */

    public boolean activityChecker(String activitiInLoadday) {

        boolean activity = true;
        char[] tab = activitiInLoadday.toCharArray();

        for (int i = 0; i < tab.length; i++) {
            if (!Character.isDigit(tab[i]) && tab[i] != '.') {
                activity = false;
            }
        }
        return activity;
    }
}
