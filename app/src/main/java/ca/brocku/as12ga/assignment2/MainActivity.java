package ca.brocku.as12ga.assignment2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    EditText mTitle, mDescription, mLocation;
    DatePicker mDate;
    TimePicker mTime;
    Button add;
    SharedPreferences mPrefs;
    Context mContext;
    int total;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mPrefs = mContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mSchedule = getData();

        mTitle = (EditText) findViewById(R.id.title);
        mDescription = (EditText) findViewById(R.id.description);
        mLocation = (EditText) findViewById(R.id.location);
        mDate = (DatePicker) findViewById(R.id.datePicker);
        mTime = (TimePicker) findViewById(R.id.timePicker);
        add = (Button) findViewById(R.id.add);
    }

    @Override
    public void onSaveInstanceState (Bundle b){
        b.putSerializable("Schedule", mSchedule);
        b.putString("Title", mTitle.getText().toString());
        b.putString("Description", mDescription.getText().toString());
        b.putString("Location", mLocation.getText().toString());
        super.onSaveInstanceState(b);
    }

    @Override
    public void onRestoreInstanceState(Bundle b){
        super.onRestoreInstanceState(b);
        mSchedule = (Schedule) b.getSerializable("Schedule");
    }

    @Override
    public void onBackPressed() {
        saveData(mSchedule);
        finish();
    }

    public void saveData(Schedule obj){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt("total", total);
        for (int i = 0; i < total; i++){
            editor.putString("Title" + i, obj.getMeeting(i).getTitle());
            editor.putString("Description" + i, obj.getMeeting(i).getDescription());
            editor.putLong("Date" + i, obj.getMeeting(i).getTime());
            editor.putString("Location" + i, obj.getMeeting(i).getLocation());
        }
        editor.apply();
    }

    public Schedule getData(){
        Schedule result = new Schedule();
        total = mPrefs.getInt("total", 0);
        for (int i = 0; i < total; i++){
            String title = mPrefs.getString("Title" + i, "");
            String description = mPrefs.getString("Description" + i, "");
            String location = mPrefs.getString("Location" + i, "");
            long date = mPrefs.getLong("Date" + i, 0);
            Meeting in = new Meeting();
            in.setTitle(title);
            in.setDescription(description);
            in.setLocation(location);
            in.setDate(date);
            result.add(in);
        }
        return result;
    }

    public void proceed(View view){
        Intent intent = new Intent(this.getApplicationContext(), MyScheduleActivity.class);
        intent.putExtra("Schedule", mSchedule);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == MyScheduleActivity.RESULT_OK){
                mSchedule = (Schedule) data.getSerializableExtra("Schedule");
                total = mSchedule.getLength();
            }
        }
    }

    public void addMe(View view){
        Meeting m = new Meeting();
        m.setTitle(mTitle.getText().toString());
        m.setDescription(mDescription.getText().toString());
        m.setDate(mDate.getYear(), mDate.getMonth() + 1, mDate.getDayOfMonth(), mTime.getHour(), mTime.getMinute());
        m.setLocation(mLocation.getText().toString());
        mSchedule.add(m);

        Toast.makeText(this, "Created Meeting.", Toast.LENGTH_SHORT).show();
        mTitle.setText("");
        mDescription.setText("");
        mLocation.setText("");
        add.setClickable(false);

        // Credit to Eric B. for this Timeout sequence.
        // Basically denies user from adding multiple Meetings very quickly.
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
