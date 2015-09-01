package martinothamar.uiatimeplan;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URLConnection;
import java.net.URLEncoder;

import martinothamar.uiatimeplan.Models.PostData;


public class GetProgrammeScheduleTask extends AsyncTask<PostData, Void, Document> {

    @Override
    protected Document doInBackground(PostData... requestData) {
        try {
            URLConnection connection = Util.getSemesterURL().openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            StringBuilder postData = new StringBuilder();
            for (Field param : requestData[0].getClass().getFields()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getName(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode((String) param.get(requestData[0]), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            connection.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            for (int c = in.read(); c != -1; c = in.read())
                result.append((char) c);

            Document html = Jsoup.parse(result.toString());
            return html;
        } catch(Exception ex) {
            throw new IllegalArgumentException("getProgrammeScheduleTask: " + ex.getMessage());
        }
    }
}
