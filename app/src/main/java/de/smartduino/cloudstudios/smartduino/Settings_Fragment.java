package de.smartduino.cloudstudios.smartduino;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Locale;

import static de.smartduino.cloudstudios.smartduino.MainActivity.httpScanner;
import static de.smartduino.cloudstudios.smartduino.MainActivity.main;


public class Settings_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_settings_, container, false);

        return inf;
    }
}


