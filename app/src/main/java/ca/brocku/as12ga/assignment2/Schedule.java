package ca.brocku.as12ga.assignment2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by flami on 2016-11-27.
 * STUFF
 */

public class Schedule extends Meeting implements Serializable {
    private static final long serialVersionUID = 65465454753654L;

    private PriorityQueue<Meeting> schedule;
    private ArrayList<Meeting> list;

    public Schedule(){
        super();
        this.schedule = new PriorityQueue<Meeting>();
        this.list = new ArrayList<Meeting>();
    }

    public int getLength(){
        return this.schedule.size();
    }

    public void add(Meeting m){
        this.schedule.add(m);
        this.makeList();
    }

    public Meeting remove(){
        Meeting result = this.schedule.remove();
        this.makeList();
        return result;
    }

    public Meeting getMeeting(int i){
        return list.get(i);
    }

    private void makeList(){
        list = new ArrayList<Meeting>();
        for (Meeting m : schedule){
            list.add(m);
        }
    }
}
