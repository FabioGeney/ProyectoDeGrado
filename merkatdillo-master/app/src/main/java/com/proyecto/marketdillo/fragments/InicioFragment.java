package com.proyecto.marketdillo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.proyecto.marketdillo.R;
import com.proyecto.marketdillo.SliderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment{

    private ViewPager slideViewPager;
    private LinearLayout dotsLayout;

    private SliderAdapter sliderAdapter;

    public InicioFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);
        slideViewPager = (ViewPager) root.findViewById(R.id.slideViewPager);
        dotsLayout = (LinearLayout) root.findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(getActivity());
        slideViewPager.setAdapter(sliderAdapter);

        return root;
    }

}
