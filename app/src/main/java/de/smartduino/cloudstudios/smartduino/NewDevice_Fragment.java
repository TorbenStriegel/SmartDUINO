package de.smartduino.cloudstudios.smartduino;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import static de.smartduino.cloudstudios.smartduino.MainActivity.httpScanner;

public class NewDevice_Fragment extends Fragment implements View.OnClickListener{

     ToggleButton toggle;
     LinearLayout expertModus;
     View inf;
     RadioButton rb_fernseher;
     RadioButton rb_steckdose;
     RadioButton rb_lampe;
     EditText name;
     EditText codeAn;
     EditText codeAus;

    private int modus = 1;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        inf = inflater.inflate(R.layout.fragment_new_device_, container, false);
        rb_fernseher = (RadioButton) inf.findViewById(R.id.radioButton_Fernseher);
        rb_steckdose = (RadioButton) inf.findViewById(R.id.radioButton_Steckdose);
        rb_lampe = (RadioButton) inf.findViewById(R.id.radioButton_Lampe);
        name =(EditText) inf.findViewById(R.id.gereateName);
        codeAn =(EditText) inf.findViewById(R.id.codeEin);
        codeAus =(EditText) inf.findViewById(R.id.codeAus);

        Button b = inf.findViewById(R.id.button_addDevice);
        b.setOnClickListener(this);

        expertModus= inf.findViewById(R.id.expertMode);
        toggle = (ToggleButton) inf.findViewById(R.id.toggleButton_Modus);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    expertModus.setVisibility(View.GONE);
                }else{
                    expertModus.setVisibility(View.VISIBLE);
                }
            }
        });

       return inf;
    }


    @Override
    public void onClick(View v) {
        if (rb_steckdose.isChecked()) {
            modus = 1;
        } else if (rb_lampe.isChecked()) {
            modus = 2;
        } else if (rb_fernseher.isChecked()) {
            modus = 3;
        }

        if(toggle.isChecked()) {
            //easy Modus
            httpScanner.newDevTAN(modus,2,name.getText().toString());
            Log.d("","Easy Modus");
        }else{
            //expert Modus
            Log.d("","Expert Modus");
            if(codeAn.getText().toString().matches("")||codeAus.getText().toString().matches("")) {
                Snackbar.make(inf, "Please enter both codes or usr the Easy Mode!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                long[] codeArr = new long[2];
                codeArr[0] = Long.parseLong(codeAn.getText().toString());
                codeArr[1] = Long.parseLong(codeAus.getText().toString());
                httpScanner.newDevTACN(modus,codeArr,name.getText().toString());
                Log.d("","Expert Modus");
            }
        }

    }
}
