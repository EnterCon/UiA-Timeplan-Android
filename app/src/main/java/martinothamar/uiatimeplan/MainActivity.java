package martinothamar.uiatimeplan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            URL url = Utilities.getSemesterURL();
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
                startActivity(intent);
            }
        });
    }


    private PostData getSchedulePostData(String programmeCode) {
        Element __EVENTTARGET = schedulePage.select("input[id=__EVENTTARGET]").first();
        Element __EVENTARGUMENT = schedulePage.select("input[id=__EVENTARGUMENT]").first();
        Element __LASTFOCUS = schedulePage.select("input[id=__LASTFOCUS]").first();
        Element __VIEWSTATE = schedulePage.select("input[id=__VIEWSTATE]").first();
        Element __VIEWSTATEGENERATOR = schedulePage.select("input[id=__VIEWSTATEGENERATOR]").first();
        Element __EVENTVALIDATION = schedulePage.select("input[id=__EVENTVALIDATION]").first();
        Element tLinkType = schedulePage.select("input[id=tLinkType]").first();
        Element tWildcard = schedulePage.select("input[id=tWildcard]").first();
        Element lbWeeks = schedulePage.select("select[name=lbWeeks]option[selected=selected]").first();
        Element lbDays = schedulePage.select("select[name=lbDays]option[selected=selected]").first();
        Element RadioType = schedulePage.select("input[type=radio][name=RadioType][checked=checked]").first();
        Element bGetTimetable = schedulePage.select("input[id=bGetTimetable]").first();
        return new PostData(__EVENTTARGET.val(), __EVENTARGUMENT.val(), __LASTFOCUS.val(), __VIEWSTATE.val(),
                __VIEWSTATEGENERATOR.val(), __EVENTVALIDATION.val(), tLinkType.val(), tWildcard.val(), lbWeeks.val(),
                lbDays.val(), RadioType.val(), bGetTimetable.val(), programmeCode);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
