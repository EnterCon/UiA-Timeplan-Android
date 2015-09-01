package martinothamar.uiatimeplan.Models;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity implements Serializable {
    public ArrayList<String> courses;
    public Calendar start;
    public Calendar end;
    public String lecturer;
    public String notice;
    public ArrayList<String> rooms;

    public Activity() {
        this.rooms = new ArrayList<String>();
        this.courses = new ArrayList<String>();
        this.start = Calendar.getInstance();
        this.end = Calendar.getInstance();
    }

    public void parseTimespan(int year, String date, String time) {
        String[] times = time.split("-");
        if (times.length != 2)
            throw new IllegalArgumentException("ParseTimespan: time argument has invalid format: '"
                    + time + "'");
        String startStr = times[0];
        String endStr = times[1];

        try {
            String[] hoursAndMinutes = startStr.split("\\.");
            for (int i = 0; i < hoursAndMinutes.length; i++)
                hoursAndMinutes[i] =
                        hoursAndMinutes[i].length() == 1 ? "0" + hoursAndMinutes[i] : hoursAndMinutes[i];
            Object[] parameters = new Object[] { date, year, hoursAndMinutes[0], hoursAndMinutes[1] };
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH.mm.ss", Locale.ENGLISH);
            this.start.setTime(sdf.parse(String.format("%s %d %s.%s.00", parameters)));
        }
        catch (Exception ex)
        {
            throw new IllegalArgumentException("ParseTimespan: couldn't parse start of activity: '"
                    + startStr + "'", ex);
        }

        try
        {
            String[] hoursAndMinutes = endStr.split("\\.");
            for (int i = 0; i < hoursAndMinutes.length; i++)
                hoursAndMinutes[i] =
                        hoursAndMinutes[i].length() == 1 ? "0" + hoursAndMinutes[i] : hoursAndMinutes[i];
            Object[] parameters = new Object[] { date, year, hoursAndMinutes[0], hoursAndMinutes[1] };
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH.mm.ss", Locale.ENGLISH);
            this.end.setTime(sdf.parse(String.format("%s %d %s.%s.00", parameters)));
        }
        catch (Exception ex)
        {
            throw new IllegalArgumentException("ParseTimespan: couldn't parse end of activity: '"
                    + endStr + "'", ex);
        }
    }

    public void parseRooms(String stringOfRooms) {
        String[] rooms = stringOfRooms.split(",");
        for(String room : rooms)
        {
            this.rooms.add(room.trim());
        }
    }

    public void parseCourses(String stringOfCourses) {
        Pattern pattern = Pattern.compile("(([A-Z]{2}|[A-Z]{3})(-)?\\d{3})");
        Matcher matcher = pattern.matcher(stringOfCourses);
        while(matcher.find()) {
            this.courses.add(matcher.group());
        }
    }



    // GETTERS AND SETTERS

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<String> rooms) {
        this.rooms = rooms;
    }
}
