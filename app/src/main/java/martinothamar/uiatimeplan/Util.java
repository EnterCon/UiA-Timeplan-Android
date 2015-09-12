package martinothamar.uiatimeplan;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class Util {

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

    public static String sanitize(String str)
    {
        StringBuilder res = new StringBuilder();
        Character[] escapeSequences = new Character[]{'\r', '\n', '\t', '\\', '\f', '\b', '\n', '\'', '\"'};
        for (int i = 0; i < str.length(); i++)
        {
            if (Arrays.asList(escapeSequences).contains(str.charAt(i)))
                continue;
            if (i == 0 && str.charAt(i) == ' ')
                continue;
            if (i != 0 && str.charAt(i) == ' ' && str.charAt(i - 1) == ' ')
                continue;
            res.append(str.charAt(i));
        }
        return res.toString().trim();
    }
}
