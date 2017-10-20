package com.zetaxmage.chagrapp.View.InfoActivity;


import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zetaxmage.chagrapp.Controller.ControllerDB;
import com.zetaxmage.chagrapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeasonScreenFragment extends Fragment {

    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_season_screen, container, false);

        mediaPlayer = new MediaPlayer();

        // On click del boton misterioso
        Button button = view.findViewById(R.id.button_season_mistery);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ControllerDB controllerDB = new ControllerDB(getContext());
                    String raw = controllerDB.getAudioRawName(86);
                    Integer rawId = getResources().getIdentifier(raw, "raw", getContext().getPackageName());
                    reproducirAudio(rawId);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), R.string.error_audionotfound, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        super.onDestroy();
    }

    // Metodo para reproducir el audio
    private void reproducirAudio (int resId) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        try {
            mediaPlayer = MediaPlayer.create(getContext(), resId);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.reset();
                }
            });
            mediaPlayer.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}
