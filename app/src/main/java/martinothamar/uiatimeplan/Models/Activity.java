package martinothamar.uiatimeplan.Models;


import java.util.ArrayList;
import java.util.Calendar;

public class Activity {
    public ArrayList<String> courses;
    public Calendar start;
    public Calendar end;
    public String lecturer;
    public String notice;
    public ArrayList<String> rooms;

    public Activity() {
        this.rooms = new ArrayList<String>();
        this.courses = new ArrayList<String>();
    }

    public void parseTimespan(int year, String date, String time) {

    }

    public void parseRooms(String stringOfRooms) {

    }

    public void parseCourses(String stringOfCourses) {

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
