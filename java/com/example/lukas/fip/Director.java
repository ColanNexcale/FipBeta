package com.example.lukas.fip;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;




public class Director {

    String date;
    Context context;
    private FipView fipView;
    private DayManager dayManager;
    private DateManager dateManager;
    private MonthManager monthManager;
    private Day dayOverview, dayInspector;
    private Overview overviewFragment;
    private Inspector inspectorFragment;



    Director(Activity activity, Context context){
        date = DateFormat.getDateInstance(DateFormat.MEDIUM ,Locale.GERMAN).format(new Date());

        fipView = new FipView(activity, context);

        this.context = context;

        //init managers
        dateManager = new DateManager(context, date);
        dayManager = new DayManager(context, date);
        monthManager = new MonthManager(context, dateManager.getDateList(), dayManager.getDayList());

        dayOverview = dayManager.getDay(date);
        dayInspector = dayManager.getDay(date);

    }

/* TODO
- add dropdown menu for day inspection
- add new tab: Month inspection, lists expenses monthwise
- add option to insert montly budget and subtract new expenses / show month sum in overview


 */


    void initializeGUI(BasicUIInterface source){

        if (source.getClass() == Overview.class)
            overviewFragment = (Overview)source;
        else if (source.getClass() == Inspector.class)
            inspectorFragment = (Inspector)source;

        setUI(source, dayOverview, true);
    }



    private void setUI (BasicUIInterface source, Day day, boolean setFullDay){
        fipView.setDate(source, date);
        fipView.setDaySumDate(source, day.getDate());
        fipView.setDaySumValue(source, Float.toString(day.getDaySum()));

        if (source.getClass() == Overview.class){
            fipView.setMonthSum((Overview)source, Float.toString(monthManager.getMonthSum(day.getDate().split("\\.")[1])));
            fipView.setMonth ((Overview)source, monthManager.getMonthName(day.getDate()));
        }

        if (source.getClass() == Inspector.class)
            setDayDetails((Inspector)source, day, setFullDay);
    }


    private void setDayDetails(DetailedDayUIInterface source, Day day, boolean setFullDay){
        LinkedList<Day.Expense> temp;
        if ((temp = day.getExpenses()) != null){
            if (setFullDay){
                fipView.clearView(source);
                for (Day.Expense exp : temp){
                    fipView.addDisplayedExpense(source, exp.getNote(), Float.toString(exp.getAmount()));
                }
            }else{
                Day.Expense exp = temp.getLast();
                fipView.addDisplayedExpense(source, exp.getNote(), Float.toString(exp.getAmount()));
            }
        }
    }


    void saveData(){
        dayManager.save();
        dateManager.save();
    }




    boolean addExpense(String amount, String note){
        boolean success = dayManager.addExpense(dayOverview.getDate(), amount, note);
        if (success){
            setUI(overviewFragment, dayOverview, false);
            if (dayOverview == dayInspector)
                setUI(inspectorFragment, dayOverview, false);
        }
        return success;
    }

    String getDate(){
        return date;
    }


    void changeDay(Fragment source, int direction){
        if (source.getClass() == Overview.class){
            dayOverview = getRequestedDay(dayOverview, dateManager.getDateList(), direction);
            setUI((Overview)source, dayOverview, false);
        }

        if (source.getClass() == Inspector.class){
            dayInspector = getRequestedDay(dayInspector, dateManager.getDateList(), direction);
            setUI((Inspector)source, dayInspector, true);
        }
    }

    private Day getRequestedDay(Day currentDay, ArrayList<String> dateList, int direction){
        int position = dateList.indexOf(currentDay.getDate());
        Day tempDay = currentDay;
        if (!(direction == 1 && position == dateList.size()-1) && !(direction == -1 && position == 0)){
            tempDay = dayManager.getDay(dateList.get(position+direction));
        }
        return tempDay;

    }

    //TODO
    //implement for drop down in detailed view
    void changeDay(Fragment source, String date){
    }

    //TODO => task of ConnectionManager
    void sendToServer(){
    }


}

