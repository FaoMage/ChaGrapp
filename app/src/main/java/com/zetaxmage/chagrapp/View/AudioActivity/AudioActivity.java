package com.zetaxmage.chagrapp.View.AudioActivity;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.zetaxmage.chagrapp.Controller.ControllerDB;
import com.zetaxmage.chagrapp.R;

public class AudioActivity extends AppCompatActivity {

    private AudioFragment audioFragment;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        // Carga la ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.actionbar_audios);

        // Carga el fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        audioFragment = new AudioFragment();
        fragmentTransaction.replace(R.id.frame_container_audioActivity,audioFragment);
        fragmentTransaction.commit();
    }

    // Infla el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_audio_activity,menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    // Listeners del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ControllerDB controllerDB = new ControllerDB(this);

        switch (item.getItemId()) {
            case R.id.menu_ordenar_nombre:
                audioFragment.changeSortOrder(true);
                controllerDB.updateAudioFormatSetting(true);
                return true;

            case R.id.menu_ordenar_autor:
                audioFragment.changeSortOrder(false);
                controllerDB.updateAudioFormatSetting(false);
                return true;

            case R.id.menu_random_button:
                audioFragment.randomAudio();
                return true;

            case R.id.menu_stack_mode:
                if (audioFragment.changeMode(true)) {
                    changeModeOption(true);
                }
                return true;

            case R.id.menu_spam_mode:
                if(audioFragment.changeMode(false)) {
                    changeModeOption(false);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeModeOption (Boolean isStackMode) {
        MenuItem menuStack = menu.findItem(R.id.menu_stack_mode);
        MenuItem menuSpam = menu.findItem(R.id.menu_spam_mode);

        menuStack.setVisible(!isStackMode);
        menuSpam.setVisible(isStackMode);
    }
}
