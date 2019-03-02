package com.zetaxmage.chagrapp.View.MainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.zetaxmage.chagrapp.Controller.ControllerDB;
import com.zetaxmage.chagrapp.R;
import com.zetaxmage.chagrapp.View.AudioActivity.AudioActivity;
import com.zetaxmage.chagrapp.View.InfoActivity.InfoActivity;
import com.zetaxmage.chagrapp.View.WhatsNewActivity.WhatsNewActivity;
import com.zetaxmage.chagrapp.View.Login.LoginActivity;

public class MainActivity extends AppCompatActivity
                            implements MainActivityFragment.MainActivityFragmentInterface {

    private static final String HOME = "HOME";
    private static final String NOT_HOME = "NOT_HOME";
    public static final String KEY_USER = "KEY_USER";

    private MainActivityFragment mainActivityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Metodo que se ejecuta solo la primera vez en ejecutar la app
        onFirstTime();

        // Action Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.actionbar_home);

        // Se carga el fragment
        mainActivityFragment = new MainActivityFragment();
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
        switch (item.getItemId()){
            case R.id.menu_whats_new:
                onFirstTimeInVersion(true);
                Intent intentW = new Intent(this, WhatsNewActivity.class);
                startActivity(intentW);
                break;

            case R.id.menu_aboutUs:
                Intent intentI = new Intent(this, InfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(InfoActivity.KEY_CHOICE,InfoActivity.KEY_ABOUT_DEV);
                intentI.putExtras(bundle);
                startActivity(intentI);
                break;

            case R.id.menu_log_out:
                FirebaseAuth.getInstance().signOut();
                Intent intentLogOut = new Intent(this, LoginActivity.class);
                startActivity(intentLogOut);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // Metodo para cambiar el fragment y manejar Home (no se usa en la v actual)
    private void changeFragment (Fragment fragment, String tag) {
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
        boolean firstRunSetUp = wmbPreference.getBoolean("firstRunSetUp", true);

        if (firstRunSetUp) {
            ControllerDB controllerDB = new ControllerDB(this);
            controllerDB.firstTimeSettingsSetUp();
            controllerDB.firstTimeAudiosSetUp();

            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("firstRunSetUp", false);
            editor.apply();
        }
        onFirstTimeInVersion(false);
    }

    public void onFirstTimeInVersion (boolean whatsNewScreen) {
        final SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstRunThisVersion = wmbPreference.getBoolean("firstRunInV20", true);

        if (firstRunThisVersion) {
            // Si ve la whats new screen gracias al menu
            if (whatsNewScreen) {
                SharedPreferences.Editor editor = wmbPreference.edit();
                editor.putBoolean("firstRunInV20", false);
                editor.apply();
            } else { // Sino, se muestra la snack
                View view = findViewById(R.id.coordinator_main);
                Snackbar.make(view, R.string.first_run_text, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.first_run_button, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SharedPreferences.Editor editor = wmbPreference.edit();
                                editor.putBoolean("firstRunInV20", false);
                                editor.apply();

                                Intent intent = new Intent(MainActivity.this, WhatsNewActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.white))
                        .show();
            }
        }
    }



    // onClicks
    @Override
    public void onClickAudioButton() {
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
    }

}
