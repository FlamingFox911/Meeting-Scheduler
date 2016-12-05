package ca.brocku.as12ga.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MeetingDetail extends AppCompatActivity {
    TextView title, description, time, location;
    Button delete;
    int id;
    Schedule mSchedule;
    Meeting mMeeting;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_meeting_detail);
        Intent in = getIntent();

        id = in.getIntExtra("ID", -1);
        mSchedule = (Schedule) getIntent().getSerializableExtra("Schedule");
        title = (TextView) findViewById(R.id.titleMeeting);
        description = (TextView) findViewById(R.id.descriptionMeeting);
        time = (TextView) findViewById(R.id.timeMeeting);
        location = (TextView) findViewById(R.id.locationMeeting);
        delete = (Button) findViewById(R.id.delete);

        display();
    }

    public void display(){
        mMeeting = mSchedule.getMeeting(id);
        title.setText(mMeeting.getTitle());
        description.setText(mMeeting.getDescription());
        location.setText(mMeeting.getLocation());
        int[] timeArr = mMeeting.toIntArray();
        time.setText(timeArr[0] + "/" + timeArr[1] + "/" + timeArr[2] + " at " + timeArr[3] + ":" + timeArr[4]);
        delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mSchedule.remove(id);
                Toast.makeText(getApplicationContext(), "Meeting Deleted", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Schedule", mSchedule);
                setResult(MyScheduleActivity.RESULT_OK, returnIntent);
                finish();
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState (Bundle b){
        b.putSerializable("Schedule", mSchedule);
        b.putString("Title", title.getText().toString());
        b.putString("Description", description.getText().toString());
        b.putString("Location", location.getText().toString());
        b.putInt("ID", id);
        super.onSaveInstanceState(b);
    }

    @Override
    public void onRestoreInstanceState(Bundle b){
        super.onRestoreInstanceState(b);
        mSchedule = (Schedule) b.getSerializable("Schedule");
        title.setText(b.getString("Title"));
        description.setText(b.getString("Description"));
        location.setText(b.getString("Location"));
        id = b.getInt("ID");
        display();
    }
}
