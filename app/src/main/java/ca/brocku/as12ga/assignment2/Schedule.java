package ca.brocku.as12ga.assignment2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Schedule extends Meeting implements Serializable {
    private static final long serialVersionUID = 65465454753654L;

    private PriorityQueue<Meeting> schedule;
    private ArrayList<Meeting> list;

    public Schedule(){
        super();
        this.schedule = new PriorityQueue<>();
        this.list = new ArrayList<>();
    }

    public int getLength(){
        return this.schedule.size();
    }

    public void add(Meeting m){
        this.schedule.add(m);
        this.makeList();
    }

    public Meeting remove(){
        return this.remove(0);
    }

    public int contains(Meeting to){
        for (int i = 0; i < schedule.size(); i++){
            Meeting m = getMeeting(i);
            if (m.equals(to)){
                return i;
            }
        }
        return -1;
    }

    public Meeting remove(int i){
        Meeting result = list.remove(i);
        PriorityQueue<Meeting> newSchedule = new PriorityQueue<>();
        newSchedule.addAll(list);
        schedule = newSchedule;
        return result;
    }

    public Meeting getMeeting(int i){
        return list.get(i);
    }

    private void makeList(){
        list = new ArrayList<>();
        for (Meeting m : schedule){
            list.add(m);
        }
    }
}
