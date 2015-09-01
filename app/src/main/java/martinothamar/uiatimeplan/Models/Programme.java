package martinothamar.uiatimeplan.Models;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import martinothamar.uiatimeplan.GetProgrammeScheduleTask;
import martinothamar.uiatimeplan.Util;


public class Programme implements Serializable {
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
        try {
            Document html = new GetProgrammeScheduleTask().execute(requestData).get();
            String title = html.select(".title").first().text();
            this.name = Util.sanitize(title);

            Elements weeks = html.select("table");
            for(Element week : weeks) {
                Elements days = week.select("tr.tr2");
                if(days != null && days.size() > 0) {
                    Week theWeek = new Week();
                    ArrayList<Day> dayList = new ArrayList<Day>();
                    String weekString = Util.sanitize(week.select("td.td1").first().text());
                    theWeek.parseWeekNumber(weekString);
                    theWeek.parseYear(weekString);
                    for(Element dayActivity : days) {
                        Activity activity = new Activity();
                        activity.parseCourses(Util.sanitize(dayActivity.child(3).text()));
                        activity.parseRooms(Util.sanitize(dayActivity.child(4).text()));
                        activity.lecturer = Util.sanitize(dayActivity.child(5).text());
                        activity.notice = Util.sanitize(dayActivity.child(6).text());
                        String dateStr = Util.sanitize(dayActivity.child(1).text());
                        String timeStr = Util.sanitize(dayActivity.child(2).text());
                        activity.parseTimespan(theWeek.year, dateStr, timeStr);
                        ArrayList<Day> exists = new ArrayList<Day>();
                        for(Day d : dayList) {
                            if(d.date.get(Calendar.DATE) == activity.start.get(Calendar.DATE)) {
                                exists.add(d);
                            }
                        }
                        if(exists.size() == 0) {
                            Day aday = new Day();
                            aday.activities.add(activity);
                            aday.date = new GregorianCalendar
                                    (activity.start.get(Calendar.YEAR),
                                    activity.start.get(Calendar.MONTH),
                                    activity.start.get(Calendar.DAY_OF_MONTH));
                            aday.dayOfWeek = activity.start.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
                            dayList.add(aday);
                        }
                        else if (exists.size() > 0){
                            exists.get(0).activities.add(activity);
                        }
                    }

                    theWeek.days.addAll(dayList);
                    this.schedule.add(theWeek);
                }
            }
        } catch(Exception ex) {
            throw new IllegalArgumentException("scrapeSchedule: " + ex.getMessage());
        }
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
