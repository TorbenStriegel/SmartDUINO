package de.smartduino.cloudstudios.smartduino;

import android.util.Log;

public class Steckdose extends Device {

	public Steckdose(int p_id , String p_name, boolean[] p_states,String[] p_nameStates) {
        Log.d("","Steckdose Konstruktor");
		id = p_id;
		name = p_name;
		states = p_states;
		nameStates= p_nameStates;
	}
	
}
