package com.example.lukas.fip;


import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class DateManager {

    private FileManager fileMan;
    private Context context;
    private final String fileName = "dates.txt";
    private String currentDate;
    private ArrayList<String> dateList;
    private String initDate;

    DateManager(Context context, String date){
        this.context = context;
        fileMan = new FileManager(context, fileName);
        initDate = date;
        dateList = loadDateList();
        addDate(date);

    }


    void save(){
        saveDateList(dateList);
        Toast.makeText(context, "Daylist saved", Toast.LENGTH_SHORT).show();
    }


    private ArrayList<String> loadDateList(){

        return fileMan.loadDateList();
    }

    private void saveDateList(ArrayList<String> dl){
        fileMan.saveDateList(dl);
    }

    void addDate(String date){
        if (dateList.contains(date))
            return;
        else{
            dateList.add(date);
            currentDate = date;
        }
    }

    String getCurrentDate(){
        return currentDate;
    }

    ArrayList<String> getDateList(){
        return dateList;
    }
}
