package martinothamar.uiatimeplan.Models;

import java.util.ArrayList;


public class Programme {
    public String id;
    public String name;
    public ArrayList<Week> schedule;

    public Programme() {
        this.schedule = new ArrayList<Week>();
    }

    public Programme(String id) {
        this.schedule = new ArrayList<Week>();
        this.id = id;
    }


    public void scrapeSchedule(PostData requestData) {

    }


    // GETTERS AND SETTERS

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Week> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Week> schedule) {
        this.schedule = schedule;
    }
}
