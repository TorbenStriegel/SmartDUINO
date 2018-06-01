package de.smartduino.cloudstudios.smartduino;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class Steckdose_Fragment extends Fragment {

    int idState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inf = inflater.inflate(R.layout.fragment_steckdose, container, false);
        TextView tv = (TextView) inf.findViewById(R.id.nameSteckdose);
        tv.setText(MainActivity.aktuellDevice.name);

        for(int i = 0; i < MainActivity.aktuellDevice.nameStates.length;i++){
            final Switch sw = new Switch(MainActivity.main);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(30, 30, 30, 30);
            sw.setLayoutParams(layoutParams);
            sw.setText(MainActivity.aktuellDevice.nameStates[i]);
            sw.setChecked(MainActivity.aktuellDevice.states[i]);

            LinearLayout linearLayout = inf.findViewById(R.id.layoutSteckdose);
            // Add Switch to LinearLayout
            if (linearLayout != null) {
                linearLayout.addView(sw);
            }
            idState = i;
            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MainActivity.httpScanner.setINZ(MainActivity.aktuelleId,MainActivity.aktuellDevice.nameStates[idState],isChecked);
                }
            });
        }
        return inf;
    }


}
