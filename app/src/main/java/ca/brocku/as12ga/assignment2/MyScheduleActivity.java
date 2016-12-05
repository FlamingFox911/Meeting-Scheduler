package ca.brocku.as12ga.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MyScheduleActivity extends AppCompatActivity {
    private final static int MY_MEETINGS = 10;

    ScrollView list;
    TextView total;
    Schedule mSchedule, currentView;
    Button clear, filter;
    int filtering;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_my_schedule);

        mSchedule = (Schedule) getIntent().getSerializableExtra("Schedule");
        currentView = new Schedule();
        total = (TextView) findViewById(R.id.total);
        list = (ScrollView) findViewById(R.id.list);
        clear = (Button) findViewById(R.id.clear);
        filter = (Button) findViewById(R.id.filter);

        filtering = 0;
        String[] disp = getResources().getStringArray(R.array.filter);
        filter.setText(disp[filtering]);
        display(filtering);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pushMeeting:
                Date today = Calendar.getInstance().getTime();
                for (int i = 0; i < mSchedule.getLength(); i++){
                    Meeting in = mSchedule.getMeeting(i);
                    if (in.isThisDay(today)){
                        mSchedule.remove(i);
                        in.incTime(6);
                        mSchedule.add(in);
                    }
                }
                display(filtering);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void display (int filter){
        list.removeAllViews();
        currentView = new Schedule();
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        int[] values;
        int count = 0;
        Date today = Calendar.getInstance().getTime();
        if (filter == 0 || filter == 1){
            for (int i = 0; i < mSchedule.getLength(); i++){
                final Meeting in = mSchedule.getMeeting(i);
                if (filter == 0 || in.isThisDay(today)){
                    values = in.toIntArray();
                    currentView.add(in);
                    final TextView meet = new TextView(this);
                    meet.setText("");
                    meet.append(in.getTitle() + "\n");
                    meet.append(values[0] + "/" + values[1] + "/" + values[2] + " at " + values[3] + ":" + values[4]);
                    meet.setClickable(true);
                    meet.setId(MY_MEETINGS + i);
                    final Intent intent = new Intent(this, MeetingDetail.class);
                    final int place = i;
                    meet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (meet.getId() == v.getId()){
                                intent.putExtra("Schedule", mSchedule);
                                intent.putExtra("ID", place);
                                startActivityForResult(intent, 1);
                            }
                        }
                    });
                    meet.setPadding(0, 8, 8, 0);
                    ll.addView(meet);
                    count++;
                }
            }
        }
        else if (filter == 2){
            String[] temp = new String[mSchedule.getLength()];
            for (int i = 0; i < mSchedule.getLength(); i++){
                Meeting in = mSchedule.getMeeting(i);
                String loc = in.getLocation();
                if (!loc.equals("")) {
                    for (int j = 0; j < temp.length; j++) {
                        if (temp[j] == null || temp[j].compareTo(loc) == 0) {
                            if (temp[j] == null) {
                                count++;
                            }
                            temp[j] = loc;
                            break;
                        }
                    }
                }
            }
            String[] sArr = new String[count];
            sArr = Arrays.copyOf(temp, sArr.length);
            for (String aSArr : sArr) {
                TextView show = new TextView(this);
                show.setText("");
                show.append(aSArr);
                show.setPadding(0, 8, 8, 0);
                ll.addView(show);
            }
        }
        list.addView(ll);

        total.setText("");
        if (filtering == 0 || filtering == 1) {
            total.append("Total # of Meetings planned");
            if (filtering == 1) total.append(" today");
            total.append(": " + count);
            total.append("\nTap a meeting for more details and options");
        }
        else if (filtering == 2){
            total.append("Total Number of unique locations: " + count);
        }

        if (mSchedule.getLength() <= 0){
            clear.setClickable(false);
        }
        else {
            clear.setClickable(true);
        }

    }

    public void clear(View view){
        for (int i = 0; i < currentView.getLength(); i++){
            int value = mSchedule.contains(currentView.getMeeting(i));
            if (value >= 0){
                mSchedule.remove(value);
            }
        }
        Toast.makeText(this, "All shown meetings removed.", Toast.LENGTH_SHORT).show();
        display(filtering);
    }

    public void filter(View view){
        filtering = (filtering + 1) % 3;
        if (filtering == 2){
            clear.setClickable(false);
        }
        else {
            clear.setClickable(true);
        }
        String[] disp = getResources().getStringArray(R.array.filter);
        filter.setText(disp[filtering]);
        display(filtering);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == MyScheduleActivity.RESULT_OK){
                mSchedule = (Schedule) data.getSerializableExtra("Schedule");
                display(filtering);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("Schedule", mSchedule);
        setResult(RESULT_OK, intent);
        finish();
    }
}