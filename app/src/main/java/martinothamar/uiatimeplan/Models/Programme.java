package martinothamar.uiatimeplan.Models;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import martinothamar.uiatimeplan.MainActivity;
import martinothamar.uiatimeplan.Utilities;


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


    public String scrapeSchedule(PostData requestData) {
        try {
            URLConnection connection = Utilities.getSemesterURL().openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            StringBuilder postData = new StringBuilder();
            for (Field param : requestData.getClass().getFields()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getName(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode((String)param.get(requestData), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.getOutputStream().write(postDataBytes);

            InputStream response = connection.getInputStream();
            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            StringBuilder result = new StringBuilder();
            for ( int c = in.read(); c != -1; c = in.read() )
                result.append((char)c);
            return result.toString();
        } catch(Exception ex) {

        }
        return null;
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
