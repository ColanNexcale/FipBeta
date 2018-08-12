package com.example.lukas.fip;


import android.content.Context;
import android.widget.Toast;
import java.util.HashMap;
import java.util.LinkedList;


public class DayManager {

    private FileManager fileMan;
    private Context context;
    private HashMap<String, Day> dayList;
    private String initDate;

    private final String dayDivider = "##";
    private final String dayDataDivider = "#+";
    private final String dayDataDivPattern = "#\\+";
    private final String dayExpDivider = "#-";
    private final int initialHashCapacity = 30;


    DayManager(Context context, String currentDate){
        this.context = context;
        fileMan = new FileManager(context, "days.txt");
        initDate = currentDate;

        dayList = loadDayList();

        checkAndUpdate(currentDate);

    }

    //TODO
    /*

     */

    void checkAndUpdate(String date){
       if (!checkDate(date)){
           dayList.put(date, new Day(date));
       }
    }


    Day getDay(String date){
        return dayList.get(date);
    }

    boolean addExpense(String date, String amount, String note) {
        float fAmount;
        Day day = dayList.get(date);
        if (validateInput(amount))
            fAmount = parseAmount(amount);
        else
            return false;
        day.addEntry(note, fAmount);
        return true;
    }


    public void save(){
        saveDays(parseDayListToString(dayList));
        Toast.makeText(context, "Days saved", Toast.LENGTH_SHORT).show();
    }



    private HashMap<String, Day> getDayListFromString(String dayListStr){
        HashMap<String, Day> tempDL = new HashMap<>(initialHashCapacity);

        if(dayListStr != null && !dayListStr.isEmpty()){
            String[] days = dayListStr.split(dayDivider);
            for (String day : days) {
                String[] daySplit = day.split(dayDataDivPattern);
                Day tempDay = new Day(daySplit[0]);

                for (int i = 1; i < daySplit.length; i++) {
                    String[] expSplit = daySplit[i].split(dayExpDivider);
                    tempDay.addEntry(expSplit[0], Float.parseFloat(expSplit[1]));
                }
                tempDL.put(tempDay.getDate(), tempDay);
            }
        }

        return tempDL;
    }

    private String parseDayListToString(HashMap<String, Day> dl){
        StringBuilder sb = new StringBuilder();

        for (String key : dl.keySet()){
            Day temp = dl.get(key);
            sb.append(temp.getDate());
            sb.append(dayDataDivider);

            LinkedList<Day.Expense> tempExp = temp.getExpenses();
            for(Day.Expense exp : tempExp){
                sb.append(exp.getNote());
                sb.append(dayExpDivider);
                sb.append(Float.toString(exp.getAmount()));
                sb.append(dayDataDivider);
            }
            sb.append(dayDivider);
        }
        return sb.toString();
    }

    private boolean checkDate (String date){
        return dayList.containsKey(date);
    }

    private void saveDays(String parsedDayList){
        fileMan.writeDaysToFile(parsedDayList);
    }

    private HashMap<String, Day> loadDayList(){
        return getDayListFromString(fileMan.getDaysFromFile());
    }



   private boolean validateInput (String amount){
        boolean validChars = true;
        for (int i = 0; i < amount.length(); i++){
            if(!Character.isDigit(amount.charAt(i)) && (amount.charAt(i) != ',' && amount.charAt(i) != '.'))
                validChars = false;
        }
        return validChars;
    }

    private float parseAmount (String amount){
        if (amount.charAt(0) == '.' || amount.charAt(0) == ',')
            amount = "0"+amount;
        return Float.parseFloat(amount);
    }




}
