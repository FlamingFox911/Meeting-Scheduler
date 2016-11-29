package ca.brocku.as12ga.assignment2;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by flami on 2016-11-27.
 * Stuff
 */

public class Meeting implements Comparable<Meeting>, Serializable {
    private static final long serialVersionUID = 65465454753654L;

    private String title, description;
    private Date date;
    SimpleDateFormat f;

    public Meeting(){
        this(null, null, null);
    }

    @SuppressLint("SimpleDateFormat")
    public Meeting(String t, String i, String d){
        this.f = new SimpleDateFormat("yyyy,MM,dd,HH,mm");
        this.setTitle(t);
        this.setDescription(i);
        this.setDate(d);
    }

    public void setDate(int year, int month, int day, int hour, int minute){
        String in = year + "," + month + "," + day + "," + hour + "," + minute;
        setDate(in);
    }

    public void setDate(String s){
        if (s != null) {
            try {
                date = f.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public long getTime(){
        return date.getTime();
    }

    public String toString(){
        return f.format(date);
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setTitle(String in){
        title = in;
    }

    public void setDescription(String in){
        description = in;
    }

    public int[] toIntArray(){
        String[] sArray = f.format(date).split(",");
        int[] result = new int[sArray.length];
        for(int i = 0; i < sArray.length; i++) {
            result[i] = Integer.parseInt(sArray[i]);
        }
        return result;
    }

    @Override
    public int compareTo(@NonNull Meeting m) {
        if (this.getTime() < m.getTime()){
            return -1;
        }
        else if (this.getTime() > m.getTime()){
            return 1;
        }
        else if (this.getTitle().compareToIgnoreCase(m.getTitle()) < 0){
            return -1;
        }
        else if (this.getTitle().compareToIgnoreCase(m.getTitle()) > 0){
            return 1;
        }
        else {
            return 0;
        }
    }
}
