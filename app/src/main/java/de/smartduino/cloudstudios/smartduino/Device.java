package de.smartduino.cloudstudios.smartduino;

public class Device {

	int id;
	String name;
	boolean[] states;

	void changePara(boolean[] p_states){
		states = p_states;
	}
	
	
}
