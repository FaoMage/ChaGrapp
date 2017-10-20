package com.zetaxmage.chagrapp.View.InfoActivity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.zetaxmage.chagrapp.R;

public class InfoActivity extends AppCompatActivity {

    public static final String KEY_ABOUT_DEV = "KEY_ABOUT_DEV";
    public static final String KEY_SEASON = "KEY_SEASON";
    public static final String KEY_CHOICE = "KEY_CHOICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Recupera el bundle
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // Carga el ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Fragment Management
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Dependiendo del choice, carga el fragment deseado
        String choice = bundle.getString(KEY_CHOICE);
        assert choice != null;
        switch (choice) {
            case KEY_ABOUT_DEV:
                actionBar.setTitle(R.string.actionbar_aboutapp);
                AboutDevFragment aboutDevFragment = new AboutDevFragment();
                fragmentTransaction.add(R.id.frame_container_infoActivity,aboutDevFragment);
                break;

            case KEY_SEASON:
                actionBar.setTitle(R.string.actionbar_season);
                SeasonScreenFragment seasonScreenFragment = new SeasonScreenFragment();
                fragmentTransaction.add(R.id.frame_container_infoActivity,seasonScreenFragment);
                break;
        }
        fragmentTransaction.commit();
    }
}
