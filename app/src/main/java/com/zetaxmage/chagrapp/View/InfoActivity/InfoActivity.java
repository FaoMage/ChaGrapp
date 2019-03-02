package com.zetaxmage.chagrapp.View.InfoActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zetaxmage.chagrapp.R;

public class InfoActivity extends AppCompatActivity implements AboutDevFragment.AboutDevFragmentInterface {

    public static final String KEY_ABOUT_DEV = "KEY_ABOUT_DEV";
    public static final String KEY_WHATS_NEW = "KEY_WHATS_NEW";
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

            // NEW CASES
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onClickSourceImg() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/FaoMage/ChaGrapp"));
        startActivity(browserIntent);
    }

    @Override
    public void onClickChangeImg() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1s1gLIL400awpeXg0IJRWUNz1JobycW8odm0N2_Agki0/edit?usp=sharing"));
        startActivity(browserIntent);
    }
}
