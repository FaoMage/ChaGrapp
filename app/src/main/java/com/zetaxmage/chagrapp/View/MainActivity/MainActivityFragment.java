package com.zetaxmage.chagrapp.View.MainActivity;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zetaxmage.chagrapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        final MainActivityFragmentInterface mafi = (MainActivityFragmentInterface) getContext();

        Button buttonAudio = view.findViewById(R.id.button_audios);
        buttonAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mafi.onClickAudioButton();
            }
        });

        return view;
    }

    public interface MainActivityFragmentInterface {
        void onClickAudioButton();
    }

}
