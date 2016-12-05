package ca.brocku.as12ga.assignment2;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Meeting implements Comparable<Meeting>, Serializable {
    private static final long serialVersionUID = 65465454753654L;

    private String title, description, location;
    private Date date;
    SimpleDateFormat f;

    public Meeting(){
        this(null, null, "1970,01,01,00,00", null);
    }

    @SuppressLint("SimpleDateFormat")
    public Meeting(String t, String i, String d, String l){
        this.f = new SimpleDateFormat("yyyy,MM,dd,HH,mm");
        this.setTitle(t);
        this.setDescription(i);
        this.setDate(d);
        this.setLocation(l);
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

    public void setDate(long l){
        date.setTime(l);
    }

    @SuppressLint("SimpleDateFormat")
    public boolean isThisDay(Date day){
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.setTime(date);
        to.setTime(day);

        return from.get(Calendar.YEAR) == to.get(Calendar.YEAR) && from.get(Calendar.MONTH) == to.get(Calendar.MONTH) && from.get(Calendar.DAY_OF_MONTH) == to.get(Calendar.DAY_OF_MONTH);
    }
    public void incTime(int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, i);
        date = c.getTime();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String in) {
        this.location = in;
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
