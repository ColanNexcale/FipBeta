package com.example.lukas.fip;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import android.support.v4.app.Fragment;

public class FipView {


    private Activity mainActivity;
    private Context context;
    private Fragment overview, inspector;

    FipView(Activity activity, Context context){
        this.context = context;
        mainActivity = activity;
    }




    void setDate(BasicUIInterface source, String date){
        source.setDate(date);
    }


    void setDaySumDate(BasicUIInterface source, String date){
        source.setDaySumDate(date);
    }

    void setMonthSum (OverviewInterface source, String monthSum ){
        source.setMonthSum(monthSum);
    }

    void setMonth(OverviewInterface source, String month){
        source.setMonth(month);
    }

    void setDaySumValue(BasicUIInterface source, String value){
        source.setDaySumValue(value);
    }

    void addDisplayedExpense(DetailedDayUIInterface source, String note, String value){
        source.addExpense(note, value);
    }

    void clearView (DetailedDayUIInterface source){
        source.clearEntries();
    }
}
