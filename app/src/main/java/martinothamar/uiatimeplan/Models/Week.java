package martinothamar.uiatimeplan.Models;


import java.util.ArrayList;

public class Week {
    public int weekNumber;
    public int year;
    public ArrayList<Day> days;

    public Week() {
        this.days = new ArrayList<Day>();
    }


    public static int getWeekNumber(String stringContainingWeekNumber) {
        return 0;
    }

    public static int getYear(String stringContainingYear) {
        return 0;
    }


    // GETTERS AND SETTERS

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }
}
