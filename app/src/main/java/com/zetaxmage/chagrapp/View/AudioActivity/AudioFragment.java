package com.zetaxmage.chagrapp.View.AudioActivity;


import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.zetaxmage.chagrapp.Controller.ControllerDB;
import com.zetaxmage.chagrapp.Model.POJO.Audio;
import com.zetaxmage.chagrapp.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AudioFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private FloatingActionButton fab;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Audio> audiosList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio, container, false);

        mediaPlayer = new MediaPlayer();
        fab = view.findViewById(R.id.fab_audioStop);

        // Listener para el boton play
        RecyclerViewAdapter.RecyclerSongInterface listener = new RecyclerViewAdapter.RecyclerSongInterface() {
            @Override
            public void onClickPlay(Audio audio) {
                try {
                    // Busca el resId del audio en cuestion y lo reproduce
                    Integer rawId = getResources().getIdentifier(audio.getAudioRawName(), "raw", getContext().getPackageName());
                    reproducirAudio(rawId);
                    fab.show();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                    fab.hide();
                    Toast.makeText(getContext(), R.string.error_audionotfound, Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Listener del fab (stop audio)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMediaPlayer();
            }
        });

        // Busca en la db la lista de audios y la forma de ordenarla
        ControllerDB controllerDB = new ControllerDB(getContext());
        audiosList = controllerDB.getAudioList();
        Boolean formatSetting = controllerDB.getAudioFormatSetting();

        // ordena la lista
        sortList(audiosList,formatSetting);

        // setup de recyclerview
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_sound);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(),audiosList,listener,formatSetting);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onDestroy() {
        stopMediaPlayer();
        super.onDestroy();
    }

    // Metodo para ordenar la lista de audios
    private List<Audio> sortList (List<Audio> audiosList,Boolean formatSetting) {
        if (formatSetting) {
            Collections.sort(audiosList, new Comparator<Audio>() {
                @Override
                public int compare(Audio audio, Audio t1) {
                    return audio.getAudioDisplayName().compareTo(t1.getAudioDisplayName());
                }
            });
        } else {
            Collections.sort(audiosList, new Comparator<Audio>() {
                @Override
                public int compare(Audio audio, Audio t1) {
                    return audio.getAudioDisplayAuthor().compareTo(t1.getAudioDisplayAuthor());
                }
            });
        }
        return audiosList;
    }

    // Metodo para cambiar el orden de la lista desde el recycler
    public void changeSortOrder (Boolean order){
        sortList(audiosList,order);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.notifyAudioSortChanged(order);
    }

    // Metodo para reproducir el audio
    private void reproducirAudio (int resId) {
        if (mediaPlayer.isPlaying()) {
            stopMediaPlayer();
        }
        try {
            fab.show();
            mediaPlayer = MediaPlayer.create(getContext(), resId);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.reset();
                    fab.hide();
                }
            });
            mediaPlayer.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    // Metodo para parar el audio
    private void stopMediaPlayer () {
        mediaPlayer.stop();
        mediaPlayer.reset();
        fab.hide();
    }

}
