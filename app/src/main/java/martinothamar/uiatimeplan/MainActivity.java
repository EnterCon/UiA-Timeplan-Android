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
import android.widget.Toast;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                CharSequence text = "";

                String programmeName = (String)parent.getItemAtPosition(position);
                String programmeCode = programmesMap.get(programmeName);

                Programme programme = null;
                File f = new File(getFilesDir() + "/"+programmeCode+".ser");
                Date lastModified = new Date(f.lastModified());
                Date currentDate = new Date(System.currentTimeMillis());

                if (f.exists() == true && (System.currentTimeMillis() - f.lastModified()) / (1000*60*60*24) < 14 ){
                    text = "Henter lagret timeplan..";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    programme = readFile(programmeCode);
                } else {
                    text = "Lagrer timeplan..";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    PostData requestData = getSchedulePostData(programmeCode);
                    programme = new Programme(programmeCode);
                    programme.scrapeSchedule(requestData);
                    saveFile(programme);
                }

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

    public void saveFile(Programme programme) {
        try{
            //use buffering
            OutputStream file = new FileOutputStream(getFilesDir() + "/"+ programme.getId() +".ser");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);
            try{
                output.writeObject(programme);
            }
            finally{
                output.close();
            }
        }
        catch(IOException ex){
            System.out.println("SaveFile: " + ex.getMessage());
        }
    }

    public Programme readFile(String programmeCode) {
        Programme programme = null;
        try{
            //use buffering
            InputStream file = new FileInputStream(getFilesDir() + "/"+ programmeCode +".ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            try{
                //deserialize the List
                programme = (Programme)input.readObject();
            }
            finally{
                input.close();
            }
        }
        catch(ClassNotFoundException ex){
            System.out.println("ReadFile: " + ex.getMessage());
        }
        catch(IOException ex){
            System.out.println("ReadFile: " + ex.getMessage());
        }
        return programme;
    }


}
