package martinothamar.uiatimeplan;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class GetProgrammesTask extends AsyncTask<URL, Void, HashMap<String, String>> {
    private Exception exception;


    @Override
    protected HashMap<String, String> doInBackground(URL... url) {
        HashMap<String, String> programmes = new HashMap<String, String>();

        try {
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);
            HttpURLConnection conn = (HttpURLConnection) url[0].openConnection();
            conn.setReadTimeout(5000);
            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.addRequestProperty("User-Agent", "Mozilla");
            conn.addRequestProperty("Referer", "google.com");

            boolean redirect = false;
            // normally, 3xx is redirect
            int status = conn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }

            if (redirect) {
                // get redirect url from "location" header field
                String newUrl = conn.getHeaderField("Location");

                // get the cookie if need, for login
                String cookies = conn.getHeaderField("Set-Cookie");

                // open the new connnection again
                conn = (HttpURLConnection) new URL(newUrl).openConnection();
                conn.setRequestProperty("Cookie", cookies);
                conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                conn.addRequestProperty("User-Agent", "Mozilla");
                conn.addRequestProperty("Referer", "google.com");
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer html = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();

            MainActivity.schedulePage = Jsoup.parse(html.toString());
            Element selectBox = MainActivity.schedulePage.select("select[id=dlObject]").first();

            for(Element programme : selectBox.children()) {
                programmes.put(programme.text(), programme.val());
            }

        } catch(Exception ex) {
            System.out.println("Something happened: " + ex.getMessage());
        }
        return programmes;
    }
}
