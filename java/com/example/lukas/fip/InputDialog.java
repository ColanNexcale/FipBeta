package com.example.lukas.fip;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputDialog extends DialogFragment {

    EditText inputAmount;
    EditText inputNote;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.input_dialog, container, false);
        getDialog().setTitle("Neue Ausgabe hinzufügen");


        inputAmount = (EditText) rootView.findViewById(R.id.inputAmountSpent);
        inputNote = (EditText) rootView.findViewById(R.id.inputNote);

        Button addBtn = (Button) rootView.findViewById(R.id.inputDialogAddButton);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String amount = inputAmount.getText().toString();
                if (amount.equals("")){
                    showToast("Kein Betrag eingegeben");
                }else{
                    String note = inputNote.getText().toString();
                    if (((MainActivity)getActivity()).addExpense(amount, note )){
                        showToast("Betrag hinzugefügt");
                        inputAmount.setText("");
                        inputNote.setText("");
                        dismiss();
                    }else{
                        showToast("Ungültiger Betrag");
                        inputAmount.setText("");
                    }
                }
            }
        });

        Button cancelBtn = (Button) rootView.findViewById(R.id.inputDialogCancelButton);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dismiss();
            }
        });
        return rootView;
    }

    void showToast(CharSequence text){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(MainActivity.mainContext, text, duration);
        toast.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.inputDialogTitle);
    }

}
