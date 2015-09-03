package martinothamar.uiatimeplan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import martinothamar.uiatimeplan.Models.PostData;
import martinothamar.uiatimeplan.Models.Programme;

public class MainActivity extends Activity implements View.OnClickListener {
    public static Document schedulePage;
    public static HashMap<String, String> programmesMap; // The name of the programme and it's select-box ID
    public static Map<String, Integer> mapIndex; // The first letter of the programme name and it's index in the ListView
    public static ListView programmes; // The View component
    public static Programme activeProgramme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            URL url = Util.getSemesterURL();
            programmesMap = new GetProgrammesTask().execute(url).get();
        } catch(Exception ex) {

        }

        ArrayList<String> programmeListData = new ArrayList<String>();
        for(String key : programmesMap.keySet()) {
            programmeListData.add(key);
        }
        Collections.sort(programmeListData);

        programmes = (ListView) findViewById(R.id.programmes);
        programmes.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, programmeListData));

        getIndexList(programmeListData);
        displayIndex();


        programmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String programmeName = (String)parent.getItemAtPosition(position);
                String programmeCode = programmesMap.get(programmeName);
                PostData requestData = getSchedulePostData(programmeCode);
                Programme programme = new Programme(programmeCode);
                programme.scrapeSchedule(requestData);
                Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                intent.putExtra("activeProgramme", programme);
                try {
                    startActivity(intent);
                } catch (Exception ex) {
                    System.out.print(ex.getMessage());
                }
            }
        });
    }


    private PostData getSchedulePostData(String programmeCode) {
        try {
            String __EVENTTARGET = schedulePage.select("input[id=__EVENTTARGET]").first().val();
            String __EVENTARGUMENT = schedulePage.select("input[id=__EVENTARGUMENT]").first().val();
            String __LASTFOCUS = schedulePage.select("input[id=__LASTFOCUS]").first().val();
            String __VIEWSTATE = schedulePage.select("input[id=__VIEWSTATE]").first().val();
            String __VIEWSTATEGENERATOR = schedulePage.select("input[id=__VIEWSTATEGENERATOR]").first().val();
            String __EVENTVALIDATION = schedulePage.select("input[id=__EVENTVALIDATION]").first().val();
            String tLinkType = schedulePage.select("input[id=tLinkType]").first().val();
            String tWildcard = schedulePage.select("input[id=tWildcard]").first().val();
            String lbWeeks = schedulePage.select("select[name=lbWeeks] option[selected=selected]").first().val();
            String lbDays = schedulePage.select("select[name=lbDays] option[selected=selected]").first().val();
            String RadioType = schedulePage.select("input[type=radio][name=RadioType][checked=checked]").first().val();
            String bGetTimetable = schedulePage.select("input[id=bGetTimetable]").first().val();
            return new PostData(__EVENTTARGET, __EVENTARGUMENT, __LASTFOCUS, __VIEWSTATE,
                    __VIEWSTATEGENERATOR, __EVENTVALIDATION, tLinkType, tWildcard, lbWeeks,
                    lbDays, RadioType, bGetTimetable, programmeCode);
        } catch(Exception ex) {
            throw new IllegalArgumentException("Couldn't get post data!");
        }
    }


    private void getIndexList(ArrayList<String> programmes) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < programmes.size(); i++) {
            String programme = programmes.get(i);
            String index = programme.substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(
                    R.layout.side_index_layout, null);
            textView.setText(index);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    @Override
    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        programmes.setSelection(mapIndex.get(selectedIndex.getText()));
    }

    public void saveFile(String data, String filename) {
        Context context = this;
        File file = new File(context.getFilesDir(), filename);
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFile(String filename) {
        Context context = this;
        String filePath = context.getFilesDir() + "/" + filename;
        File file = new File( filePath );
    }


}
