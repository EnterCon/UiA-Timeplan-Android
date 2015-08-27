package martinothamar.uiatimeplan;

import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class GetProgrammeScheduleTask extends AsyncTask<String, Void, Document> {

    @Override
    protected Document doInBackground(String... params) {
        Element __EVENTARGUMENT = MainActivity.schedulePage.select("input[id=__EVENTARGUMENT]").first();
        Element __EVENTTARGET = MainActivity.schedulePage.select("input[id=__EVENTTARGET]").first();
        Element __LASTFOCUS = MainActivity.schedulePage.select("input[id=__LASTFOCUS]").first();
        Element __VIEWSTATE = MainActivity.schedulePage.select("input[id=__VIEWSTATE]").first();
        Element __VIEWSTATEGENERATOR = MainActivity.schedulePage.select("input[id=__VIEWSTATEGENERATOR]").first();
        Element __EVENTVALIDATION = MainActivity.schedulePage.select("input[id=__EVENTVALIDATION]").first();
        Element tLinkType = MainActivity.schedulePage.select("input[id=tLinkType]").first();
        Element tWildcard = MainActivity.schedulePage.select("input[id=tWildcard]").first();
        Element lbWeeks = MainActivity.schedulePage.select("select[name=lbWeeks]option[selected=selected]").first();
        Element lbDays = MainActivity.schedulePage.select("select[name=lbDays]option[selected=selected]").first();
        Element RadioType = MainActivity.schedulePage.select("input[type=radio][name=RadioType][checked=checked]").first();
        Element bGetTimetable = MainActivity.schedulePage.select("input[id=bGetTimetable]").first();
        return null;
    }
}
