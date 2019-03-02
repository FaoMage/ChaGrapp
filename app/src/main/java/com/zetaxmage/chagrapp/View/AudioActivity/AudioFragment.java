package com.zetaxmage.chagrapp.View.AudioActivity;


import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zetaxmage.chagrapp.Controller.ControllerDB;
import com.zetaxmage.chagrapp.Model.POJO.Audio;
import com.zetaxmage.chagrapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class AudioFragment extends Fragment {

    private MediaPlayer mediaPlayer = null;
    private FloatingActionButton fab;

    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Audio> audiosList;

    private Boolean stackMode = false;
    private List<MediaPlayer> stackList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio, container, false);

        fab = view.findViewById(R.id.fab_audioStop);

        // Listener para el boton play
        RecyclerViewAdapter.RecyclerSongInterface listener = new RecyclerViewAdapter.RecyclerSongInterface() {
            @Override
            public void onClickPlay(Audio audio) {
                reproducirAudio(audio);
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
                new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
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
    private List<Audio> sortList (List<Audio> audiosList, Boolean formatSetting) {
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
    void changeSortOrder(Boolean order){
        sortList(audiosList,order);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.notifyAudioSortChanged(order);
    }

    // Metodo para reproducir el audio
    private void reproducirAudio (final Audio audio) {
        if (mediaPlayer != null && !stackMode && mediaPlayer.isPlaying()) {
            stopMediaPlayer();
        }
        try {
            // Busca el resId del audio en cuestion y lo reproduce
            int rawId = getResources().getIdentifier(audio.getAudioRawName(), "raw", getContext().getPackageName());
            try {
                // Se crea la instancia del mediaPlayer
                mediaPlayer = MediaPlayer.create(getContext(), rawId);

                if (stackMode) {
                    stackList.add(mediaPlayer);
                }

                // Listener para cuando termina el audio
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        stopCriteria(mediaPlayer);
                    }
                });
                fab.show();
                mediaPlayer.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                Log.d("1---------","----------");
                fab.hide();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), R.string.error_audionotfound, Toast.LENGTH_SHORT).show();
        }
    }

    // Metodo para parar el audio
    private void stopMediaPlayer () {
        if (stackMode) {
            for (MediaPlayer player : stackList) {
                if (player != null && player.isPlaying()) {
                    player.stop();
                    player.reset();
                    player.release();
                }
            }
            stackList.clear();
        } else {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
            }
        }
        mediaPlayer = null;
        fab.hide();
    }

    private void stopCriteria (MediaPlayer mediaPlayer) {
        if (stackMode) {
            stackList.remove(mediaPlayer);
            if (stackList.isEmpty()) {
                fab.hide();
                this.mediaPlayer = null;
            }
        } else {
            this.mediaPlayer = null;
            fab.hide();
        }
        mediaPlayer.reset();
        mediaPlayer.release();
    }

    // Cambia el modo de reproduccion
    Boolean changeMode(Boolean mode) {
        if (mediaPlayer == null) {
            this.stackMode = mode;
            return true;
        } else {
            Toast.makeText(getContext(), R.string.error_change_mode, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Reproduce un random audio
    void randomAudio() {
        Random random = new Random();
        // Genera un numero random que representa la posicion de algun audio de la lista
        int randomNumer = random.nextInt(audiosList.size());
        // Se busca a ese audio de la lista
        Audio randomAudio = audiosList.get(randomNumer);

        reproducirAudio(randomAudio);
    }
}
