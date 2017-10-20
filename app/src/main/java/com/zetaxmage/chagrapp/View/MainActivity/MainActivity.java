package com.zetaxmage.chagrapp.View.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zetaxmage.chagrapp.Controller.ControllerDB;
import com.zetaxmage.chagrapp.R;
import com.zetaxmage.chagrapp.View.AudioActivity.AudioActivity;
import com.zetaxmage.chagrapp.View.InfoActivity.InfoActivity;

public class MainActivity extends AppCompatActivity
                            implements MainActivityFragment.MainActivityFragmentInterface {

    private static final String HOME = "HOME";
    private static final String NOT_HOME = "NOT_HOME";

    private MainActivityFragment mainActivityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Metodo que se ejecuta solo la primera vez en ejecutar la app
        onFirstTime();

        setContentView(R.layout.activity_main);
        mainActivityFragment = new MainActivityFragment();

        // Action Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.actionbar_home);

        // Se carga el fragment
        changeFragment(mainActivityFragment,HOME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, InfoActivity.class);
        Bundle bundle = new Bundle();
        switch (item.getItemId()){
            case R.id.menu_aboutUs:
                bundle.putString(InfoActivity.KEY_CHOICE,InfoActivity.KEY_ABOUT_DEV);
                break;

            case R.id.menu_seasonScreen:
                bundle.putString(InfoActivity.KEY_CHOICE,InfoActivity.KEY_SEASON);
                break;
        }
        intent.putExtras(bundle);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    // Metodo para cambiar el fragment y manejar Home (no se usa en la v actual)
    private void changeFragment (Fragment fragment,String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment loadedFragment = fragmentManager.findFragmentById(R.id.frame_container_mainActivity);
        if (loadedFragment == null || !loadedFragment.equals(fragment)) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame_container_mainActivity,fragment,tag);
            if (loadedFragment != null && loadedFragment.getTag().equals(HOME)) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    }

    // Metodo que se ejecuta una sola vez para setear la db
    private void onFirstTime () {
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun)
        {
            ControllerDB controllerDB = new ControllerDB(this);
            controllerDB.firstTimeSettingsSetUp();
            controllerDB.firstTimeAudiosSetUp();

            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }
    }

    // onClicks
    @Override
    public void onClickAudioButton() {
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
    }

}
