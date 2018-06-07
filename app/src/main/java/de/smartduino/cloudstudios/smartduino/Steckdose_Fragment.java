package de.smartduino.cloudstudios.smartduino;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

import static de.smartduino.cloudstudios.smartduino.MainActivity.main;


public class Steckdose_Fragment extends Fragment {

    private TextToSpeech tts;
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
                    if(isChecked){
                        speak("The Function"+ MainActivity.aktuellDevice.nameStates[idState]+" of Device" + MainActivity.aktuellDevice.name +" is now on" );
                    }else{

                        speak("The Function"+ MainActivity.aktuellDevice.nameStates[idState]+" of Device" + MainActivity.aktuellDevice.name +" is now off" );
                    }
                    MainActivity.httpScanner.setINZ(MainActivity.aktuelleId,MainActivity.aktuellDevice.nameStates[idState],isChecked);
                }
            });
        }

        tts = new TextToSpeech(main, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });

        return inf;
    }
    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


}
