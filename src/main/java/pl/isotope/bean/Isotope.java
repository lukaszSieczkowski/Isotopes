package pl.isotope.bean;

public class Isotope {
    /**
     * Isotope Id in database
     */
    private int idUserLogin;
    /**
     * Isotope type
     */
    private String isotopeType;
    /**
     * Isotope name;
     */
    private String isotopeName;
    /**
     * Date when isotope was load
     */
    private String isotopeLoadDate;
    /**
     * isotope activity in load day
     */
    private double activityInLoadDay;

    public double getActivityInLoadDay() {
        return activityInLoadDay;
    }

    public void setActivityInLoadDay(double activityInLoadDay) {
        this.activityInLoadDay = activityInLoadDay;
    }

    public int getIdUserLogin() {
        return idUserLogin;
    }

    public void setIdUserLogin(int idUserLogin) {
        this.idUserLogin = idUserLogin;
    }

    public String getIsotopeType() {
        return isotopeType;
    }

    public void setIsotopeType(String isotopeType) {
        this.isotopeType = isotopeType;
    }

    public String getIsotopeName() {
        return isotopeName;
    }

    public void setIsotopeName(String isotopeName) {
        this.isotopeName = isotopeName;
    }

    public String getIsotopeLoadDate() {
        return isotopeLoadDate;
    }

    public void setIsotopeLoadDate(String isotopeLoadDate) {
        this.isotopeLoadDate = isotopeLoadDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Isotope isotope = (Isotope) o;
        if (idUserLogin != isotope.idUserLogin) return false;
        if (Double.compare(isotope.activityInLoadDay, activityInLoadDay) != 0) return false;
        if (isotopeType != null ? !isotopeType.equals(isotope.isotopeType) : isotope.isotopeType != null) return false;
        if (isotopeName != null ? !isotopeName.equals(isotope.isotopeName) : isotope.isotopeName != null) return false;
        return isotopeLoadDate != null ? isotopeLoadDate.equals(isotope.isotopeLoadDate) : isotope.isotopeLoadDate == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idUserLogin;
        result = 31 * result + (isotopeType != null ? isotopeType.hashCode() : 0);
        result = 31 * result + (isotopeName != null ? isotopeName.hashCode() : 0);
        result = 31 * result + (isotopeLoadDate != null ? isotopeLoadDate.hashCode() : 0);
        temp = Double.doubleToLongBits(activityInLoadDay);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Isotope{" +
                "idUserLogin=" + idUserLogin +
                ", isotopeType='" + isotopeType + '\'' +
                ", isotopeName='" + isotopeName + '\'' +
                ", isotopeLoadDate='" + isotopeLoadDate + '\'' +
                ", activityInLoadDay=" + activityInLoadDay +
                '}';
    }
}

