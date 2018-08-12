package com.example.lukas.fip;

import java.io.Serializable;
import java.util.LinkedList;



public class Day implements Serializable {

    class Expense{
        private float amount;
        private String note;
        Expense (String note, float amount){
            this.amount = amount;
            this.note = note;
        }
        float getAmount(){
            return amount;
        }
        String getNote(){
            return note;
        }
    }

    public static final String undefined = "Sonstiges";

    public enum ServerCommand{FINISH, ADD, CLOSE, SAVE;}

    private String date;
    private float[] expensesDBready;
    private float daySum = 0;
    private ServerCommand cmd;
    private LinkedList<Expense> expenses;

    Day(String date){
        this.date = date;
        expenses = new LinkedList<>();
    }

    void addEntry (String note, float value){
        if (expenses == null)
            expenses = new LinkedList<>();

        String tempNote = note.replaceAll("\n", "");

        if (note.isEmpty()){
            tempNote = undefined;
            boolean found = false;
            for (Expense ele : expenses ) {
                if (ele.getNote().equals(undefined)){
                    float oldValue = ele.getAmount();
                    int position = expenses.indexOf(ele);
                    expenses.remove(ele);
                    expenses.add(new Expense(tempNote, value+oldValue));
                    found = true;
                    break;
                }
            }
            if (!found)
                expenses.add(new Expense(tempNote, value));
        }
        else{
            expenses.add(new Expense(tempNote, value));
        }
        daySum += value;
        cmd = ServerCommand.ADD;
    }

    String getDate(){
        return date;
    }

    float getDaySum(){
        return daySum;
    }

    LinkedList<Expense> getExpenses(){
        return expenses;
    }

    ServerCommand getServerCommand (){
        return cmd;
    }

}




