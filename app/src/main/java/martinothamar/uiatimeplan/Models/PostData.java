package martinothamar.uiatimeplan.Models;

/**
 * Created by martin on 8/27/15.
 */
public class PostData {
    public String __EVENTTARGET;
    public String __EVENTARGUMENT;
    public String __LASTFOCUS;
    public String __VIEWSTATE;
    public String __VIEWSTATEGENERATOR;
    public String __EVENTVALIDATION;
    public String tLinkType;
    public String tWildcard;
    public String lbWeeks;
    public String lbDays;
    public String RadioType;
    public String bGetTimetable;
    public String programmeCode;


    public PostData() {
    }

    public PostData(String __EVENTTARGET, String __EVENTARGUMENT, String __LASTFOCUS,
                    String __VIEWSTATE, String __VIEWSTATEGENERATOR, String __EVENTVALIDATION,
                    String tLinkType, String tWildcard, String lbWeeks,
                    String lbDays, String RadioType, String bGetTimetable, String programmeCode) {
        this.set__EVENTTARGET(__EVENTTARGET);
        this.set__EVENTARGUMENT(__EVENTARGUMENT);
        this.set__LASTFOCUS(__LASTFOCUS);
        this.set__VIEWSTATE(__VIEWSTATE);
        this.set__VIEWSTATEGENERATOR(__VIEWSTATEGENERATOR);
        this.set__EVENTVALIDATION(__EVENTVALIDATION);
        this.settLinkType(tLinkType);
        this.settWildcard(tWildcard);
        this.setLbWeeks(lbWeeks);
        this.setLbDays(lbDays);
        this.setRadioType(RadioType);
        this.setbGetTimetable(bGetTimetable);
        this.setProgrammeCode(programmeCode);
    }




    // GETTERS AND SETTERS

    public String get__EVENTTARGET() {
        return __EVENTTARGET;
    }

    public void set__EVENTTARGET(String __EVENTTARGET) {
        this.__EVENTTARGET = __EVENTTARGET;
    }

    public String get__EVENTARGUMENT() {
        return __EVENTARGUMENT;
    }

    public void set__EVENTARGUMENT(String __EVENTARGUMENT) {
        this.__EVENTARGUMENT = __EVENTARGUMENT;
    }

    public String get__LASTFOCUS() {
        return __LASTFOCUS;
    }

    public void set__LASTFOCUS(String __LASTFOCUS) {
        this.__LASTFOCUS = __LASTFOCUS;
    }

    public String get__VIEWSTATE() {
        return __VIEWSTATE;
    }

    public void set__VIEWSTATE(String __VIEWSTATE) {
        this.__VIEWSTATE = __VIEWSTATE;
    }

    public String get__VIEWSTATEGENERATOR() {
        return __VIEWSTATEGENERATOR;
    }

    public void set__VIEWSTATEGENERATOR(String __VIEWSTATEGENERATOR) {
        this.__VIEWSTATEGENERATOR = __VIEWSTATEGENERATOR;
    }

    public String get__EVENTVALIDATION() {
        return __EVENTVALIDATION;
    }

    public void set__EVENTVALIDATION(String __EVENTVALIDATION) {
        this.__EVENTVALIDATION = __EVENTVALIDATION;
    }

    public String gettLinkType() {
        return tLinkType;
    }

    public void settLinkType(String tLinkType) {
        this.tLinkType = tLinkType;
    }

    public String gettWildcard() {
        return tWildcard;
    }

    public void settWildcard(String tWildcard) {
        this.tWildcard = tWildcard;
    }

    public String getLbWeeks() {
        return lbWeeks;
    }

    public void setLbWeeks(String lbWeeks) {
        this.lbWeeks = lbWeeks;
    }

    public String getLbDays() {
        return lbDays;
    }

    public void setLbDays(String lbDays) {
        this.lbDays = lbDays;
    }

    public String getRadioType() {
        return RadioType;
    }

    public void setRadioType(String radioType) {
        RadioType = radioType;
    }

    public String getbGetTimetable() {
        return bGetTimetable;
    }

    public void setbGetTimetable(String bGetTimetable) {
        this.bGetTimetable = bGetTimetable;
    }

    public String getProgrammeCode() {
        return programmeCode;
    }

    public void setProgrammeCode(String programmeCode) {
        this.programmeCode = programmeCode;
    }
}
