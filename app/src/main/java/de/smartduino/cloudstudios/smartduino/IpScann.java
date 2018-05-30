package de.smartduino.cloudstudios.smartduino;

import android.util.Log;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URL;
import java.util.Scanner;

public class IpScann implements Runnable {

	int local;
	int host;
	ScanHttp scanObj;

	public IpScann(int int1,int int2,ScanHttp obj) {
		local = int1;
		host = int2;
		scanObj= obj;
	}

	@Override
	public void run() {
		String ipAddress = "192.168."+local+"."+host;

		try {
			InetAddress inet = InetAddress.getByName(ipAddress);
			if(inet.isReachable(5000)) {
				if(!(inet.getHostAddress().equals(InetAddress.getLocalHost().getHostAddress()))) {
					Log.d("IPSCANN",inet.getHostAddress());
					URL u = new URL("http://192.168." + local+"."+host+"/?getIP");
					String r = new Scanner(u.openStream()).useDelimiter("\\Z").next();
					if(r.contains("IP_Arduino"))scanObj.setIpAddr(inet);
				}
			}
		} catch (IOException e) {
		}

	}

}
