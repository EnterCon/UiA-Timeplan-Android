package martinothamar.uiatimeplan.Models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Week implements Serializable {
    public int weekNumber;
    public int year;
    public ArrayList<Day> days;

    public Week() {
        this.days = new ArrayList<Day>();
    }


    public void parseWeekNumber(String stringContainingWeekNumber) {
        Pattern pattern = Pattern.compile("(?<=Uke.{0,10})\\b([0-9]|[1-4][0-9]|5[0-2])\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(stringContainingWeekNumber);
        int count = 0;
        int weekNumber = 0;
        while(matcher.find()) {
            if(count > 1) {
                throw new IllegalArgumentException("getWeekNumber: found more than 1 matches");
            }
            weekNumber = Integer.parseInt(matcher.group());
            count++;
        }
        if(weekNumber == 0) {
            throw new IllegalArgumentException("getWeekNumber: found no matches in string");
        }
        this.weekNumber = weekNumber;
    }

    public void parseYear(String stringContainingYear) {
        Pattern pattern = Pattern.compile("(20)\\d{2}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(stringContainingYear);
        int count = 0;
        int year = 0;
        while(matcher.find()) {
            if(count > 1) {
                throw new IllegalArgumentException("parseYear: found more than 1 matches");
            }
            year = Integer.parseInt(matcher.group());
            count++;
        }
        if(year == 0) {
            throw new IllegalArgumentException("parseYear: found no matches in string");
        }
        this.year = year;
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
