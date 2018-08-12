package com.example.lukas.fip;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;

public class FipController {

    private Director director;

    FipController(Activity activity, Context context){
        director = new Director(activity, context);
    }

    boolean addExpense(String amount, String note){
        return director.addExpense(amount, note);
    }

    void saveData(){
        director.saveData();
    }

    void initializeGUI(BasicUIInterface source){
        director.initializeGUI(source);
    }

    String getDate(){
        return director.getDate();
    }

    void changeDay(Fragment source, int direction){
        director.changeDay(source, direction);
    }

    void changeDay(Fragment source, String date){
        director.changeDay(source, date);
    }
}
