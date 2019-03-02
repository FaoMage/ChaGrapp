package com.zetaxmage.chagrapp.View.WhatsNewActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zetaxmage.chagrapp.R;

public class WhatsNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.actionbar_whats_new);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button button = findViewById(R.id.new_20_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1BjMyl6vCbmrfWmQD7OW5exZ6QNIboYOo25AbRSdoE84/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });

    }
}
