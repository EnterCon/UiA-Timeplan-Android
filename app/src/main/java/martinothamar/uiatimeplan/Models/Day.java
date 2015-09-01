package martinothamar.uiatimeplan.Models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Day implements Serializable {
    public Calendar date;
    public String dayOfWeek;
    public ArrayList<Activity> activities;

    public Day() {
        this.activities = new ArrayList<Activity>();
    }


    // GETTERS AND SETTERS

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }
}
