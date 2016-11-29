package ca.brocku.as12ga.assignment2;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Schedule mSchedule;
    EditText mTitle, mDescription;
    DatePicker mDate;
    TimePicker mTime;
    Button add;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        mSchedule = new Schedule();
        mTitle = (EditText) findViewById(R.id.title);
        mDescription = (EditText) findViewById(R.id.description);
        mDate = (DatePicker) findViewById(R.id.datePicker);
        mTime = (TimePicker) findViewById(R.id.timePicker);
        add = (Button) findViewById(R.id.add);
    }

    public void proceed(View view){
        Intent intent = new Intent(this.getApplicationContext(), MyScheduleActivity.class);
        intent.putExtra("Schedule", mSchedule);
        startActivity(intent);
    }

    public void addMe(View view){
        Meeting m = new Meeting();
        m.setTitle(mTitle.toString());
        m.setDescription(mDescription.toString());
        m.setDate(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), mTime.getHour(), mTime.getMinute());
        mSchedule.add(m);
        Toast.makeText(this, "Created Meeting.", Toast.LENGTH_SHORT).show();
        add.setClickable(false);

        // Credit to Eric B. for this Timeout sequence.
        final Handler handler = new Handler();
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        add.setClickable(true);
                    }
                });
            }
        },Toast.LENGTH_SHORT);
    }
}
