package com.example.lukas.fip;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Overview extends Fragment implements BasicUIInterface {

    private static final String TAG = "OverviewFragment";


    private FipController fCon;
    private View view;
    private OverviewCompleteListener ocl;
    private TextView dateView, daySumDateView, daySumValue;
    private Fragment self;


    public Overview() {
    }



    private FloatingActionButton nextDayBtn, previousDayBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.overview_fragment, container,false);
        fCon = ((MainActivity)getActivity()).getController();

        dateView = ((TextView)view.findViewById(R.id.dateOverview));
        daySumDateView = ((TextView)view.findViewById(R.id.daySumDateOverview));
        daySumValue = ((TextView)view.findViewById(R.id.daySumValueOverview));
        self = this;

        nextDayBtn = (FloatingActionButton) view.findViewById(R.id.fabNextDayOverview);
        nextDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeDay(self , 1);
            }
        });


        previousDayBtn = (FloatingActionButton) view.findViewById(R.id.fabPreviousDayOverview);
        previousDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeDay(self , -1);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.addInput);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog input = new InputDialog();
                input.show(((MainActivity)getActivity()).getFragmentManager(), "Betrag hinzufügen");
            }
        });
        ocl.onOverviewComplete();
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            ocl = (OverviewCompleteListener) context;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void setDate(String date){
        dateView.setText(date);
    }

    @Override
    public void setDaySumDate(String date){
        daySumDateView.setText(date);
    }

    @Override
    public void setDaySumValue(String value){
        daySumValue.setText(value);
    }



}
