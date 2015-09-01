package martinothamar.uiatimeplan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import martinothamar.uiatimeplan.Models.Day;
import martinothamar.uiatimeplan.Models.Programme;
import martinothamar.uiatimeplan.Models.Week;

public class ScheduleActivity extends Activity {
    public Programme activeProgramme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Intent intent = getIntent();
        this.activeProgramme = (Programme)intent.getSerializableExtra("activeProgramme");
        TableLayout table = (TableLayout)findViewById(R.id.table);

        for (Week week : activeProgramme.getSchedule()){
            TableRow weekRow = new TableRow(this);
            TextView textText = new TextView(this);
            textText.setText("Ukenummer " + week.weekNumber);
            weekRow.addView(textText);
            table.addView(weekRow);
            for(Day day : week.days) {
                for(martinothamar.uiatimeplan.Models.Activity activity : day.activities) {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm");

                    TableRow activityRow = new TableRow(this);
                    TextView time = new TextView(this); time.setText(dateFormat.format(activity.getStart().getTime()) + "-" +
                            dateFormat.format(activity.getEnd().getTime())); activityRow.addView(time);
                    TextView course = new TextView(this); course.setText(activity.getCourses().get(0)); activityRow.addView(course);
                    TextView lecturer = new TextView(this); lecturer.setText(activity.getLecturer()); activityRow.addView(lecturer);
                    TextView room = new TextView(this); time.setText(activity.getRooms().get(0)); activityRow.addView(room);

                    table.addView(activityRow);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
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
