package de.smartduino.cloudstudios.smartduino;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String[] geraetenamen = new String[3];
    ScanHttp httpScanner ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hi Du EI", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
          httpScanner = new ScanHttp(this);

//-------------------------------------------------------------------------------------------------------------------------------------



        geraetenamen[0] = "Licht";
        geraetenamen[1] = "Licht";
        geraetenamen[2] = "Licht";
        Log.d("","tetasjgd");



        if (true){
            Intent newArduiono = new Intent(this, NewArduino_Activity.class);
            startActivity(newArduiono);
        }

        /*submenu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.mipmap.licht), "Licht2"));
        submenu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.mipmap.steckdose), "Steckdose1"));
        submenu.add(0, 1, 1, menuIconWithText(getResources().getDrawable(R.mipmap.fernbedienung), "TV1"));*/

//-------------------------------------------------------------------------------------------------------------------------------------
    }


    void submenUE(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        //menu.add(R.id.group1,0,0,"Test");
        Menu submenu = menu.addSubMenu("Geräte");
        int a = 0;
        if(httpScanner.myDevices != null) {
            for (Device dev : httpScanner.myDevices) {
                if (dev.getClass() == new Steckdose(0, null,null).getClass())
                submenu.add(0, a, a, menuIconWithText(getResources().getDrawable(R.mipmap.fernbedienung),dev.name));
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_neuesGerät) {
            FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, new NewDevice_Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }else if (id == R.id.action_settings) {
            FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, new Settings_Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }else if (id == R.id.action_impressum) {
            FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, new Impressum_Fragment());
            transaction.addToBackStack(null);
            transaction.commit();}


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }*/ if (id == 1) {

             FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_fragment, new Steckdose_Fragment());
            transaction.addToBackStack(null);
            transaction.commit();
            /*Steckdose fragment = new Steckdose();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment, fragment).commit();*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
