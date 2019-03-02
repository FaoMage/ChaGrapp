package com.zetaxmage.chagrapp.View.InfoActivity;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zetaxmage.chagrapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutDevFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_dev, container, false);

        final AboutDevFragmentInterface adfi = (AboutDevFragmentInterface) getContext();

        ImageView imageViewSource = view.findViewById(R.id.img_button_source);
        imageViewSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adfi.onClickSourceImg();
            }
        });

        ImageView imageViewChange = view.findViewById(R.id.img_button_change);
        imageViewChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adfi.onClickChangeImg();
            }
        });

        return view;
    }

    public interface AboutDevFragmentInterface {
        void onClickSourceImg();
        void onClickChangeImg();
    }
}
