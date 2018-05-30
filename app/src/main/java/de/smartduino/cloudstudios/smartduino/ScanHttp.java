package de.smartduino.cloudstudios.smartduino;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanHttp {

    String ssid = "";
    String pw = "";

    InetAddress ipAddrArduino;
    int localNetwork;
    Device[] myDevices;
    MainActivity main;
    RequestQueue queue;

    public ScanHttp(MainActivity p_main) {

        main = p_main;
        queue = Volley.newRequestQueue(main);
        String ipAddr;
        try {
            ipAddr = InetAddress.getByName(getIPAddress()).getHostAddress();
            ipAddr = ipAddr.replace("192.168.", "");
            Log.d("1", ipAddr);
            ipAddr = ipAddr.substring(0, ipAddr.indexOf("."));
            localNetwork = Integer.parseInt(ipAddr);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

        for (int i = 1; i < 255; i++) {
            Thread t1 = new Thread(new IpScann(localNetwork, i, this));
            t1.start();
        }
        int ctr = 0;
        while (ipAddrArduino == null) {
            try {
                Thread.sleep(10);
                ctr++;
            } catch (InterruptedException e) { // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (ctr > 600) {
                Log.d("ctr", "over600");
                break;
            }

        }

        //Log.d("", ipAddrArduino.getHostAddress());
        /*
         * setSSID("BOETZINGEN"); try { Thread.sleep(100); } catch (InterruptedException
         * e) { e.printStackTrace(); } setPW("42025916439220026669"); try {
         * Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
         * save();
         */
        //generateDevices();


        getInfo();


        //setSSID("test");
       /* setSSID("BOETZINGEN");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        setPW("42025916439220026669");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        save();*/

    }

    void setIpAddr(InetAddress p_ipAddr) {
        ipAddrArduino = p_ipAddr;
    }


    void getInfo() {
        String url = "http:/" + ipAddrArduino;
        refreshDevices(url);
    }


    void setSSID(final String ssid) {

        String url = "http://192.168.4.1/?ssid=" + ssid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //setSSID(ssid);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    void setPW(final String pw) {
        String url = "http://192.168.4.1/?pw=" + pw;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //setPW(pw);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    void save() {
        String url = "http://192.168.4.1/?save";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //save();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    void newDevTS(int p_typ, long p_startcode) {
        String url = "http:/" + ipAddrArduino + "/?newTS=" + p_typ + "," + p_startcode;
        refreshDevices(url);
    }

    void newDevTSN(int p_typ, long p_startcode, String p_name) {
        String url = "http:/" + ipAddrArduino + "/?newTSN=" + p_typ + "," + p_startcode + "," + p_name;
        refreshDevices(url);
    }

    void newDevTAC(int p_typ, long[] longArr) {
        String longArrString = "";
        for (int i = 0; i < longArr.length; i++) {
            longArrString += "," + longArr[i];
        }
        String url = "http:/" + ipAddrArduino + "/?newTAC=" + p_typ + "," + longArr.length + "" + longArrString;
        refreshDevices(url);
    }

    void newDevTACN(int p_typ, long[] longArr, String p_name) {
        String longArrString = "";
        for (int i = 0; i < longArr.length; i++) {
            longArrString += "," + longArr[i];
        }
        String url = "http:/" + ipAddrArduino + "/?newTACN=" + p_typ + "," + longArr.length + "" + longArrString
                + "," + p_name;
        refreshDevices(url);
    }

    void changeIIZ(int id, int idx, boolean zustand) {
        int i = 0;
        if (zustand)
            i = 1;
        String url = "http:/" + ipAddrArduino + "/?changeIIZ=" + id + "," + idx + "," + i;
        refreshDevices(url);
    }

    public String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();

                        Log.d("2", sAddr);
                        if (!sAddr.contains(":"))
                            return sAddr;

                    }
                }
            }
        } catch (Exception ignored) {
            Log.d("3", "Error");
        } // for now eat exceptions
        return "";
    }


    void refreshDevices(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = response.replaceAll("<html>", "");
                        response = response.replaceAll("</html>", "");
                        response = response.replaceAll(" ", "");
                        response = response.replaceFirst("\n", "");
                        Log.d("tag", response);
                        String line = response.substring(0, response.indexOf("\n"));
                        response = response.substring(response.indexOf("\n")).replaceFirst("\n", "");

                        while (line.length() > 0) {
                            Log.d("line", line);
                            int intNull = 0;
                            int id = Integer.parseInt(line.substring(intNull, line.indexOf("##")));
                            line = line.substring(line.indexOf("##")).replaceFirst("##", "");

                            String name = line.substring(0, line.indexOf("##"));
                            line = line.substring(line.indexOf("##")).replaceFirst("##", "");
                            int typ = Integer.parseInt(line.substring(intNull, line.indexOf("#")));
                            line = line.substring(line.indexOf("#")).replaceFirst("#", "");

                            ArrayList<Boolean> statesBool = new ArrayList<Boolean>();
                            while (line.indexOf("#") >= 0) {
                                int state = Integer.parseInt(line.substring(intNull, line.indexOf("#")));
                                line = line.substring(line.indexOf("#")).replaceFirst("#", "");
                                if (state == 1)
                                    statesBool.add(true);
                                else
                                    statesBool.add(false);
                            }

                            Log.d("", "" + id);
                            Log.d("", name);
                            Log.d("", "" + typ);

                            boolean[] p_bools = new boolean[statesBool.size()];
                            for (int i = 0; i < p_bools.length; i++) {
                                p_bools[i] = statesBool.get(i);
                            }
                            boolean existing = false;
                            if (myDevices != null) {
                                for (Device dev : myDevices) {
                                    if (dev.id == id) {
                                        existing = true;
                                        dev.changePara(p_bools);
                                    }
                                }
                            }

                            if (!existing) {
                                if (myDevices != null) {
                                    Device[] newDev = new Device[myDevices.length + 1];
                                    for (int i = 0; i < myDevices.length; i++) {
                                        newDev[i] = myDevices[i];
                                    }


                                    switch (typ) {
                                        case 1:
                                            newDev[myDevices.length] = new Steckdose(id, name, p_bools);
                                            break;

                                        default:
                                            break;
                                    }
                                    myDevices = newDev;
                                } else {
                                    myDevices = new Device[1];
                                    switch (typ) {
                                        case 1:
                                            myDevices[0] = new Steckdose(id, name, p_bools);
                                            break;

                                        default:
                                            break;
                                    }
                                }

                            }
                            if (response.indexOf("\n") >= 0) {
                                line = response.substring(intNull, response.indexOf("\n"));
                                response = response.substring(response.indexOf("\n")).replaceFirst("\n", "");
                            } else {
                                line = "";
                            }
                        }
                        main.submenUE();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "That didn't work!" + error);
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

}
