package de.smartduino.cloudstudios.smartduino;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String[] geraetenamen = new String[3];
    static MainActivity main;
    static ScanHttp httpScanner;
    static Device aktuellDevice;
    static int aktuelleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpScanner.getInfo();
                Snackbar.make(view, "Refresh devices", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        if (true){
        httpScanner = new ScanHttp(this);


        if (!httpScanner.isArduinoInNetwork()) {
            Intent newArduiono = new Intent(this, NewArduino_Activity.class);
            startActivity(newArduiono);
        }

        TextView anzeige = (TextView) findViewById(R.id.textView_start);
        //anzeige.setText(httpScanner.myDevices.length+" devices were found in your network.");
        }
        /*
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        Menu submenu = menu.addSubMenu("Geräte");
        submenu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.mipmap.licht), "Licht1"));
        submenu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.mipmap.licht), "Licht2"));
        submenu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.mipmap.licht), "Licht3"));
        submenu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.mipmap.steckdose), "Steckdose1"));
        submenu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.mipmap.fernbedienung), "TV1"));
        */

//-------------------------------------------------------------------------------------------------------------------------------------
    }


    void submenUE() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        menu.clear();
        Menu submenu = menu.addSubMenu("Geräte");
        if (httpScanner.myDevices != null) {
            for (int i = 0; i< httpScanner.myDevices.length;i++) {
                if (httpScanner.myDevices[i].getClass() == Steckdose.class)
                    submenu.add(0, i, i, menuIconWithText(getResources().getDrawable(R.mipmap.steckdose), httpScanner.myDevices[i].name));
            }
        }
    }

    private CharSequence menuIconWithText(Drawable icon, String title) {                            // ICON und TEXT zusammenfügen

        icon.setBounds(0, 0, 100, 100);
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(icon, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int aktuelleID = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (aktuelleID == R.id.action_neuesGerät) {
            FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, new NewDevice_Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (aktuelleID == R.id.action_settings) {
            FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, new Settings_Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (aktuelleID == R.id.action_impressum) {
            FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, new Impressum_Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        aktuellDevice = httpScanner.myDevices[id];
        aktuelleId = id;
        Log.d("DevicenName",aktuellDevice.name);


        if (aktuellDevice.getClass() == Steckdose.class) {

            FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, new Steckdose_Fragment());
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (aktuellDevice.getClass() == Licht.class) {

            FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, new Licht_Fragement());
            transaction.addToBackStack(null);
            transaction.commit();
        } /*else if (httpScanner.myDevices[id].getClass() == Steckdose.class) {

            FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, new Fernseher_Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
