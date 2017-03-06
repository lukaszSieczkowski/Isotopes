package pl.isotope.utils;

import pl.isotope.bean.Isotope;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import java.util.GregorianCalendar;

public class Activity {

    /**
     * Calulate number of days betwean today and load date
     *
     * @param date Load date
     * @return Number of days
     */
    public static long calculateDays(String date) {

        int year;
        int day;
        int month;

        year = Integer.parseInt(date.substring(0, 4));
        month = Integer.parseInt(date.substring(5, 7));
        day = Integer.parseInt(date.substring(8, 10));

        GregorianCalendar loadDate = new GregorianCalendar(year, month - 1, day);
        Calendar curentDate = Calendar.getInstance();

        long loadDateMilis = loadDate.getTimeInMillis();

        long curentDateMilis = curentDate.getTimeInMillis();

        long daysNumber = (curentDateMilis - loadDateMilis) / 1000 / 60 / 60 / 24;

        return daysNumber;

    }

    /**
     * Method calculates current activity for selected isotope
     *
     * @param isotope Selected isotope
     * @param days    Numebr aof days betwean load date and today
     * @return Current activity
     */
    public static String calculateActivity(Isotope isotope, long days) {

        double loadActivity = isotope.getActivityInLoadDay();
        double currentActivity;
        int halfDestructionPeriod = 0;

        if (isotope.getIsotopeType().equals("Selenium")) {
            halfDestructionPeriod = 120;
        } else if (isotope.getIsotopeType().equals("Iridium")) {
            halfDestructionPeriod = 74;
        } else if (isotope.getIsotopeType().equals("Cobalt")) {
            halfDestructionPeriod = 1935;
        } else if (isotope.getIsotopeType().equals("Ytterbium")) {
            halfDestructionPeriod = 32;
        }

        double pow = (double) days / (double) halfDestructionPeriod;
        double powAndHalf = Math.pow(0.5, pow);
        NumberFormat formater = new DecimalFormat("##.##");
        currentActivity = loadActivity * powAndHalf;

        String currentActivityInString = formater.format(currentActivity);

        return currentActivityInString;
    }
}
