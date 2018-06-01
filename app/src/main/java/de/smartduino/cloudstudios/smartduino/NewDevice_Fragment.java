package de.smartduino.cloudstudios.smartduino;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

public class NewDevice_Fragment extends Fragment {

    private int modus = 1;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View inf = inflater.inflate(R.layout.fragment_new_device_, container, false);
        final RadioButton rb_fernseher = (RadioButton) inf.findViewById(R.id.radioButton_Fernseher);
        final RadioButton rb_steckdose = (RadioButton) inf.findViewById(R.id.radioButton_Steckdose);
        final RadioButton rb_lampe = (RadioButton) inf.findViewById(R.id.radioButton_Lampe);
        final EditText name =(EditText) inf.findViewById(R.id.gereateName);
        final EditText codeAn =(EditText) inf.findViewById(R.id.codeEin);
        final EditText codeAus =(EditText) inf.findViewById(R.id.codeAus);

        Button b =(Button) inf.findViewById(R.id.button_addDevice2);
        b.setOnClickListener(new Button.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     Log.d("","pressed");
                                     if (rb_steckdose.isChecked()) {
                                         modus = 1;
                                     } else if (rb_lampe.isChecked()) {
                                         modus = 2;
                                     } else if (rb_fernseher.isChecked()) {
                                         modus = 3;
                                     }
                                     long[] codeArr = new long[2];
                                     codeArr[0] = Long.parseLong(codeAn.getText().toString());
                                     codeArr[1] = Long.parseLong(codeAus.getText().toString());
                                     httpScanner.newDevTACN(modus,codeArr,name.getText().toString());

                                 }});

        final LinearLayout expertModus= inf.findViewById(R.id.expertMode);
        ToggleButton toggle = (ToggleButton) inf.findViewById(R.id.toggleButton_Modus);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    expertModus.setVisibility(View.VISIBLE);
                } else {
                    expertModus.setVisibility(View.INVISIBLE);
                }
            }
        });

       return inflater.inflate(R.layout.fragment_new_device_, container, false);
    }


}
