package martinothamar.uiatimeplan;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;


public class Utilities {

    public static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


    /*
    * Get the appropriate schedule page URL, depending on time of year
    * 16. Des, 10. Juni
     */
    public static URL getSemesterURL() {
        try {
            Calendar cal = Calendar.getInstance();

            int month = cal.get(Calendar.MONTH);
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

            if (month > 5 && month < 11) {
                return new URL("http://timeplan.uia.no/swsuiah/public/no/default.aspx");
            } else if(month == 5 && dayOfMonth >= 20) {
                return new URL("http://timeplan.uia.no/swsuiah/public/no/default.aspx");
            } else if(month == 11 && dayOfMonth <= 16) {
                return new URL("http://timeplan.uia.no/swsuiah/public/no/default.aspx");
            } else {
                return new URL("http://timeplan.uia.no/swsuiav/public/no/default.aspx");
            }
        } catch(Exception ex) {
            return null;
        }
    }
}
