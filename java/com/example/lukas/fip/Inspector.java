package com.example.lukas.fip;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.lukas.fip.Day.undefined;


public class Inspector extends Fragment implements DetailedDayUIInterface{

    private static final String TAG = "InspectorFragment";

    private InspectorCompleteListener icl;

    public Inspector() {
    }



    private FloatingActionButton nextDayBtn, previousDayBtn;
    private TableLayout table;
    private Context context;
    private TextView dateView, daySumDate, daySumValue;
    private TableRow.LayoutParams tableRowLayout;
    private Inspector self;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_inspector_fragment, container,false);
        context = getActivity().getApplicationContext();
        self = this;

        dateView = (TextView)view.findViewById(R.id.dateInspector);
        daySumDate = (TextView)view.findViewById(R.id.daySumDateInspector);
        daySumValue = (TextView)view.findViewById(R.id.daySumValueInspector);

        nextDayBtn = (FloatingActionButton) view.findViewById(R.id.fabNextDayInspector);
        nextDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                ((MainActivity)getActivity()).changeDay(self, 1);
            }
        });


        previousDayBtn = (FloatingActionButton) view.findViewById(R.id.fabPreviousDayInspector);
        previousDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                ((MainActivity)getActivity()).changeDay(self, -1);

            }
        });

        table = view.findViewById(R.id.inspectorDetails);
        tableRowLayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);

        icl.onInspectorComplete();
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            icl = (InspectorCompleteListener) context;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setDate(String date){
        dateView.setText(date);
    }

    @Override
    public void setDaySumValue(String value){
        daySumValue.setText(value);
    }

    @Override
    public void setDaySumDate(String date){
        daySumDate.setText(date);
    }

    @Override
    public void addExpense(String note , String value){
        if(note.equals(undefined))
            updateUndefined(value);
        else
            addEntry(note, value);
    }
    @Override
    public void clearEntries(){
        table.removeAllViews();
    }


    private void addEntry(String note, String value){


            TableRow row = new TableRow(context);
            row.setLayoutParams(tableRowLayout);


            TextView noteView = new TextView(context);
            noteView.setText(note);
            noteView.setTextColor(Color.BLACK);
            noteView.setPadding(10, 2, 10, 2);
            TextView valueView = new TextView(context);
            valueView.setText(value);
            valueView.setTextColor(Color.BLACK);
            valueView.setPadding(10, 2, 10, 2);
            row.addView(noteView);
            row.addView(valueView);

            table.addView(row);

    }

    private boolean updateUndefined(String value){
        for (int i = 0 ; i < table.getChildCount(); i++){
            TableRow row = (TableRow)table.getChildAt(i);
            if (((TextView)row.getChildAt(0)).getText().equals(undefined)){
                ((TextView)row.getChildAt(1)).setText(value);
                return true;
            }
        }
        addEntry(undefined, value);
        return false;
    }
}
