package ca.brocku.as12ga.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MyScheduleActivity extends AppCompatActivity {
    private final static int MY_MEETNGS = 10;

    ScrollView list;
    TextView total;
    Schedule mSchedule;
    Button poll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        mSchedule = (Schedule) getIntent().getSerializableExtra("Schedule");
        total = (TextView) findViewById(R.id.total);
        list = (ScrollView) findViewById(R.id.list);
        poll = (Button) findViewById(R.id.poll);

        display();
    }

    public void display (){
        total.setText("");
        total.append("Total # of Meetings planned: " + mSchedule.getLength());

        list.removeAllViews();
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        int[] values;
        for (int i = 0; i < mSchedule.getLength(); i++){
            final Meeting in = mSchedule.getMeeting(i);
            values = in.toIntArray();
            final TextView meet = new TextView(this);
            meet.append(in.getTitle() + "\n");
            meet.append(values[0] + "/" + values[1] + "/" + values[2] + " at " + values[3] + ":" + values[4]);
            meet.setClickable(true);
            meet.setId(MY_MEETNGS + i);
            meet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (meet.getId() == v.getId()){
                        Toast.makeText(getBaseContext(), in.getDescription(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            ll.addView(meet);
        }
        list.addView(ll);

        if (mSchedule.getLength() <= 0){
            poll.setClickable(false);
        }
        else {
            poll.setClickable(true);
        }

    }

    public void poll(View view){
        mSchedule.remove();
        display();
        Toast.makeText(this, "First Meeting Polled (Removed)", Toast.LENGTH_SHORT).show();
    }
}
