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

import static de.smartduino.cloudstudios.smartduino.MainActivity.httpScanner;


public class Settings_Fragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_settings_, container, false);
        Button b = inf.findViewById(R.id.button_addDevice);
        b.setOnClickListener(this);

        return inf;
    }

    @Override
    public void onClick(View v) {
        Log.d("dasd","asdasdsad");
    }
}
