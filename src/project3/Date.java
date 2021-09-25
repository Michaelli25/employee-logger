package project3;
import java.util.Calendar;

/**
 * Date is a class that stores the day, month, and year, used mainly for
 * the hiring date of an employee.
 * @author Shane Hoffman, Michael Li
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    private static final int MISS = -1;

    /**
     * Creates a date object with today's date using the calendar class.
     */
    public Date() {
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        this.month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Creates a date object with a year, month, and day.
     * @param date - date represented as a string: "1/20/20"
     */
    public Date(String date) {
        String[] dateArray = date.split("-");
        this.month = Integer.parseInt(dateArray[1]);
        this.day = Integer.parseInt(dateArray[2]);
        this.year = Integer.parseInt(dateArray[0]);
    }

    /**
     * Converts a date object into a readable string.
     * @return dateString - string that represents the date stored
     */
    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }


    /**
     * Compares two dates to see if the compared date is earlier, equal to, or
     * later than the other date.
     * @param date - a date to compare to the called date
     * @return 1 if called date is later than param date, 0 if called date is equal
     * to param date, -1 if called date is earlier than param date
     */
    @Override
    public int compareTo(Date date) {
        // Return 1 if this > date, 0 if this = date, and -1 if this < date
        if(this.day == date.day && this.month == date.month && this.year == date.year)
            return 0;
        else if(this.year > date.year || (this.month > date.month && this.year == date.year))
            return 1;
        else if(this.day > date.day && this.month >= date.month && this.year == date.year)
            return 1;
        return MISS;
    }

}
