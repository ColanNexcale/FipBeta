package com.example.lukas.fip;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class MonthManager {

    private ArrayList<String> dateList;
    private Context context;
    private HashMap<String, Day> dayList;
    private HashMap<Integer, String> months;

    MonthManager(Context context, ArrayList<String> dateList, HashMap<String, Day> dayList){
        this.context = context;
        this.dateList = dateList;
        this.dayList = dayList;
        months = getMonths();
    }



    float getMonthSum (String monthAsNumberLiteral){
        ArrayList<Day> list = getDaysInMonth(monthAsNumberLiteral);
        float sum = 0;
        for (Day day : list ){
            sum += day.getDaySum();
        }
        return sum;
    }

    String getMonthName(String date){
        return months.get(Integer.parseInt(date.split("\\.")[1]));
    }

    private ArrayList<Day> getDaysInMonth (String monthAsNumberLiteral){
        return filterDayList(dayList, monthAsNumberLiteral);
    }


    private ArrayList<Day> filterDayList(HashMap<String, Day> dayList, String predicate){
        ArrayList<Day> filtered = new ArrayList<>();
        for(String date : dayList.keySet()){
            if (date.split("\\.")[1].equals(predicate))
                filtered.add(dayList.get(date));
        }
        return filtered;
    }

    private HashMap<Integer, String> getMonths(){
        HashMap <Integer, String> mon = new HashMap<>();
        mon.put(1, "Januar");
        mon.put(2, "Februar");
        mon.put(3, "März");
        mon.put(4, "April");
        mon.put(5, "Mai");
        mon.put(6, "Juni");
        mon.put(7, "Juli");
        mon.put(8, "August");
        mon.put(9, "September");
        mon.put(10, "Oktober");
        mon.put(11, "November");
        mon.put(12, "Dezember");
        return mon;
    }

}
